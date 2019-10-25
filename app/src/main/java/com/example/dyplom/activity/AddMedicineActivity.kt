package com.example.dyplom.activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.example.dyplom.R
import com.example.dyplom.adapter.TimeListAdapter
import com.example.dyplom.entity.TimeOfMedicine
import com.example.dyplom.presenter.AddMedicinePresenter
import com.example.dyplom.view.AddMedicineView
import java.util.*



class AddMedicineActivity : AppCompatActivity(), AddMedicineView {

    val addMedicinePresenter = AddMedicinePresenter(this)

    var id = 0
    var name= ""
    var available_quantity = 0f
    var required_amount = 0f
    var dose = 0f
    //var listOfTime: ArrayList<TimeOfMedicine> = arrayListOf<TimeOfMedicine>()
    var type = ""

    var types= arrayOf("tablet","capsule", "drops", "injection", "ointment", "syrup", "spoon", "spray", "inhalator")

    private val timeListAdapter : TimeListAdapter = TimeListAdapter(this, ArrayList<TimeOfMedicine>())
    lateinit var gridView : GridView

    lateinit var nameEditText: EditText
    lateinit var availableQuantityEditText: EditText
    lateinit var requiredAmountEditText: EditText
    lateinit var doseEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_medicine)

        var listOfTime: ArrayList<TimeOfMedicine> = arrayListOf<TimeOfMedicine>()
        gridView = findViewById<GridView>(R.id.gridview)
        if (savedInstanceState != null) {
            //     Toast.makeText(this, "bbb", Toast.LENGTH_SHORT).show()
            listOfTime = savedInstanceState.getParcelableArrayList("listOfTime")
            timeListAdapter.setItems(listOfTime)
        }

        gridView.setAdapter(timeListAdapter)

        gridView.onItemLongClickListener = AdapterView.OnItemLongClickListener { adapter, arg1, pos, id ->

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Usunac czas?")
            builder.setPositiveButton("Tak", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, arg1: Int) {
                    deleteTime(pos)
                }
            })
            builder.setNegativeButton("Nie", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, arg1: Int) {
                    dialog.cancel()
                }
            })
            val alert = builder.create()
            alert.show()

            true
        };


        findAllView()

    }

    private fun deleteTime(pos: Int) {
        timeListAdapter.delete(pos)
    }

    private fun findAllView()
    {
        nameEditText = findViewById(R.id.tytul) as EditText
        availableQuantityEditText = findViewById(R.id.available_quantity) as EditText
        requiredAmountEditText = findViewById(R.id.required_amount) as EditText
        doseEditText = findViewById(R.id.dose) as EditText
    }




    private fun isAllDataEntered(): Boolean
    {

        if (type=="")
        {
            Toast.makeText(this, "Wybierz typ", Toast.LENGTH_SHORT).show()
            return false
        }
        if (timeListAdapter.listIsEmpty())
        {
            Toast.makeText(this, "Wprowadz czas", Toast.LENGTH_SHORT).show()
            return false
        }
        if (nameEditText.text.toString()=="")
        {
            Toast.makeText(this, "Wpisz tytul", Toast.LENGTH_SHORT).show()
            return false
        }
        if (availableQuantityEditText.text.toString()=="")
        {
            Toast.makeText(this, "Wpisz dostepna liczbe", Toast.LENGTH_SHORT).show()
            return false
        }
        if (requiredAmountEditText.text.toString()=="")
        {
            Toast.makeText(this, "Wpisz ilosc w kursie", Toast.LENGTH_SHORT).show()
            return false
        }
        if (doseEditText.text.toString()=="")
        {
            Toast.makeText(this, "Wpisz dawke", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    override fun getDataFromEditText(): Boolean
    {
        if (!isAllDataEntered())
            return false
        name = nameEditText.text.toString()
        available_quantity = availableQuantityEditText.text.toString().toFloat()
        required_amount = requiredAmountEditText.text.toString().toFloat()
        dose = doseEditText.text.toString().toFloat()
        return true
    }


    override fun addNewMedicine()
    {
        val listOfTime= timeListAdapter.getItems() as List<TimeOfMedicine>
        addMedicinePresenter.addNewMedicine(id, name, type, available_quantity, required_amount, dose, listOfTime)

        val intent = Intent()
        setResult(RESULT_OK, intent)
        finish()
    }


    override fun addTime()
    {
        val calendar = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            addMedicinePresenter.addTime(hour, minute)
        }
        TimePickerDialog(this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
    }

    fun type (v: View)
    {
        var typ = v.resources.getResourceName(v.id).toString()
        type = typ.removePrefix("com.example.dyplom:id/")
        for (i in 0..types.size-1)
        {
            val buttonID = types[i]
            val resID = resources.getIdentifier(buttonID, "id", packageName)
            findViewById<ImageButton>(resID).background.clearColorFilter()

        }
        findViewById<ImageButton>(v.id).background.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY)

    }

    override fun setTime(time:TimeOfMedicine){
        timeListAdapter.add(time)

    }

    fun onClick(v: View)
    {
        when (v?.id)
        {
            R.id.add_time -> {addMedicinePresenter.onAddTimeButtonClick()}
            R.id.add -> {addMedicinePresenter.onAddButtonClick()}
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState!!.putString("type", type)
        val listOfTime= timeListAdapter.getItems()
        outState!!.putParcelableArrayList("listOfTime", listOfTime)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            type = savedInstanceState.getString("type")
            if (type!="") {
                val resID = resources.getIdentifier(type, "id", packageName)
                findViewById<ImageButton>(resID).background.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY)
            }
        }

    }
}
