package com.example.dyplom

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class Adapter(private var values: List<Medicine>, private var clickListener: ClickListener, private var context: Context): RecyclerView.Adapter<Adapter.ViewHolder>()
{
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = values[position].name
        holder.time.text = values[position].time
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
        var time: TextView = view.findViewById(R.id.timer)
        //var type : TextView = view.findViewById(R.id.type)
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