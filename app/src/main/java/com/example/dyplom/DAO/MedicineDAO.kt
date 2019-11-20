package com.example.dyplom.DAO

import android.arch.persistence.room.*
import com.example.dyplom.entity.Medicine

@Dao
interface MedicineDAO {

    /*
     * Funkcja ktora pobiera liste leków z bazy danych
     * return: lista leków
     */
    @Query("select * from medicine")
    fun getAll() : List<Medicine>

    /*
     * Funkcja, która zwraca maksymalne id leków
     * return: maksymalne id leków
     */
    @Query("SELECT MAX(id) FROM medicine")
    fun maxId(): Int

    /*
     * Funkcja ktora zmiejsza ilość dostępnych oraz potrzebnych leków o jedną dawke
     * parametr: id - id zmienianego leku
     */
    @Query("UPDATE medicine " +
            "SET available_quantity = (SELECT available_quantity-dose FROM medicine WHERE id = :id)," +
            " required_amount = (SELECT required_amount-dose FROM medicine WHERE id = :id)")
    fun reduce(id: Int)

    /*
     * Funkcja ktora zmiejsza zwiększa ilość dostępnych leków o podaną ilość
     *  parametr: id - id zmienianego leku
     * parametr: number - ilość dodawanych leków
     */
    @Query("UPDATE medicine SET available_quantity=(SELECT available_quantity+:number FROM medicine WHERE id = :id) WHERE id = :id")
    fun addAvailableQuantity(number: Float, id: Int)

    /*
     * Funkcja ktora pobiera lek z bazy danych po id tego leku
     * parametr: id leku
     * return: lek
     */
    @Query("SELECT * FROM medicine WHERE id = :id")
    fun getById(id: Int): Medicine

    /*
    * Funkcja ktora dodaje lek do bazy danych
    * parametr: medicine - nowy lek
    */
    @Insert
    fun insert(medicine: Medicine)

    /*
    * Funkcja ktora zmienia lek w bazie danych
    * parametr: medicine - lek ze zmienionymi  danymi
    */
    @Update
    fun update(medicine: Medicine)

    /*
    * Funkcja ktora usuwa lek z bazy danych po jego id
    * parametr: id - id usuwanego leku
    */
    @Query("DELETE FROM medicine WHERE id = :id")
    fun deleteById(id: Int)

}