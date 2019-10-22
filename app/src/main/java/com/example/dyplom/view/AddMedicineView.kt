package com.example.dyplom.view

import com.example.dyplom.entity.TimeOfMedicine

interface AddMedicineView {
    fun setTime(time: TimeOfMedicine)
    fun addNewMedicine()
    fun getDataFromEditText():Boolean
    fun addTime()
}