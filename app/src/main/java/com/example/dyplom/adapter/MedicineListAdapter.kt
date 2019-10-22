package com.example.dyplom.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import com.example.dyplom.*
import com.example.dyplom.entity.Medicine
import com.example.dyplom.entity.TimeOfMedicine

class MedicineListAdapter(private var values: List<Medicine>, private var clickListener: ClickListener, private var context: Context): RecyclerView.Adapter<MedicineListAdapter.ViewHolder>()
{


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = values[position].name
        var listOfTime = MyDatabase.getInstance(context).TimeOfMedicineDAO().getByMedicineID(values[position].id)
        var adapter =
            TimeListAdapter(context, listOfTime as ArrayList<TimeOfMedicine>)
        holder.gridView.setAdapter(adapter)
        val type = values[position].type
        val resID = context.resources.getIdentifier(type, "drawable", context.packageName)
        holder.image.setImageResource(resID)
    }

    override fun getItemCount(): Int {
        return values.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return ViewHolder(itemView, clickListener)
    }

    class ViewHolder(view: View, private var clickListener: ClickListener) : RecyclerView.ViewHolder(view), View.OnClickListener
    {
        var name: TextView = view.findViewById(R.id.name)
        var gridView = view.findViewById<GridView>(R.id.gridview)
        var image: ImageView = view.findViewById(R.id.image)
        init
        {
            view.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            Log.v("click", "onClick: $adapterPosition")
            clickListener.onItemClick(adapterPosition)
        }
    }

    fun updateResults(results: List<Medicine>) {
        values = results
        notifyDataSetChanged()
    }

    interface ClickListener {
        fun onItemClick(position: Int)
    }


}