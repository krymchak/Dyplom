package com.example.dyplom.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.dyplom.MyDatabase
import com.example.dyplom.R
import com.example.dyplom.entity.Medicine
import com.example.dyplom.presenter.AddMedicinePresenter
import com.example.dyplom.presenter.NeedBuyPresenter
import com.example.dyplom.view.NeedBuyView

class NeedBuyActivity : AppCompatActivity(), NeedBuyView {

    val needBuyPresenter = NeedBuyPresenter(this)


    var id: Int = 0
    // Lek, który trzeba dokupić
    var medicine: Medicine? = null


    /*
     * Funkcja do tworzenia widoku
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.need_buy)

        showInformation()
    }

    /*
     * Funkcja do wyswietlenia informacji
     */
    private fun showInformation() {
        needBuyPresenter.showMedicine()
    }

    /*
     * Funkcja do ustawienia informacji o leku
     */
    override fun setInformation()
    {
        if(medicine!=null) {
            val number = findViewById<TextView>(R.id.textView)
            number.text = "Zostało leków" + medicine!!.available_quantity.toInt()
        }

    }

    /*
     * Funkcja pobierająca informacje o leku
     */
    override fun getInformation() {
        id = intent.getIntExtra("id",0)
        medicine = needBuyPresenter.getMedicine(id)


    }

    /*
     * Funkcja zarządzająca przyciskami
     * parametr: v - element sterujący przyciskiem
     */
    fun onClick (v: View)
    {
        when (v?.id)
        {
            R.id.accept -> {needBuyPresenter.onAcceptButtonClick()}
            R.id.later -> {needBuyPresenter.onLaterButtonClick()}
        }

    }

    /*
     * Funkcja do powrotu do widoku głównego
     */
    override fun returnToMainActivity()
    {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    /*
    * Funkcja do przejścia do widoku mapy
    */
    override fun goToMap()
    {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }
}