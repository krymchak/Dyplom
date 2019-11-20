package com.example.dyplom.presenter

import com.example.dyplom.App
import com.example.dyplom.MyDatabase
import com.example.dyplom.entity.Medicine
import com.example.dyplom.entity.TimeOfMedicine
import com.example.dyplom.view.AddMedicineView
import com.example.dyplom.view.ShowMedicineView

class ShowMedicinePresenter{
    val context = App.applicationContext()
    val medicineDAO = MyDatabase.getInstance(context).MedicineDAO()
    val timeOfMedicineDAO = MyDatabase.getInstance(context).TimeOfMedicineDAO()
    val showMedicineView: ShowMedicineView

    constructor(showMedicineView: ShowMedicineView)
    {
        this.showMedicineView=showMedicineView
    }

    fun getMedicine(id: Int): Medicine{
        return medicineDAO.getById(id)
    }

    fun getTimeOfMedicine(id: Int): List<TimeOfMedicine>{
        return timeOfMedicineDAO.getByMedicineID(id)
    }

    fun deleteMedicine(id: Int){
        medicineDAO.deleteById(id)
        showMedicineView.returnToMainActivity()
    }

    fun editMedicine(id: Int){
        showMedicineView.edit(id)
    }

    fun addAvailableQuantity() {
        showMedicineView.addAvailableQuantity()
    }
}