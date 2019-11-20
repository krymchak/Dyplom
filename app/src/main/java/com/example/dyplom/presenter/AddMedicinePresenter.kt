package com.example.dyplom.presenter

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

import com.example.dyplom.App
import com.example.dyplom.MyDatabase
import com.example.dyplom.entity.Medicine
import com.example.dyplom.entity.TimeOfMedicine
import com.example.dyplom.notification.MedicineManager
import com.example.dyplom.view.AddMedicineView
import java.util.*

class AddMedicinePresenter {

    val context = App.applicationContext()
    val medicineDAO = MyDatabase.getInstance(context).MedicineDAO()
    val timeOfMedicineDAO = MyDatabase.getInstance(context).TimeOfMedicineDAO()
    val addMedicineView: AddMedicineView
    var timeid= timeOfMedicineDAO.maxId()+1

    constructor(addMedicineView: AddMedicineView)
    {
        this.addMedicineView=addMedicineView
    }


    private fun createNotification(time: TimeOfMedicine)
    {
        val calendar = Calendar.getInstance()
        setTimeToCalendar(calendar, time.hour, time.minute)
        val i = Intent(context, MedicineManager::class.java)
        var medicineId=time.medicineId
        var timeid=time.timeId
        i.putExtra("id", medicineId)
        i.putExtra("timeid", timeid)
        val pid = ((medicineId!!+timeid)*(medicineId!!+timeid+1)+timeid)/2
        val pi = PendingIntent.getBroadcast(context, pid, i, PendingIntent.FLAG_UPDATE_CURRENT)
        var a: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        a.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),24*60*60*1000, pi)
    }

    private fun setTimeToCalendar(calendar: Calendar, hour: Int, minute: Int)
    {
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        if(Calendar.getInstance().getTimeInMillis() - calendar.getTimeInMillis()>0)
            calendar.add(Calendar.DAY_OF_YEAR, 1)
    }

   /* private fun createNewTime(calendar: Calendar, id: Int, timeid: Int): TimeOfMedicine
    {
        val time = SimpleDateFormat("HH:mm").format(calendar.time)
        return TimeOfMedicine(timeid, id, time)
    }*/


    fun addTime(hour: Int, minute: Int)
    {
        //val calendar = Calendar.getInstance()
        //setTimeToCalendar(calendar, hour, minute)
        val id = medicineDAO.maxId() + 1

        val newTime= TimeOfMedicine(timeid, id, hour, minute)
        timeid++
        //createNotification(calendar, id, timeid)

        addMedicineView.setTime(newTime)

    }


    fun addNewMedicine(id:Int, name:String, type:String, available_quantity:Float, required_amount:Float, dose:Float, listOfTime:List<TimeOfMedicine>)
    {
        var item = Medicine(id, name, type, available_quantity, required_amount, dose)
        medicineDAO.insert(item)
        timeOfMedicineDAO.insert(listOfTime)
        setAlarmForTimeOfMedicine(listOfTime)
    }

    private fun setAlarmForTimeOfMedicine(listOfTime: List<TimeOfMedicine>) {
       for (time in listOfTime)
           createNotification(time)
    }

    fun onAddButtonClick() {
        if(addMedicineView.getDataFromEditText())
            addMedicineView.addNewMedicine()
    }

    fun onAddTimeButtonClick() {
        addMedicineView.addTime()
    }




}