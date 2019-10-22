package com.example.dyplom.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.dyplom.MyDatabase
import com.example.dyplom.R
import com.example.dyplom.entity.Medicine
import com.example.dyplom.presenter.AddMedicinePresenter
import com.example.dyplom.presenter.NeedBuyPresenter
import com.example.dyplom.view.NeedBuyView

class NeedBuyActivity : AppCompatActivity(), NeedBuyView {

    val needBuyPresenter = NeedBuyPresenter(this)


    var id: Int = 0
    var medicine: Medicine? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.need_buy)

        showInformation()
    }

    private fun showInformation() {
        needBuyPresenter.showMedicine()
    }

    override fun setInformation()
    {
        if(medicine!=null) {
            val number = findViewById<TextView>(R.id.textView)
            number.text = "Zostało leków" + medicine!!.available_quantity.toInt()
        }

    }

    override fun getInformation()
    {
        id = intent.getIntExtra("id",0)
        medicine = needBuyPresenter.getMedicine(id)

}

    fun accept (v: View)
    {
        returnToMainActivity()

    }

    fun cancel (v: View)
    {
        returnToMainActivity()
    }

    fun returnToMainActivity()
    {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}