package com.example.foodrunner.RecyclerAdapter

import android.content.Context
import android.content.Intent
import android.media.Image
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.foodrunner.Activity.HomeActivity
import com.example.foodrunner.Activity.RestaurantAcitivty
import com.example.foodrunner.Dataclass.Restaurant
import com.example.foodrunner.Fragment.Dashboard
import com.example.foodrunner.R
import com.example.foodrunner.database.RestaurantDatabase
import com.example.foodrunner.database.RestaurantEntity
import com.squareup.picasso.Picasso
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import kotlin.Exception

class RecyclerAdapter(val context : Context, val itemList : ArrayList<Restaurant>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtPrice: TextView = view.findViewById(R.id.txtCost)
        val txtRating: TextView = view.findViewById(R.id.txtRating)
        val imgImage: ImageView = view.findViewById(R.id.imgImage)
        val rlParent: RelativeLayout = view.findViewById(R.id.rlParent)
        val imgFav: ImageView = view.findViewById(R.id.imgFav)
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
        holder.txtPrice.text = "Rs.${restaurant.price}"
        holder.txtRating.text = restaurant.rating
        Picasso.get().load(restaurant.image).into(holder.imgImage)
        holder.rlParent.setOnClickListener {
            val intent = Intent(context, RestaurantAcitivty::class.java )
            intent.putExtra("restaurant_id", restaurant.id)
            context.startActivity(intent)
        }
        val res = RestaurantEntity(
            restaurant.id.toInt(),
            restaurant.name,
            restaurant.price,
            restaurant.rating,
            restaurant.image
        )

        val inFav = HomeActivity.Dbasync(context, res, 1 ).execute().get()
        if(inFav){
            holder.imgFav.setImageResource(R.drawable.ic_favorite_black_24dp)
        }

        holder.imgFav.setOnClickListener {

            val checkFav = HomeActivity.Dbasync(context, res, 1).execute().get()
            if(checkFav){
                val removeFav = HomeActivity.Dbasync(context, res, 3).execute().get()
                if (removeFav){
                    holder.imgFav.setImageResource(R.drawable.ic_favorite_border_black_24dp)
                }else{
                    Toast.makeText(context, "Try Again", Toast.LENGTH_SHORT).show()
                }
            }else{
                val addFav = HomeActivity.Dbasync(context, res, 2).execute().get()
                if (addFav){
                    holder.imgFav.setImageResource(R.drawable.ic_favorite_black_24dp)
                }else{
                    Toast.makeText(context, "Try Again", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}