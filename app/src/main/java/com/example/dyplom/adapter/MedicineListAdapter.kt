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

    /*
     * Typ wyswietlanych leków. Domyślnie ustawiony na "all"
     */
    var typeOfMedicine = "all"
    /*
     * Niepofiltrowana lista leków
     */
    var all = listOf<Medicine>()

    /*
     * Funkcja, która ustawia dane  leku
     * parametr: holder - widok na którym są ustawiane dane
     * parametr: position - pozycja leku w tabele
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = values[position].name
        var listOfTime = MyDatabase.getInstance(context).TimeOfMedicineDAO().getByMedicineID(values[position].id)
        var adapter =
            TimeListAdapter(context, listOfTime as ArrayList<TimeOfMedicine>)
        holder.gridView.setAdapter(adapter)
        holder.gridView.isEnabled = false
        val type = values[position].type
        val resID = context.resources.getIdentifier(type, "drawable", context.packageName)
        holder.image.setImageResource(resID)
    }

    /*
     * Funkcja, która zwraca ilość elementów na liście
     * return: ilość elementów na liście
     */
    override fun getItemCount(): Int {
        return values.size
    }

    /*
     * Funkcja która zwraca id leku znajdującego się na danej pozycji
     * parametr: position - pozycja leku
     */
    fun getId(position: Int): Int {
        return values[position].id
    }

    /*
    * Funkcja, która pobiera widok na którym są ustawiane dane
    * return:  widok na którym są ustawiane dane
    */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return ViewHolder(itemView, clickListener)
    }

    class ViewHolder(view: View, private var clickListener: ClickListener) : RecyclerView.ViewHolder(view), View.OnClickListener {
        /*
         * Pola na których są ustawiane dane leku
         */
        var name: TextView = view.findViewById(R.id.name)
        var gridView = view.findViewById<GridView>(R.id.gridview)
        var image: ImageView = view.findViewById(R.id.image)
        init
        {
            view.setOnClickListener(this)
        }

        /*
         * Funkcja zarządzająca przyciskami
         * parametr: v - element sterujący przyciskiem
         */
        override fun onClick(view: View) {
            clickListener.onItemClick(adapterPosition)
        }
    }

    /*
     * Funkcja updatująca liste leków
     * parametr: list - nowa lista leków
     */
    fun updateList(list: List<Medicine>) {
        all = list
        filter()
    }

    /*
     * Funkcja ustawiająca typ wyswietlanych leków
     * parametr: s - nowy typ
     */
    fun setType(s: String)
    {
        typeOfMedicine=s
        filter()
    }

    /*
     * Funkcja, która filtruje listę leków zgodnie z przechowywanym typem
     */
    fun filter()
    {
        if(typeOfMedicine=="all")
            values=all
        else
            values=all.filter { it.type==typeOfMedicine }
        notifyDataSetChanged()
    }


    interface ClickListener {
        fun onItemClick(position: Int)
    }


}