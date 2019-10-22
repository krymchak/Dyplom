package com.example.dyplom.presenter

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.dyplom.App
import com.example.dyplom.MyDatabase
import com.example.dyplom.entity.Medicine
import com.example.dyplom.notification.MedicineManager
import com.example.dyplom.view.AddMedicineView
import com.example.dyplom.view.NotificationOfMedicineView
import java.util.*

class NotificationOfMedicinePresenter {


    val context = App.applicationContext()
    val medicineDAO = MyDatabase.getInstance(context).MedicineDAO()
    val timeOfMedicineDAO = MyDatabase.getInstance(context).TimeOfMedicineDAO()
    val notificationOfMedicineView: NotificationOfMedicineView

    constructor(notificationOfMedicineView: NotificationOfMedicineView)
    {
        this.notificationOfMedicineView=notificationOfMedicineView
    }

    fun getMedicine(id: Int): Medicine {
        return medicineDAO.getById(id)
    }

    fun onAcceptButtonClick(id: Int) {
        medicineDAO.reduce(id)
        notificationOfMedicineView.accept()
    }

    fun onLaterButtonClick(id: Int, timeid: Int) {

        setAlarm(id, timeid)
        notificationOfMedicineView.later()
    }

    private fun setAlarm(id: Int, timeid: Int)
    {
        val i = Intent(context, MedicineManager::class.java)
        i.putExtra("id", id)
        i.putExtra("timeid",timeid)

        val pid = ((id+timeid)*(id+timeid+1)+timeid)/2
        val pi = PendingIntent.getBroadcast(context, pid, i, PendingIntent.FLAG_UPDATE_CURRENT)

        var a: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.SECOND, 20)

        a.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi)
    }
}