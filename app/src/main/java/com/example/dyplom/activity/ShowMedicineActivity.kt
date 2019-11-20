package com.example.dyplom.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
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


    private val showMedicinePresenter = ShowMedicinePresenter(this)

    // lek
    private lateinit var medicine: Medicine

    // lista czasów przyjmowania leku
    private var listOfTime: ArrayList<TimeOfMedicine> = arrayListOf()

    // id leku
    private var id = 0

    // GridView do pokazania wprowadzonych czasów oraz adapter do niego
    private lateinit var gridView : GridView
    private lateinit var listAdapter : TimeListAdapter

    /*
    * Funkcja do tworzenia widoku
    */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_medicine)

        val mActionBarToolbar = findViewById(R.id.my_toolbar) as Toolbar
        setSupportActionBar(mActionBarToolbar)

        getInformation()
        setInformation()

    }

    /*
     * Funkcja pobierająca informacje o leku
     */
    private fun getInformation()
    {
        id = intent.getIntExtra("id",0)
        medicine = showMedicinePresenter.getMedicine(id)
        listOfTime = showMedicinePresenter.getTimeOfMedicine(id) as ArrayList<TimeOfMedicine>
    }

    /*
     * Funkcja do ustawienia informacji o leku
     */
    private fun setInformation()
    {
        val name = findViewById<TextView>(R.id.name)
        name.text = medicine.name

        val available_quantity = findViewById<TextView>(R.id.available_quantity)
        available_quantity.text = medicine.available_quantity.toString()

        val required_amount = findViewById<TextView>(R.id.required_amount)
        required_amount.text = medicine.required_amount.toString()

        listAdapter = TimeListAdapter(this, listOfTime)
        gridView = findViewById<GridView>(R.id.gridview)
        gridView.setAdapter(listAdapter)



        var image = findViewById(R.id.image) as ImageView
        val resID = this.resources.getIdentifier(medicine.type, "drawable", this.packageName)
        image.setImageResource(resID)

    }

    /*
     * Funkcja do tworzenia menu
     * parametr: menu - tworzone menu
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.medicine_menu, menu)
        return true
    }

    /*
     * Funkcja do przejścia do widoku AddPopup
     */
    override fun addAvailableQuantity()
    {
        val intent = Intent(this, AddPopupActivity::class.java)
        intent.putExtra("id", id)
        startActivityForResult(intent, 0)
    }

    /*
     * Funkcja do zarządzania elementami menu
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId)
        {
            R.id.delete -> {showDialog() }
            R.id.edit -> {showMedicinePresenter.editMedicine(id)}
            R.id.add -> {showMedicinePresenter.addAvailableQuantity()}
        }
        return super.onOptionsItemSelected(item)
    }

    /*
     * Funkcja do powrotu do widoku głównego
     */
    override fun returnToMainActivity()
    {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    /*
     * Funkcja do przejścia do widoku edytowania leku
     * parametr: id - id edytowanego leku
     */
    override fun edit(id: Int) {
        val intent = Intent(this, EditMedicineActivity::class.java)
        intent.putExtra("id", id)
        startActivityForResult(intent, 0)
    }

    /*
     * Funkcja odswieżająca dane po pokazaniu AddPopup
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        getInformation()
        setInformation()
    }

    /*
     * Funkcja pokazująca dialog z pytaniem o usuwaniu leku
     */
    private fun showDialog(){
        lateinit var dialog: AlertDialog

        val builder = AlertDialog.Builder(this)

        builder.setTitle("Usunąc lek?")

        val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> showMedicinePresenter.deleteMedicine(id)
            }
        }

        builder.setPositiveButton("TAK",dialogClickListener)
        builder.setNegativeButton("NIE",dialogClickListener)
        dialog = builder.create()
        dialog.show()
    }
}