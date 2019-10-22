package com.example.dyplom.activity

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.dyplom.MyDatabase
import com.example.dyplom.R
import com.example.dyplom.presenter.AddMedicinePresenter
import com.example.dyplom.presenter.AddPopupPresenter
import com.example.dyplom.view.AddPopupView

public class AddPopupActivity: Activity(), AddPopupView{

    val addPopupPresenter = AddPopupPresenter(this)


    var id = 0
    var number: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_popup)

        setSizeWindow()
        id = intent.getIntExtra("id",0)
    }

    private fun setSizeWindow()
    {
        var dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        var width = dm.widthPixels
        var height = dm.heightPixels
        window.setLayout((width*0.6).toInt(), (height*0.3).toInt())
    }


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

    override fun setData()
    {
        addPopupPresenter.addAvailableQuantity(number, id)
        finish()
    }

    override fun cancel()
    {
        finish()
    }

    fun onClick(v: View)
    {
        when (v?.id)
        {
            R.id.add -> {addPopupPresenter.onAddButtonClick()}
            R.id.cancel -> {addPopupPresenter.onCancelButtonClick()}
        }
    }
}