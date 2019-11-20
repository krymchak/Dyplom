package com.example.dyplom.view

interface AddPopupView {
    /*
     * Funkcja, pobierające dane o dodawanej ilości leku
     */
    fun getData()
    /*
     * Funkcja, zapisująca dane o dodawanej ilości leku
     * Po jej wykonaniu aktywność konczy swoje działanie
     */
    fun setData()
    /*
     * Funkcja, po wykonaniu której aktywność konczy swoje działanie
     */
    fun cancel()

}
