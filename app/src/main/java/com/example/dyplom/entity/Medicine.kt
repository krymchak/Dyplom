package com.example.dyplom.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.util.Log


@Entity(tableName = "medicine")
class Medicine  {

    @PrimaryKey(autoGenerate = true)
     var id: Int = 0
     var name: String? = null
    var type: String? = null
    var available_quantity: Float = 10f
    var required_amount: Float = 10f
    var  dose: Float = 1f


    constructor(id: Int, name: String, type: String, available_quantity: Float, required_amount: Float, dose: Float) {
        this.id=id
        this.name = name
        this.type=type
        this.available_quantity=available_quantity
        this.required_amount = required_amount
        this.dose=dose
    }

    fun numberOfAvailableReceptions(): Float
    {
        return available_quantity/dose;
    }

    fun numberOfRequiredReceptions(): Float
    {
        return required_amount/dose;
    }

    fun isNeedBuy(): Boolean
    {
        if (numberOfAvailableReceptions()<numberOfRequiredReceptions() && numberOfAvailableReceptions()<=6)
            return true
        return false
    }


}
