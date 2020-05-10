package com.example.foodrunner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(val context : Context, val itemList : ArrayList<String>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val txtview: TextView = view.findViewById(R.id.txtRow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_list_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val text = itemList[position]
        holder.txtview.text = text
    }
}