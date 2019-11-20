package com.example.dyplom.presenter

import com.example.dyplom.App
import com.example.dyplom.MyDatabase
import com.example.dyplom.entity.Medicine
import com.example.dyplom.entity.TimeOfMedicine
import com.example.dyplom.view.AddPopupView
import com.example.dyplom.view.ShowMedicineView

class AddPopupPresenter{
    val context = App.applicationContext()
    val medicineDAO = MyDatabase.getInstance(context).MedicineDAO()
    val addPopupView: AddPopupView

    constructor(addPopupView: AddPopupView)
    {
        this.addPopupView=addPopupView
    }

    fun onAddButtonClick() {
        addPopupView.getData()
        addPopupView.setData()
    }

    fun addAvailableQuantity(number: Float, id: Int) {
        medicineDAO.addAvailableQuantity(number, id)
    }

    fun onCancelButtonClick() {
        addPopupView.cancel()
    }
}