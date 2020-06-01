package com.example.foodrunner.Fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.foodrunner.R
import com.example.foodrunner.RecyclerAdapter.FavRestaurantRecycler
import com.example.foodrunner.database.RestaurantDatabase
import com.example.foodrunner.database.RestaurantEntity


class Favourites : Fragment() {

    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: FavRestaurantRecycler

    var favRestaurantList = listOf<RestaurantEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_favourites, container, false)
        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)
        layoutManager = GridLayoutManager(activity as Context, 2 )

        favRestaurantList = RetrieveFavRestaurant(activity as Context).execute().get()

        if(activity != null){
            recyclerAdapter =
                FavRestaurantRecycler(
                    activity as Context,
                    favRestaurantList
                )
            recyclerDashboard.adapter = recyclerAdapter
            recyclerDashboard.layoutManager = layoutManager
        }

        return view
    }

    class RetrieveFavRestaurant(val context: Context): AsyncTask<Void, Void, List<RestaurantEntity>>() {
        override fun doInBackground(vararg params: Void?): List<RestaurantEntity> {
            val db = Room.databaseBuilder(context, RestaurantDatabase::class.java, "restaurant-db").build()

            return db.restaurantDao().getAllRestaurant()
        }
    }

}
