package com.example.foodrunner.RecyclerAdapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.foodrunner.Dataclass.MenuItem
import com.example.foodrunner.R
import com.example.foodrunner.database.CartDatabase
import com.example.foodrunner.database.CartEntity

class MenuAdapter(val context: Context, val itemList : ArrayList<MenuItem>):
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtCost: TextView = view.findViewById(R.id.txtCost)
        val llParent: LinearLayout = view.findViewById(R.id.llParent)
        val btnAdd : Button = view.findViewById(R.id.btnAdd)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_menu,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MenuAdapter.ViewHolder, position: Int) {
        val item = itemList[position]
        holder.txtName.text = item.name
        holder.txtCost.text = "Rs.${item.cost_for_one}"


        val itemEntity = CartEntity(
            item.id.toInt(),
            item.name,
            item.cost_for_one
        )
        val checkItem = CartDb(context, itemEntity, 3).execute().get()
        if(!checkItem){
            holder.btnAdd.setBackgroundColor(context.resources.getColor(R.color.addFav))
            holder.btnAdd.text = "Add"
        }else{
            holder.btnAdd.text = "Remove"
            holder.btnAdd.setBackgroundColor(context.resources.getColor(R.color.removeFav))
        }

        holder.btnAdd.setOnClickListener {

            val checkItem = CartDb(context, itemEntity, 3).execute().get()
            if(checkItem){
                val remove = CartDb(context, itemEntity, 2).execute()
                holder.btnAdd.setBackgroundColor(context.resources.getColor(R.color.addFav))
                holder.btnAdd.text = "Add"
            }else{
                val add = CartDb(context, itemEntity, 1).execute()
                holder.btnAdd.text = "Remove"
                holder.btnAdd.setBackgroundColor(context.resources.getColor(R.color.removeFav))
            }
        }

    }

    class CartDb(val context: Context, val item : CartEntity, val mode : Int) :
        AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {

            val cartDb = Room.databaseBuilder(context, CartDatabase::class.java, "cart-db").build()

            when(mode){

                1->{
                    //Insert
                    val add = cartDb.cartDao().insertInCart(item)
                    cartDb.close()
                    return true
                }

                2->{
                    //Delete
                    val delete = cartDb.cartDao().deleteFromCart(item)
                    cartDb.close()
                    return true
                }

                3->{

                    val item = cartDb.cartDao().getItem(item.id)
                    cartDb.close()
                    return item != null
                }
            }
            return false
        }
    }
}