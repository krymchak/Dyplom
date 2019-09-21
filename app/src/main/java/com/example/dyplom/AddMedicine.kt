package com.example.dyplom

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.text.format.DateFormat
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.add_medicine.*
import java.util.*

class AddMedicine : AppCompatActivity() {

    var id = 1
    var type = ""
    var tytul=""
    //var priority=0
    var types= arrayOf("tablet","capsule", "drops", "injection", "ointment", "syrup", "spoon", "spray", "inhalator")
    var time=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_medicine)

        timePicker1.setIs24HourView(DateFormat.is24HourFormat(this))

        if (savedInstanceState != null) {

            type = savedInstanceState.getString("type")
            tytul = savedInstanceState.getString("tytul")
            //priority = savedInstanceState.getInt("priority")
            time = savedInstanceState.getString("time")


            /*if (priority!=0)
            {
                val buttonID = "pryorytet$priority"
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                findViewById<TextView>(resID).background.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY)
            }*/
            if (type!="")
            {
                val buttonID = type
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                findViewById<ImageButton>(resID).background.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY)
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun add(v: View)
    {
        var editText = findViewById(R.id.tytul) as EditText
        tytul = editText.text.toString()
        //var editText2 = findViewById(R.id.time) as EditText
        //var editText3 = findViewById(R.id.time2) as EditText
        //time = editText2.text.toString().toInt()*3600+editText3.text.toString().toInt()*60
        var timePicker = findViewById(R.id.timePicker1) as TimePicker
        if (timePicker.minute>9) {
            time = timePicker.hour.toString() + ":" + timePicker.minute.toString()
        }
        else {
            time = timePicker.hour.toString() + ":0" + timePicker.minute.toString()
        }
        var a: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar = Calendar.getInstance()
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            calendar.set(
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                timePicker1.getHour(), timePicker1.getMinute(), 0
            )
        } else {
            calendar.set(
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                timePicker1.getCurrentHour(), timePicker1.getCurrentMinute(), 0
            )
        }

        //id = intent.getIntExtra("id",1)
        id = MyDatabase.getInstance(this).MedicineDAO().max() + 1
        val i = Intent(this, MedicineManager::class.java)
        i.putExtra("id", id)
        val pi = PendingIntent.getBroadcast(this, id, i, PendingIntent.FLAG_UPDATE_CURRENT)


        a.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),24*60*60*1000, pi)


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
       /* if (priority==0)
        {
            Toast.makeText(this, "Wybierz pryorytet", Toast.LENGTH_SHORT).show()
            return
        }*/
        if (time=="")
        {
            Toast.makeText(this, "Wybierz czas", Toast.LENGTH_SHORT).show()
            return
        }
       /* intent.putExtra("tytul", tytul.toString())
        intent.putExtra("typ", type)
        intent.putExtra("timer", time)
        //intent.putExtra("pryorytet", priority)*/

        var item = Medicine(id, tytul,time,type/*, priority*/)
        MyDatabase.getInstance(this).MedicineDAO().insert(item)
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

    /*fun pryorytet(v: View)
    {
        priority=findViewById<TextView>(v.getId()).text.toString().toInt()
        for (i in 1..4)
        {
            val buttonID = "pryorytet$i"
            val resID = resources.getIdentifier(buttonID, "id", packageName)
            findViewById<TextView>(resID).background.clearColorFilter()

        }
        findViewById<TextView>(v.getId()).background.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
    }*/

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putString("type", type)
        savedInstanceState.putString("tytul", tytul)
        savedInstanceState.putString("time", time)
        //savedInstanceState.putInt("priority", priority)
        super.onSaveInstanceState(savedInstanceState);
    }
}
