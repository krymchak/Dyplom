package com.example.dyplom

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey



@Entity(tableName = "medicine")
class Medicine /*: Parcelable*/ {

    @PrimaryKey(autoGenerate = true)
     var id: Int = 0
     var name: String? = null
     var time: String? = null
     var type: String? = null
    // var priority: Int? = null


    constructor(id: Int, name: String, time: String, type: String/*, priority: Int*/) {
        this.id=id
        this.name = name
        this.time = time
        this.type=type
        //this.priority=priority
    }

    /*private constructor(`in`: Parcel) {
        name = `in`.readString()
        timer = `in`.readInt()
        type = `in`.readString()
        priority = `in`.readInt()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "$name: $timer: $type: $priority"
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeString(name)
        out.writeString(timer.toString())
        out.writeString(type)
        out.writeString(priority.toString())
    }

    companion object CREATOR : Parcelable.Creator<Medicine> {
        override fun createFromParcel(parcel: Parcel): Medicine {
            return Medicine(parcel)
        }

        override fun newArray(size: Int): Array<Medicine?> {
            return arrayOfNulls(size)
        }

    }*/

}