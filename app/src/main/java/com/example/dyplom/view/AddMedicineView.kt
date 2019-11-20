package com.example.dyplom.view

import com.example.dyplom.entity.TimeOfMedicine

interface AddMedicineView {
    /*
     * Funkcja dodająca czas do gridView
     * parametr: time - dodawany czas
     */
    fun setTime(time: TimeOfMedicine)
    /*
     * Funkcja tworząca nowy lek.
     * Po jej wykonaniu aktywność konczy swoje działanie
     */
    fun addNewMedicine()
    /*
     * Funkcja, pobierające dane leku z pól i zapisująca ich do odpowiednich zmiennych
     * return: true - wszystkie potrzebne dane zostały wprowadzone, false - jakieś dane nie są wprowadzone
     */
    fun getDataFromEditText(): Boolean
    /*
     * Funkcja dodająca czas przyjmowania leku
     */
    fun addTime()
}