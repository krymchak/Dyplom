package com.example.dyplom

import android.arch.persistence.room.*


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

    @Update
    fun update(medicine: Medicine)

    //@Delete
    //fun delete(medicine: Medicine)

    @Query("DELETE FROM medicine WHERE id = :id")
    fun deleteById(id: Int)

}