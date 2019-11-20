package com.example.dyplom.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.dyplom.R
import com.example.dyplom.adapter.MedicineListAdapter
import com.example.dyplom.entity.Medicine
import com.example.dyplom.presenter.MainPresenter
import com.example.dyplom.view.ListOfMedicineView


class MainActivity : AppCompatActivity(), ListOfMedicineView {

    val mainPresenter = MainPresenter(this)

    // lista leków
    var listOfMedicine: List<Medicine> = arrayListOf()

    // adapter do wyswietlania listy leków
    private lateinit var medicineListAdapter: MedicineListAdapter

    // zmienna przechowująca dane o tym, czy przycisk powrotu był już wcisnięty
    private var doubleBackToExitPressedOnce = false


    /*
     * Funkcja do tworzenia widoku
     */
    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mActionBarToolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(mActionBarToolbar)
        showListOfMedicine()
    }

    /*
     * Funkcja do przejścia do widoku mapy
     */
    override fun openHistory() {
        val intent = Intent(this, HistoryActivity::class.java)
        startActivityForResult(intent, 2)
    }

    /*
     * Funkcja pobierająca dane po odswieżeniu widoku
     */
    override fun onStart() {
        super.onStart()
        listOfMedicine = mainPresenter.getListOfMedicine()
        medicineListAdapter.updateList(listOfMedicine)
    }

    /*
     * Funkcja wywolująca się przy wcisnięciu przycisku back
     * Gdy przycisk jest wcisnięty pierwszy raz wyswietla komunikat
     * Gdy przycisk jest wcisnięty drugi raz zamyka apkikacje
     */
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

    /*
     * Funkcja do tworzenia menu
     * parametr: menu - tworzone menu
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    /*
     * Funkcja do zarządzania elementami menu
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onOptionsItemSelected(item)
        when (item?.itemId)
        {
            // R.id.sortByName -> {medicineListAdapter.setTypeOfSort("N")}
            // R.id.sortByTime -> {medicineListAdapter.setTypeOfSort("T")}
            R.id.show_all -> {medicineListAdapter.setType("all")}
            R.id.tablet -> {medicineListAdapter.setType("tablet")}
            R.id.drops -> {medicineListAdapter.setType("drops")}
            R.id.injection -> {medicineListAdapter.setType("injection")}
            R.id.ointment -> {medicineListAdapter.setType("ointment")}
            R.id.spoon -> {medicineListAdapter.setType("spoon")}
            R.id.spray -> {medicineListAdapter.setType("spray")}

            R.id.map -> {mainPresenter.addMapClicked()}
            R.id.history -> {mainPresenter.historyClicked()}

        }
        return super.onOptionsItemSelected(item)
    }

    /*
    * Funkcja do wyswietlenia listy leków
     */
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
        medicineListAdapter.updateList(listOfMedicine)
    }


    /*
     * Funkcja pokazująca lek z listy
     * parametr: id - id leku
     */
    fun openMedicine(id: Int)
    {
        mainPresenter.openMedicine(id)
    }

    /*
     * Funkcja otwierająca widok z informacją o leku
     * parametr: pos - pozycja leku w liście
     */
    override fun toMedicineActicity(pos: Int)
    {
        val intent = Intent(this, ShowMedicineActivity::class.java)
        intent.putExtra("id", medicineListAdapter.getId(pos))
        startActivityForResult(intent, 2)
    }

    /*
     * Funkcja zarządzająca przyciskami
     * parametr: v - element sterujący przyciskiem
     */
    fun onClick(v: View)
    {
        when (v?.id)
        {
             R.id.add -> {mainPresenter.addButtonClicked()}

        }

    }

    /*
    * Funkcja do przejścia do widoku dodawania leku
    */
    override fun toAddMedicineActivity()
    {
        val intent = Intent(this, AddMedicineActivity::class.java)
        startActivityForResult(intent, 1)
    }

    /*
    * Funkcja do przejścia do widoku mapy
    */
    override fun openMap() {
        val intent = Intent(this, MapsActivity::class.java)
        startActivityForResult(intent, 1)
    }

}
