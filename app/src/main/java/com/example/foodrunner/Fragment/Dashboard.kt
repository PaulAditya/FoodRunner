package com.example.foodrunner.Fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodrunner.Dataclass.Restaurant
import com.example.foodrunner.R
import com.example.foodrunner.RecyclerAdapter.RecyclerAdapter
import com.example.foodrunner.database.RestaurantDatabase
import com.example.foodrunner.database.RestaurantEntity
import com.example.foodrunner.util.ConnectionManager


class Dashboard : Fragment() {


    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: RecyclerAdapter

    val restaurantList = arrayListOf<Restaurant>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)
        layoutManager = LinearLayoutManager(activity)

        val queue = Volley.newRequestQueue(activity as Context)

        val url = "http://13.235.250.119/v2/restaurants/fetch_result/"

        if (ConnectionManager().checkConnectivity(activity as Context)){
            val jsonReq = object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                val data = it.getJSONObject("data")
                val success = data.getBoolean("success")

                if (success) {
                    val resData = data.getJSONArray("data")

                    for (i in 0 until resData.length()) {
                        val restaurant = resData.getJSONObject(i)
                        val restaurantObject = Restaurant(
                            restaurant.getString("id"),
                            restaurant.getString("name"),
                            restaurant.getString("rating"),
                            restaurant.getString("cost_for_one"),
                            restaurant.getString("image_url")
                        )
                        restaurantList.add(restaurantObject)
                        recyclerAdapter =
                            RecyclerAdapter(
                                activity as Context,
                                restaurantList
                            )
                        recyclerDashboard.adapter = recyclerAdapter
                        recyclerDashboard.layoutManager = layoutManager

                    }

                } else {
                    Toast.makeText(activity as Context, "Some error occured", Toast.LENGTH_SHORT).show()
                }
            }, Response.ErrorListener {

            }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "38ac8d48e87ad3"
                    return headers
                }
            }

            queue.add(jsonReq)
        }
        else{
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Failure")
            dialog.setMessage("Internet Not Connected")
            dialog.setPositiveButton("Settings") { text, listener ->
                val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingIntent)
                activity?.finish()
            }
            dialog.setNegativeButton("Exit") { text, listener ->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()
        }

        return view
    }



}
