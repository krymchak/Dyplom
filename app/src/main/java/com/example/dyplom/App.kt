package com.example.dyplom

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import android.os.AsyncTask
import android.util.Log

class App: Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: App? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        // initialize for any

        // Use ApplicationContext.
        // example: SharedPreferences etc...
        val context: Context = getApplicationContext()
    }

    /*private lateinit var mydatabase : MyDatabase

    companion object {
        private lateinit var context: Context
        fun getContext(): Context {
            return context
        }

    }

    private fun startDatabase()
    {
        AsyncTask.execute {
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
    }

    override fun onCreate() {
        super.onCreate()
        context = getApplicationContext()
        startDatabase()
    }*/
}