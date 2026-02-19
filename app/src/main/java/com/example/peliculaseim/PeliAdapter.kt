package com.example.peliculaseim

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class PeliAdapter(private val context: Activity, private val arraylist: ArrayList<Peliculas>): ArrayAdapter<Peliculas>(context, R.layout.item, arraylist) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.item, null)
        view.findViewById<TextView>(R.id.itemNombre).text = arraylist[position].nombre.toString()
        view.findViewById<TextView>(R.id.itemGenero).text = arraylist[position].genero.toString()
        view.findViewById<TextView>(R.id.itemAnio).text = arraylist[position].anio.toString()
        return view

        //return super.getView(position, convertView, parent)
    }
}