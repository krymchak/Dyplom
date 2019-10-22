package com.example.dyplom

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.content.Context
import com.example.dyplom.DAO.MedicineDAO
import com.example.dyplom.DAO.TimeOfMedicineDAO
import com.example.dyplom.entity.Medicine
import com.example.dyplom.entity.TimeOfMedicine


//@Database(entities = [Medicine::class, TimeOfMedicine::class], version = 14)
@Database(entities = [Medicine::class, TimeOfMedicine::class], version = 6)
abstract class MyDatabase : RoomDatabase() {
    abstract fun MedicineDAO(): MedicineDAO
    abstract fun TimeOfMedicineDAO(): TimeOfMedicineDAO

    companion object {

        @Volatile private var INSTANCE: MyDatabase? = null

        fun getInstance(context: Context): MyDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                MyDatabase::class.java, "tasks.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }
}