package com.example.foodrunner.RecyclerAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrunner.Dataclass.MenuItem
import com.example.foodrunner.R

class MenuAdapter(val context: Context, val itemList : ArrayList<MenuItem>):
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtCost: TextView = view.findViewById(R.id.txtCost)
        val llParent: LinearLayout = view.findViewById(R.id.llParent)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_menu,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MenuAdapter.ViewHolder, position: Int) {
        val item = itemList[position]
        holder.txtName.text = item.name
        holder.txtCost.text = item.cost_for_one
    }
}