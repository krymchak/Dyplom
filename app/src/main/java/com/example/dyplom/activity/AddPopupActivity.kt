package com.example.dyplom.activity

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.dyplom.R
import com.example.dyplom.presenter.AddPopupPresenter
import com.example.dyplom.view.AddPopupView

class AddPopupActivity: Activity(), AddPopupView{

    val addPopupPresenter = AddPopupPresenter(this)

    // Zmienne do przechowywania wprowadzonych danych
    var id = 0
    var number: Float = 0f

    /*
     * Funkcja do tworzenia widoku
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_popup)

        setSizeWindow()
        id = intent.getIntExtra("id",0)
    }

    /*
     * Funkcja do ustawienia rozmiarów widoku
     */
    private fun setSizeWindow()
    {
        var dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        var width = dm.widthPixels
        var height = dm.heightPixels
        window.setLayout((width*0.6).toInt(), (height*0.3).toInt())
    }

    /*
     * Funkcja, pobierające dane o dodawanej ilości leku
     */
    override fun getData()
    {
        var editText = findViewById(R.id.number) as EditText
        if (editText.text.toString()=="")
        {
            Toast.makeText(this, "Wpisz ilosc", Toast.LENGTH_SHORT).show()
            return
        }
        number = editText.text.toString().toFloat()
    }

    /*
     * Funkcja, zapisująca dane o dodawanej ilości leku
     * Po jej wykonaniu aktywność konczy swoje działanie
     */
    override fun setData()
    {
        addPopupPresenter.addAvailableQuantity(number, id)
        finish()
    }

    /*
     * Funkcja, po wykonaniu której aktywność konczy swoje działanie
     */
    override fun cancel()
    {
        finish()
    }

    /*
     * Funkcja zarządzająca przyciskami
     * parametr: v - element sterujący przyciskiem
     */
    fun onClick(v: View)
    {
        when (v?.id)
        {
            R.id.add -> {addPopupPresenter.onAddButtonClick()}
            R.id.cancel -> {addPopupPresenter.onCancelButtonClick()}
        }
    }
}