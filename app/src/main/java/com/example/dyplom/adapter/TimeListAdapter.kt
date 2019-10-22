package com.example.dyplom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.dyplom.R
import com.example.dyplom.entity.Medicine
import com.example.dyplom.entity.TimeOfMedicine

class TimeListAdapter(private val context: Context, list: ArrayList<TimeOfMedicine>) : BaseAdapter() {


    var list: ArrayList<TimeOfMedicine> = ArrayList()
    init
    {
        this.list=list
        sort()
    }
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(i: Int): Any {
        return list[i]
    }

    override fun getItemId(i: Int): Long {
        return list[i].TimeId.toLong()
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.gridview_item, viewGroup, false)
        }

        view!!.findViewById<TextView>(R.id.textView).text = list[i].time

        return view
    }


    /*fun add (timeOfMedicine: TimeOfMedicine)
    {
        list.add(timeOfMedicine)
        sort()
    }*/

    fun update(results: ArrayList<TimeOfMedicine>) {
        list = results
        notifyDataSetChanged()
    }


    fun sort()
    {
        val sortList=list.sortedWith(compareBy { it.time })
        for (i in 0 until list.size)
            list[i]=sortList[i]
        notifyDataSetChanged()
    }
}