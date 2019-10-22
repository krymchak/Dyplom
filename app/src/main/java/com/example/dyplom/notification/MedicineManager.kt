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
import com.example.dyplom.MyDatabase
import com.example.dyplom.activity.NotificationOfMedicineActivity
import com.example.dyplom.R
import java.util.*

class MedicineManager : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationId = createID()
        val channelId = "channel-id"
        val channelName = "Channel Name"
        val importance = NotificationManager.IMPORTANCE_HIGH

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(
                channelId, channelName, importance
            )
            notificationManager.createNotificationChannel(mChannel)
        }

        val id= intent!!.getIntExtra("id",0)
        val timeid= intent!!.getIntExtra("timeid",0)
        Log.i("aaaa", id.toString())
        val task = MyDatabase.getInstance(context).MedicineDAO().getById(id)

        if (task==null)
        {
            var alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val i = Intent(context, MedicineManager::class.java)
            val pid = ((id+timeid)*(id+timeid+1)+timeid)/2
            Log.i("AAAAAAAAAAA", pid.toString())
            val pendingIntent = PendingIntent.getBroadcast(context, pid, i, PendingIntent.FLAG_UPDATE_CURRENT)
            pendingIntent.cancel()
            alarmManager.cancel(pendingIntent)
            Log.i("usuwanie", id.toString())
        }
        else {
            Log.i("bbbbb", task.name)


            val title = task.name
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

            Log.i("random number", notificationId.toString())
            notificationManager.notify(notificationId, mBuilder.build())
        }
    }

    fun createID(): Int {
        val now = Date()
        return System.currentTimeMillis().toInt()
    }
}