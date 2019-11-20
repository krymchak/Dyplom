package com.example.dyplom.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity(foreignKeys = arrayOf(ForeignKey(entity = Medicine::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("medicineId"),
    onDelete = ForeignKey.CASCADE)))
class TimeOfMedicine(@PrimaryKey(autoGenerate = true) var timeId: Int, medicineId: Int, hour: Int, minute: Int) : Parcelable {

    var medicineId: Int? = medicineId
    //var time: String? = time
    var hour: Int=hour
    var minute: Int=minute



    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
        medicineId = parcel.readValue(Int::class.java.classLoader) as? Int
        hour = parcel.readInt()
        minute= parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(timeId)
        parcel.writeValue(medicineId)
        parcel.writeInt(hour)
        parcel.writeInt(minute)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TimeOfMedicine> {
        override fun createFromParcel(parcel: Parcel): TimeOfMedicine {
            return TimeOfMedicine(parcel)
        }

        override fun newArray(size: Int): Array<TimeOfMedicine?> {
            return arrayOfNulls(size)
        }
    }


}

