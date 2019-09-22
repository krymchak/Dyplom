package com.example.dyplom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class TimeAdapter(private val context: Context, list: ArrayList<TimeOfMedicine>) : BaseAdapter() {


    var list: ArrayList<TimeOfMedicine> = ArrayList<TimeOfMedicine>()
    init
    {
        this.list=list
    }
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(i: Int): Any {
        return list[i]
    }

    override fun getItemId(i: Int): Long {
        return list[i].Timeid.toLong()
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.gridview_item, viewGroup, false)
        }

        view!!.findViewById<TextView>(R.id.textView).text = list[i].time

        return view!!
    }


    fun add (timeOfMedicine: TimeOfMedicine)
    {
        list.add(timeOfMedicine)
        sort()


    }


    fun sort()
    {
        val sortList=list.sortedWith(compareByDescending({ it.time }))
        for (i in 0..list.size-1)
            list[i]=sortList[i]
        notifyDataSetChanged()
    }
}