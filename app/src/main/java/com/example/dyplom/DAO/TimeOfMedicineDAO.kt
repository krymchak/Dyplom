package com.example.dyplom.DAO

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Delete
import com.example.dyplom.entity.TimeOfMedicine


@Dao
interface TimeOfMedicineDAO {

    /*
     * Funkcja ktora pobiera czas z bazy danych po id leku
     * parametr: id leku
     * return: czas leku
     */
    @Query("SELECT * FROM timeofmedicine WHERE medicineId = :id")
    fun getByMedicineID(id: Int): List<TimeOfMedicine>

    /*
     * Funkcja, która zwraca maksymalne id czasów
     * return: maksymalne id czasów
     */
    @Query("SELECT MAX(timeid) FROM timeofmedicine")
    fun maxId(): Int

    /*
    * Funkcja ktora dodaje listę czasów leku do bazy danych
    * parametr: times - lista czasów leku
    */
    @Insert
    fun insert(times: List<TimeOfMedicine>)

    /*
    * Funkcja ktora usuwa listę czasów leku do bazy danych
    * parametr: times - usuwana lista czasów leku
    */
    @Delete
    fun delete(times: List<TimeOfMedicine>)

    /*
     * Funkcja ktora pobiera czas z bazy danych po id tego czasu
     * parametr: id czasu
     * return: czas leku
     */
    @Query("SELECT * FROM timeofmedicine WHERE timeId = :id")
    fun getById(id: Int): TimeOfMedicine

}