package com.example.dyplom

import android.arch.persistence.room.Room
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask.execute
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View




class MainActivity : AppCompatActivity()
{
    private val PREF_NAME = "pref"

    var listOfMedicine: ArrayList<Medicine> = arrayListOf<Medicine>()
    //private val adapter : Adapter = Adapter(this, listOfMedicine)
    private lateinit var adapter: Adapter
    lateinit  private var pref: SharedPreferences
    private lateinit var mydatabase : MyDatabase

    var typeOfSort="T"

    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*pref = getSharedPreferences(PREF_NAME, MODE_PRIVATE)


        val size= pref.getString("size", "0")
        val time = (System.currentTimeMillis()- pref.getLong("time", 0))/1000
        val gson = Gson()
        for (i in 0..size.toInt()-1)
        {
            val json = pref.getString(i.toString(), "")
            if(json!=null) {
                listOfMedicine.add(gson.fromJson<Medicine>(json, Medicine::class.java))
                listOfMedicine[i].timer= listOfMedicine[i].timer?.minus(time.toInt())
            }
        }



        if (savedInstanceState != null) {
            //     Toast.makeText(this, "bbb", Toast.LENGTH_SHORT).show()
            listOfMedicine = savedInstanceState.getParcelableArrayList("key")
            adapter.updateResults(listOfMedicine)
        }*/

        //val list = findViewById(R.id.list) as ListView
        //list.setAdapter(adapter)
        val recyclerView = findViewById<RecyclerView>(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val context = this
        adapter = Adapter(emptyList(), object : Adapter.ClickListener {
            override fun onItemClick(position: Int) {
                openMedicine(position)

            }
        }, this)
        recyclerView.adapter = adapter

        execute {
            try {
                mydatabase = Room.databaseBuilder(
                    this,
                    MyDatabase::class.java,
                    "tasks.db"
                ).build()
            } catch (e: Exception) {
                Log.i("am2019", e.message)
            }
        }
        listOfMedicine = MyDatabase.getInstance(this).MedicineDAO().getAll() as ArrayList<Medicine>
        adapter.updateResults(listOfMedicine)

        val mActionBarToolbar = findViewById(R.id.my_toolbar) as Toolbar
        setSupportActionBar(mActionBarToolbar)
    }


    fun openMedicine(pos: Int)
    {
        val intent = Intent(this, ShowMedicine::class.java)
        intent.putExtra("id", listOfMedicine[pos].id)
        startActivityForResult(intent, 2)
    }

   /* fun sortByPrioryty()
    {
        var list= listOfMedicine.sortedWith(compareBy({ it.priority }))
        for (i in 0..list.size-1)
            listOfMedicine[i]=list[i]
        adapter.updateResults(listOfMedicine)
    }*/

    fun sortByTime()
    {
        var list= listOfMedicine.sortedWith(compareBy({ it.time }))
        for (i in 0..list.size-1)
            listOfMedicine[i]=list[i]
        adapter.updateResults(listOfMedicine)
    }


    fun add(V: View)
    {
        //val id = MyDatabase.getInstance(this).MedicineDAO().max() + 1
        val intent = Intent(this, AddMedicine::class.java)
        //intent.putExtra("id", id)
        startActivityForResult(intent, 1)
    }

    override fun onStart() {
        super.onStart()
        listOfMedicine = MyDatabase.getInstance(this).MedicineDAO().getAll() as ArrayList<Medicine>
        adapter.updateResults(listOfMedicine)
    }

    override fun onBackPressed() {

    }


    /*override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putParcelableArrayList("key", listOfMedicine)
        super.onSaveInstanceState(savedInstanceState);
    }

    override fun onPause() {
        super.onPause()


        val prefsEditor = pref.edit()
        prefsEditor.putString("size", listOfMedicine.size.toString())
        val gson = Gson()
        var json=""
        for (i in 0..listOfMedicine.size-1)
        {
            json = gson.toJson(listOfMedicine[i])
            prefsEditor.putString(i.toString(), json)

        }
        prefsEditor.putLong("time", System.currentTimeMillis())
        prefsEditor.commit()
    }*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId)
        {
            //R.id.sortByPriority -> {typeOfSort="P"
                //sortByPrioryty()}
            R.id.sortByTime -> {typeOfSort="T"
                sortByTime()}
        }
        return super.onOptionsItemSelected(item)
    }
}
