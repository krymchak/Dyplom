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


    /*
     * Lista z czasami przyjmowania leku
     */
    var list: ArrayList<TimeOfMedicine> = ArrayList()

    init
    {
        this.list=list
        sort()
    }

    /*
     * Funkcja, która zwraca ilość elementów na liście
     * return: ilość elementów na liście
     */
    override fun getCount(): Int {
        return list.size
    }

    /*
     * Funkcja, która zwraca czas z podanej pozycji
     * parametr: i - pozyjcja czasu
     * return: czas z podanej pozycji
     */
    override fun getItem(i: Int): Any {
        return list[i]
    }

    /*
     * Funkcja, która zwraca id czasu z podanej pozycji
     * parametr: i - pozyjcja czasu
     * return: id czasu z podanej pozycji
     */
    override fun getItemId(i: Int): Long {
        return list[i].timeId.toLong()
    }

    /*
     * Funkcja, która ustawia dane czasu na widoku i zwraca ten widok
     * parametr: i - pozycja czasu
     * parametr: view - widik, na którym są ustawiane dane
     * return: widok z ustawionymi danymi
     */
    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.gridview_item, viewGroup, false)
        }


        view!!.findViewById<TextView>(R.id.textView).text = timeToString(list[i].hour, list[i].minute)

        return view
    }

    /*
     * Funkcja, która konwertuje godzine i minuty czasu przyjmowania leku do String
     * parametr: hour - godzina
     * parametr: minute - minuty
     * return: czas przyjmowania leku w String
     */
    private fun timeToString(hour:Int, minute:Int): String
    {
        var time = hour.toString() + ":"
        if (minute<10)
        time+="0"+minute
        else
            time+=minute
        return time

    }

    /*
     * Funkcja, która dodaje czas przyjmowania leku do listy
     * parametr: timeOfMedicine - dodawany czas przyjmowania leku
     */
    fun add (timeOfMedicine: TimeOfMedicine)
    {
        list.add(timeOfMedicine)
        sort()
    }

    /*
     * Funkcja, która usuwa czas przyjmowania leku z listy
     * parametr: pos - pozycja usuwanego czasu przyjmowania leku
     */
    fun delete (pos: Int)
    {
        list.removeAt(pos)
        sort()
    }

    /*
     * Funkcja, która sortuje liste czasów przyjmowania leku od rosnąco
     */
    fun sort()
    {
        val sortList=list.sortedWith(compareBy ({ it.hour }, { it.minute }))
        for (i in 0 until list.size)
            list[i]=sortList[i]
        notifyDataSetChanged()
    }

    /*
     * Funkcja updatująca liste czasów
     * parametr: items - nowa lista czasów
     */
    fun upadateList(items: ArrayList<TimeOfMedicine>) {
        list=items
        sort()
    }

    /*
     * Funkcja ktora zwraca liste czasów
     * return:  lista czasów
     */
    fun getItems(): ArrayList<out Parcelable> {
        return list
    }

    /*
     * Funkcja ktora sprawdza czy lista czasów jest pusta
     * return:  true - jesli lista jest pusta, false - jesli lista nie jest pusta
     */
    fun listIsEmpty(): Boolean {
        return list.isEmpty()
    }
}