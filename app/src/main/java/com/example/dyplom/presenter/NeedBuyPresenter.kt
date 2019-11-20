package com.example.dyplom.presenter

import com.example.dyplom.App
import com.example.dyplom.MyDatabase
import com.example.dyplom.entity.Medicine
import com.example.dyplom.view.AddMedicineView
import com.example.dyplom.view.NeedBuyView

class NeedBuyPresenter {

    val context = App.applicationContext()
    val medicineDAO = MyDatabase.getInstance(context).MedicineDAO()
    val timeOfMedicineDAO = MyDatabase.getInstance(context).TimeOfMedicineDAO()
    val needBuyView: NeedBuyView

    constructor(needBuyView: NeedBuyView)
    {
        this.needBuyView=needBuyView
    }

    fun showMedicine() {
        needBuyView.getInformation()
        needBuyView.setInformation()
    }

    fun getMedicine(id: Int): Medicine? {
        return medicineDAO.getById(id)
    }

    fun onAcceptButtonClick() {
        needBuyView.returnToMainActivity()
    }

    fun onLaterButtonClick() {
        needBuyView.goToMap()
    }

}