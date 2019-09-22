package com.example.dyplom

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(foreignKeys = arrayOf(ForeignKey(entity = Medicine::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("medicineId"),
    onDelete = ForeignKey.CASCADE)))
class TimeOfMedicine /*: Parcelable*/ {

    @PrimaryKey(autoGenerate = true)
    var Timeid: Int = 0
    var medicineId: Int? = null
    var time: String? = null
    //var type: String? = null
    // var priority: Int? = null


    constructor(Timeid: Int, medicineId: Int, time: String/*, type: String, priority: Int*/) {
        this.Timeid=Timeid
        this.medicineId = medicineId
        this.time = time
        //this.type=type
        //this.priority=priority
    }

}

