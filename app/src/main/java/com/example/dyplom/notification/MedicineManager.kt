package com.example.dyplom.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.TaskStackBuilder
import android.util.Log
import com.example.dyplom.App
import com.example.dyplom.MyDatabase
import com.example.dyplom.activity.NotificationOfMedicineActivity
import com.example.dyplom.R
import com.example.dyplom.entity.HistoryOfTakingMedication
import com.example.dyplom.entity.Medicine
import java.text.SimpleDateFormat
import java.util.*

class MedicineManager : BroadcastReceiver() {

    lateinit var notificationManager: NotificationManager
    var notificationId: Int = 0
    val channelId = "channel-id"
    val channelName = "Channel Name"
    val HistoryOfTakingMedicationDAO = MyDatabase.getInstance(App.applicationContext()).HistoryOfTakingMedicationDAO()

    /*
     * Funkcja, która pobiera dane i tworzy powiadomienie
     */
    override fun onReceive(context: Context?, intent: Intent?) {

        val notificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationId = createID()

        val importance = NotificationManager.IMPORTANCE_HIGH

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(
                channelId, channelName, importance
            )
            notificationManager.createNotificationChannel(mChannel)
        }

        val id= intent!!.getIntExtra("id",0)
        val timeid= intent.getIntExtra("timeid",0)
        val pid = ((id+timeid)*(id+timeid+1)+timeid)/2
        val medicine = MyDatabase.getInstance(context).MedicineDAO().getById(id)
        val time = MyDatabase.getInstance(context).TimeOfMedicineDAO().getById(timeid)

        if (medicine==null || time==null)
        {
            deleteNotification(pid, context)
        }
        else {

            createHistory(pid, medicine.name!!)

            createNotification(id, timeid, medicine.name!!, context)
        }
    }

    /*
     * Funkcja, która generuje unikatowe id dla powiadomienia
     */
    fun createID(): Int {
        return System.currentTimeMillis().toInt()
    }

    /*
    * funkcja, która usuwa powiadomienie, jeśli czas lub lek nie istnieją
    * parametr: pid - id powiadomienia
    * parametr: context - Context
     */
    fun deleteNotification(pid: Int, context: Context)
    {
        var alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val i = Intent(context, MedicineManager::class.java)

        val pendingIntent = PendingIntent.getBroadcast(context, pid, i, PendingIntent.FLAG_UPDATE_CURRENT)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
    }

    /*
     * Funkcja, która dodaje do bazy nową historie przyjmowania leków, jeśli historia jeszcze nie istnieje
     * parametr: pid - id historii
     * parametr: name - nazwa leku
     */
    fun createHistory(pid: Int, name: String)
    {
        val history = HistoryOfTakingMedicationDAO.getByPid(pid)
        if (history==null) {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
            val currentDate = sdf.format(Date())
            HistoryOfTakingMedicationDAO.insert(HistoryOfTakingMedication( pid, name, currentDate.toString()))
        }
    }

    /*
     * Funkcja. która tworzy nowe powiadomienie
     * parametr: id - id leku
     * parametr: timeid - id czasu
     * parametr: name - nazwa leku
     * parametr: context - Context
     */
    fun createNotification(id: Int, timeid: Int, name: String, context: Context)
    {
        val title = name
        val text = "Czas na leki!"
        val mBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(text)
            .setVibrate(longArrayOf(100, 250))
            .setAutoCancel(true)

        val stackBuilder = TaskStackBuilder.create(context)
        val intent = Intent(context, NotificationOfMedicineActivity::class.java)
        intent.putExtra("id",id)
        intent.putExtra("timeid",timeid)
        stackBuilder.addNextIntent(intent)
        val resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        mBuilder.setContentIntent(resultPendingIntent)

        notificationManager.notify(notificationId, mBuilder.build())
    }
}