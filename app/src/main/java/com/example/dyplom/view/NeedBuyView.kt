package com.example.dyplom.view

interface NeedBuyView {
    /*
     * Funkcja pobierająca informacje o leku
     */
    fun getInformation()
    /*
     * Funkcja do ustawienia informacji o leku
     */
    fun setInformation()
    /*
     * Funkcja do powrotu do widoku głównego
     */
    fun returnToMainActivity()
    /*
   * Funkcja do przejścia do widoku mapy
   */
    fun goToMap()

}