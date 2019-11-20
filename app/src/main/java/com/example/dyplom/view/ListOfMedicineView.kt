package com.example.dyplom.view

interface ListOfMedicineView {
    /*
    * Funkcja do przejścia do widoku dodawania leku
    */
    fun toAddMedicineActivity()
    /*
     * Funkcja otwierająca widok z informacją o leku
     * parametr: pos - pozycja leku w liście
     */
    fun toMedicineActicity(pos: Int)
    /*
    * Funkcja do przejścia do widoku mapy
    */
    fun openMap()
    /*
     * Funkcja do przejścia do widoku mapy
     */
    fun openHistory()
}