package com.example.dyplom.presenter

import com.example.dyplom.App
import com.example.dyplom.MyDatabase
import com.example.dyplom.entity.Medicine
import com.example.dyplom.view.ListOfMedicineView

class MainPresenter {

    val medicineDAO = MyDatabase.getInstance(App.applicationContext()).MedicineDAO()
    val listOfMedicineView: ListOfMedicineView

    constructor(listOfMedicineView: ListOfMedicineView)
    {
        this.listOfMedicineView=listOfMedicineView
    }


    fun getListOfMedicine(): List<Medicine>
    {
        return medicineDAO.getAll()
    }

    fun openMedicine(pos: Int)
    {
        listOfMedicineView.toMedicineActicity(pos)
    }

    fun addButtonClicked() {
        listOfMedicineView.toAddMedicineActivity()
    }

    fun addMapClicked() {
        listOfMedicineView.openMap()
    }

    fun historyClicked() {
        listOfMedicineView.openHistory()
    }


}