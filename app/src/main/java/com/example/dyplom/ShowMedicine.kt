package com.example.dyplom

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView

class ShowMedicine : AppCompatActivity() {

    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_medicine)

        val mActionBarToolbar = findViewById(R.id.my_toolbar) as Toolbar
        setSupportActionBar(mActionBarToolbar)

        setInformation()


    }

    fun setInformation()
    {
        id = intent.getIntExtra("id",0)
        val medicine = MyDatabase.getInstance(this).MedicineDAO().getById(id)

        val name = findViewById<TextView>(R.id.name)
        name.text = medicine.name

        val time = findViewById<TextView>(R.id.time)
        time.text = medicine.time

        var image = findViewById(R.id.image) as ImageView
        val resID = this.resources.getIdentifier(medicine.type, "drawable", this.packageName)
        image.setImageResource(resID)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.medicine_menu, menu)
        return true
    }

    fun deleteMedicine()
    {
        MyDatabase.getInstance(this).MedicineDAO().deleteById(id)
        returnToMainActivity()
    }

    fun editMedicine()
    {

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId)
        {
            R.id.delete -> {deleteMedicine()}
            R.id.edit -> {editMedicine()}
        }
        return super.onOptionsItemSelected(item)
    }

    fun returnToMainActivity()
    {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}