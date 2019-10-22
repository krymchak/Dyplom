package com.example.dyplom.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(foreignKeys = arrayOf(ForeignKey(entity = Medicine::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("medicineId"),
    onDelete = ForeignKey.CASCADE)))
class TimeOfMedicine(@PrimaryKey(autoGenerate = true) var TimeId: Int, medicineId: Int, time: String) /*: Parcelable*/ {

    var medicineId: Int? = medicineId
    var time: String? = time


}

