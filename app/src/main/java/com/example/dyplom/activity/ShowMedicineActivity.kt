package com.example.dyplom.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import com.example.dyplom.R
import com.example.dyplom.adapter.TimeListAdapter
import com.example.dyplom.entity.Medicine
import com.example.dyplom.entity.TimeOfMedicine
import com.example.dyplom.presenter.ShowMedicinePresenter
import com.example.dyplom.view.ShowMedicineView
import kotlin.collections.ArrayList

class ShowMedicineActivity : AppCompatActivity(), ShowMedicineView {

    val showMedicinePresenter = ShowMedicinePresenter(this)

    lateinit var medicine: Medicine

    var listOfTime: ArrayList<TimeOfMedicine> = arrayListOf()


    var id = 0
    lateinit private var listAdapter : TimeListAdapter
    lateinit var gridView : GridView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_medicine)

        val mActionBarToolbar = findViewById(R.id.my_toolbar) as Toolbar
        setSupportActionBar(mActionBarToolbar)

        getInformation()
        setInformation()

    }

    fun getInformation()
    {
        id = intent.getIntExtra("id",0)
        medicine = showMedicinePresenter.getMedicine(id)
        listOfTime = showMedicinePresenter.getTimeOfMedicine(id) as ArrayList<TimeOfMedicine>
    }

    fun setInformation()
    {
        val name = findViewById<TextView>(R.id.name)
        name.text = medicine.name

        listAdapter = TimeListAdapter(this, listOfTime)
        gridView = findViewById<GridView>(R.id.gridview)
        gridView.setAdapter(listAdapter)

        var image = findViewById(R.id.image) as ImageView
        val resID = this.resources.getIdentifier(medicine.type, "drawable", this.packageName)
        image.setImageResource(resID)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.medicine_menu, menu)
        return true
    }



    override fun addMedicine()
    {
        val intent = Intent(this, AddPopupActivity::class.java)
        intent.putExtra("id", id)
        startActivityForResult(intent, 0)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId)
        {
            R.id.delete -> {showMedicinePresenter.deleteMedicine(id)}
            R.id.edit -> {showMedicinePresenter.editMedicine(id)}
            R.id.add -> {showMedicinePresenter.addMedicine()}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun returnToMainActivity()
    {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}