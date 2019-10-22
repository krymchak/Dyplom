package com.example.dyplom.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.dyplom.R
import com.example.dyplom.adapter.MedicineListAdapter
import com.example.dyplom.entity.Medicine
import com.example.dyplom.presenter.MainPresenter
import com.example.dyplom.view.ListOfMedicineView


class MainActivity : AppCompatActivity(), ListOfMedicineView
{

    val mainPresenter = MainPresenter(this)
    var listOfMedicine: List<Medicine> = arrayListOf()
    private lateinit var medicineListAdapter: MedicineListAdapter


    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mActionBarToolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(mActionBarToolbar)

        showListOfMedicine()
    }

    override fun onStart() {
        super.onStart()
        listOfMedicine = mainPresenter.getListOfMedicine()
        medicineListAdapter.updateResults(listOfMedicine)
    }

    override fun onBackPressed() {
        finish()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId)
        {
            // R.id.sortByName -> {medicineListAdapter.setTypeOfSort("N")}
            //  R.id.sortByTime -> {medicineListAdapter.setTypeOfSort("T")}
        }
        return super.onOptionsItemSelected(item)
    }


    private fun showListOfMedicine()
    {
        val recyclerView = findViewById<RecyclerView>(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        medicineListAdapter =
            MedicineListAdapter(emptyList(), object : MedicineListAdapter.ClickListener {
                override fun onItemClick(position: Int) {
                    openMedicine(position)

                }
            }, this)
        recyclerView.adapter = medicineListAdapter
        listOfMedicine = mainPresenter.getListOfMedicine()
        medicineListAdapter.updateResults(listOfMedicine)
    }



    fun openMedicine(pos: Int)
    {
        mainPresenter.openMedicine(pos)
    }

    override fun toMedicineActicity(pos: Int)
    {
        val intent = Intent(this, ShowMedicineActivity::class.java)
        intent.putExtra("id", listOfMedicine[pos].id)
        startActivityForResult(intent, 2)
    }


    fun onClick(v: View)
    {
        when (v?.id)
        {
             R.id.add -> {mainPresenter.addButtonClicked()}
        }

    }

    override fun toAddMedicineActivity()
    {
        val intent = Intent(this, AddMedicineActivity::class.java)
        startActivityForResult(intent, 1)
    }

}
