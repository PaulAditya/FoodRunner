package com.example.foodrunner.RecyclerAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrunner.R
import com.example.foodrunner.database.RestaurantEntity
import com.squareup.picasso.Picasso

class FavRestaurantRecycler(val context : Context, val restaurantList : List<RestaurantEntity>): RecyclerView.Adapter<FavRestaurantRecycler.ViewHolder>(){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val txtBookName: TextView = view.findViewById(R.id.txtName)
        val txtBookPrice: TextView = view.findViewById(R.id.txtCost)
        val txtBookRating: TextView = view.findViewById(R.id.txtRating)
        val imgBookImage: ImageView = view.findViewById(R.id.imgImage)
        val llContent: LinearLayout = view.findViewById(R.id.llContent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_fav_book, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = restaurantList[position]
        holder.txtBookName.text = book.name
        holder.txtBookPrice.text = book.price
        holder.txtBookRating.text = book.rating
        Picasso.get().load(book.image).into(holder.imgBookImage)

    }


}