package com.example.dyplom

import android.arch.persistence.room.*
import android.arch.persistence.room.Transaction

@Dao
interface MedicineDAO {

    @Query("select * from medicine")
    fun getAll() : List<Medicine>

    @Query("SELECT COUNT(*) FROM medicine")
    fun count(): Int

    @Query("SELECT MAX(id) FROM medicine")
    fun max(): Int

    @Query("SELECT * FROM medicine WHERE id = :id")
    fun getById(id: Int): Medicine

    @Insert
    fun insert(medicine: Medicine)

    //@Insert
    //fun insertTime(timeOfMedicine: List<TimeOfMedicine>)


    @Update
    fun update(medicine: Medicine)

    //@Delete
    //fun delete(medicine: Medicine)

    /*@Transaction
    fun insertCarAndEmployee(car: Car, employee: Employee) {
        insertCar(car)
        insertEmployee(employee)
    }*/

    @Query("DELETE FROM medicine WHERE id = :id")
    fun deleteById(id: Int)

}