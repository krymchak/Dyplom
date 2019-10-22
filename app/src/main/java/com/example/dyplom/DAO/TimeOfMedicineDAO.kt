package com.example.dyplom.DAO

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Delete
import com.example.dyplom.entity.TimeOfMedicine


@Dao
interface TimeOfMedicineDAO {

    @Query("SELECT * FROM timeofmedicine WHERE medicineId = :id")
    fun getByMedicineID(id: Int): List<TimeOfMedicine>

    @Query("SELECT MAX(timeid) FROM timeofmedicine")
    fun max(): Int

    @Insert
    fun insert(times: List<TimeOfMedicine>)


    @Delete
    fun delete(car: TimeOfMedicine)

}