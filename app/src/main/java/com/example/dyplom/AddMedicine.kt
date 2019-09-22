package com.example.dyplom

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.add_medicine.*
import java.text.SimpleDateFormat
import java.util.*

class AddMedicine : AppCompatActivity() {

    var id = 1
    var type = ""
    var tytul=""
    var types= arrayOf("tablet","capsule", "drops", "injection", "ointment", "syrup", "spoon", "spray", "inhalator")
    var listOfTime: ArrayList<TimeOfMedicine> = arrayListOf<TimeOfMedicine>()
    private val adapter : TimeAdapter = TimeAdapter(this, ArrayList<TimeOfMedicine>())
    lateinit var gridView : GridView
    var timeid = MyDatabase.getInstance(this).TimeOfMedicineDAO().max()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_medicine)

        //timePicker1.setIs24HourView(DateFormat.is24HourFormat(this))
        gridView = findViewById<GridView>(R.id.gridview)
        gridView.setAdapter(adapter)

        if (savedInstanceState != null) {

            type = savedInstanceState.getString("type")
            tytul = savedInstanceState.getString("tytul")

            if (type!="")
            {
                val buttonID = type
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                findViewById<ImageButton>(resID).background.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY)
            }
        }

    }

    fun add_time(v:View)
    {
        val calendar = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),hour, minute, 0)
            id = MyDatabase.getInstance(this).MedicineDAO().max() + 1
            timeid++
            val i = Intent(this, MedicineManager::class.java)
            i.putExtra("id", id)
            i.putExtra("timeid", timeid)
            val pi = PendingIntent.getBroadcast(this, id+timeid, i, PendingIntent.FLAG_UPDATE_CURRENT)
            var a: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            a.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),24*60*60*1000, pi)
            val time = SimpleDateFormat("HH:mm").format(calendar.time)



            Log.i("aaaa", timeid.toString())
            val newTime = TimeOfMedicine(timeid,id,time)
            adapter.add(newTime)
            listOfTime.add(newTime)
        }
        TimePickerDialog(this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun add(v: View)
    {
        var editText = findViewById(R.id.tytul) as EditText
        tytul = editText.text.toString()

        val intent = Intent()

        if (type=="")
        {
            Toast.makeText(this, "Wybierz typ", Toast.LENGTH_SHORT).show()
            return
        }
        if (tytul=="")
        {
            Toast.makeText(this, "Wpisz nazwe zadania", Toast.LENGTH_SHORT).show()
            return
        }
        if (listOfTime.isEmpty())
        {
            Toast.makeText(this, "Wprowadz czas", Toast.LENGTH_SHORT).show()
            return
        }

        var item = Medicine(id, tytul,/*time,*/type/*, priority*/)

        MyDatabase.getInstance(this).MedicineDAO().insert(item)

        MyDatabase.getInstance(this).TimeOfMedicineDAO().insert(listOfTime)
        setResult(RESULT_OK, intent);
        finish();


    }

    fun typ (v: View)
    {
        var typ = v.getResources().getResourceName(v.getId()).toString()
        type = typ.removePrefix("com.example.dyplom:id/")
        for (i in 0..types.size-1)
        {
            val buttonID = types[i]
            val resID = resources.getIdentifier(buttonID, "id", packageName)
            findViewById<ImageButton>(resID).background.clearColorFilter()

        }
        findViewById<ImageButton>(v.getId()).background.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);

    }


    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putString("type", type)
        savedInstanceState.putString("tytul", tytul)
        //savedInstanceState.putString("time", time)
        //savedInstanceState.putInt("priority", priority)
        super.onSaveInstanceState(savedInstanceState);
    }
}
