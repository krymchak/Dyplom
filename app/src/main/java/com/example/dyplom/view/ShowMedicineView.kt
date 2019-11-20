package com.example.dyplom.view

import com.example.dyplom.entity.TimeOfMedicine

interface ShowMedicineView {
    /*
     * Funkcja do powrotu do widoku głównego
     */
    fun returnToMainActivity()
    /*
    * Funkcja do przejścia do widoku AddPopup
    */
    fun addAvailableQuantity()
    /*
     * Funkcja do przejścia do widoku edytowania leku
     * parametr: id - id edytowanego leku
     */
    fun edit(id: Int)
}