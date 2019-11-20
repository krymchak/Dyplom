package com.example.dyplom.activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.dyplom.MyDatabase
import com.example.dyplom.R
import com.example.dyplom.entity.Medicine
import com.example.dyplom.notification.MedicineManager
import com.example.dyplom.presenter.NotificationOfMedicinePresenter
import com.example.dyplom.view.NotificationOfMedicineView
import java.util.*

class NotificationOfMedicineActivity : AppCompatActivity(), NotificationOfMedicineView {

    var notificationOfMedicinePresenter = NotificationOfMedicinePresenter(this)

    // id leku, który przeba przyjąć
    var id: Int = 0
    // id czasu przyjmowania leku
    var timeid: Int =0
    // lek, który przeba przyjąć
    lateinit var medicine: Medicine

    /*
    * Funkcja do tworzenia widoku
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.notification_of_medicine)

        setInformation()
    }

    /*
     * Funkcja do pokazania informacji o leku
     */
    fun setInformation()
    {
        id = intent.getIntExtra("id",0)
        timeid = intent.getIntExtra("timeid",0)
        medicine = notificationOfMedicinePresenter.getMedicine(id)

        val name = findViewById<TextView>(R.id.name)
        name.text = medicine.name

    }

    /*
    * Funkcja do przejścia do widoku NeedBuy
    */
    fun showNeedBuy()
    {
        val intent = Intent(this, NeedBuyActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }


    /*
     * Funkcja do powrotu do widoku głównego
     */
    fun returnToMainActivity()
    {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    /*
     * Funkcja zarządzająca przyciskami
     * parametr: v - element sterujący przyciskiem
     */
    fun onClick(v: View)
    {
        when (v?.id)
        {
            R.id.accept -> {notificationOfMedicinePresenter.onAcceptButtonClick(id, timeid)}
            R.id.later -> {notificationOfMedicinePresenter.onLaterButtonClick(id, timeid)}
        }
    }

    /*
    * Funkcja do odłożenia przyjmowania leku
    */
    override fun later()
    {
        returnToMainActivity()
    }

    /*
    * Funkcja do akceptacji przyjmowania leku
     */
    override fun accept()
    {
        if (medicine.isNeedBuy())
        {
            showNeedBuy()
        }
        else {
            returnToMainActivity()
        }
    }
}