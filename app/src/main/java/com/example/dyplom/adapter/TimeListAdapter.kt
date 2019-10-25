package com.example.dyplom.adapter

import android.content.Context
import android.os.Parcelable
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


        view!!.findViewById<TextView>(R.id.textView).text = timeToString(list[i].hour, list[i].minute)

        return view
    }

    private fun timeToString(hour:Int, minute:Int): String
    {
        var time = ""
        if (hour<10)
        time="0"+hour
        else
            time+=hour
        time+=":"+minute
        return time

    }


    fun add (timeOfMedicine: TimeOfMedicine)
    {
        list.add(timeOfMedicine)
        sort()
    }

    fun delete (pos: Int)
    {
        list.removeAt(pos)
        sort()
    }

    /*fun update(results: ArrayList<TimeOfMedicine>) {
        list = results
        sort()
    }*/


    fun sort()
    {
        val sortList=list.sortedWith(compareBy ({ it.hour }, { it.minute }))
        for (i in 0 until list.size)
            list[i]=sortList[i]
        notifyDataSetChanged()
    }


    fun setItems(items: ArrayList<TimeOfMedicine>) {
        list=items
        sort()
    }

    fun getItems(): ArrayList<out Parcelable> {
        return list
    }

    fun listIsEmpty(): Boolean {
        return list.isEmpty()
    }
}