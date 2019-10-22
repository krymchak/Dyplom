package com.example.dyplom.DAO

import android.arch.persistence.room.*
import com.example.dyplom.entity.Medicine

@Dao
interface MedicineDAO {

    @Query("select * from medicine")
    fun getAll() : List<Medicine>

    @Query("SELECT COUNT(*) FROM medicine")
    fun count(): Int

    @Query("SELECT MAX(id) FROM medicine")
    fun max(): Int

    @Query("UPDATE medicine " +
            "SET available_quantity = (SELECT available_quantity-dose FROM medicine WHERE id = :id)," +
            " required_amount = (SELECT required_amount-dose FROM medicine WHERE id = :id)")
    fun reduce(id: Int): Int

    @Query("UPDATE medicine SET available_quantity =:number WHERE id = :id")
    fun add(number: Float, id: Int): Int

    @Query("SELECT * FROM medicine WHERE id = :id")
    fun getById(id: Int): Medicine

    @Insert
    fun insert(medicine: Medicine)

    @Update
    fun update(medicine: Medicine)


    @Query("DELETE FROM medicine WHERE id = :id")
    fun deleteById(id: Int)

}