package com.example.foodrunner.RecyclerAdapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrunner.Activity.RestaurantAcitivty
import com.example.foodrunner.Dataclass.Restaurant
import com.example.foodrunner.R
import com.squareup.picasso.Picasso

class RecyclerAdapter(val context : Context, val itemList : ArrayList<Restaurant>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtPrice: TextView = view.findViewById(R.id.txtCost)
        val txtRating: TextView = view.findViewById(R.id.txtRating)
        val imgImage: ImageView = view.findViewById(R.id.imgImage)
        val llParent: LinearLayout = view.findViewById(R.id.llParent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_list_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = itemList[position]
        holder.txtName.text = restaurant.name
        holder.txtPrice.text = restaurant.price
        holder.txtRating.text = restaurant.rating
        Picasso.get().load(restaurant.image).into(holder.imgImage)
        holder.llParent.setOnClickListener {
            Toast.makeText(context, "Clicked on ${holder.txtName.text}", Toast.LENGTH_SHORT ).show()
            val intent = Intent(context, RestaurantAcitivty::class.java )
            intent.putExtra("restaurant_id", restaurant.id)
            context.startActivity(intent)

        }
    }
}