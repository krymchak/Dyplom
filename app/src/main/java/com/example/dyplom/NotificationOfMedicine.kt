package com.example.dyplom

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import java.util.*

class NotificationOfMedicine : AppCompatActivity() {

    var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.notification_of_medicine)

        setInformation()
    }

    fun setInformation()
    {
        id = intent.getIntExtra("id",0)
        val task = MyDatabase.getInstance(this).MedicineDAO().getById(id)

        val name = findViewById<TextView>(R.id.name)
        name.text = task.name

    }

    fun accept (v: View)
    {
        returnToMainActivity()
    }

    fun later (v:View)
    {
        val i = Intent(this, MedicineManager::class.java)
        i.putExtra("id", id)
        val pi = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT)

        var a: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.SECOND, 20)

        a.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi)

        returnToMainActivity()
    }

    fun returnToMainActivity()
    {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}