package com.example.foodrunner.Activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodrunner.Dataclass.MenuItem
import com.example.foodrunner.R
import com.example.foodrunner.RecyclerAdapter.MenuAdapter
import com.example.foodrunner.database.CartDatabase
import com.example.foodrunner.database.CartEntity
import com.example.foodrunner.database.RestaurantDatabase
import com.example.foodrunner.database.RestaurantEntity
import com.example.foodrunner.util.ConnectionManager
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.lang.Exception

class RestaurantAcitivty : AppCompatActivity() {

    lateinit var recyclerRestaurant : RecyclerView
    lateinit var layoutManager : RecyclerView.LayoutManager
    lateinit var adapter : MenuAdapter

    val items = arrayListOf<MenuItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)
        if(intent != null){

            recyclerRestaurant = findViewById(R.id.recyclerRestaurant)
            layoutManager = LinearLayoutManager(this)

            val id = intent.getStringExtra("restaurant_id")

            val queue = Volley.newRequestQueue(this)
            val url = "http://13.235.250.119/v2/restaurants/fetch_result/$id"

            val req = object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {

                val data = it.getJSONObject("data")
                if(data.getBoolean("success")){

                    val menu = data.getJSONArray("data")

                    for(i in 0 until menu.length()){
                        val item = menu.getJSONObject(i)
                        val menuObject = MenuItem(
                            item.getString("id"),
                            item.getString("name"),
                            item.getString("cost_for_one"),
                            item.getString("restaurant_id")
                        )
                        items.add(menuObject)
                        adapter = MenuAdapter(this, items)
                        recyclerRestaurant.adapter = adapter
                        recyclerRestaurant.layoutManager = layoutManager
                    }
                }else{
                    Toast.makeText(this, data.getString("error_message"), Toast.LENGTH_SHORT).show()
                }

            }, Response.ErrorListener {
                Toast.makeText(this, "Error - $it", Toast.LENGTH_SHORT).show()

            }){
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "38ac8d48e87ad3"
                    return headers
                }
            }
            queue.add(req)
        }
    }

    override fun onBackPressed() {

        val cart = Cart(this, 1).execute().get()
        println("CART $cart")
        if(!cart){
            val dialog = AlertDialog.Builder(this )
            dialog.setTitle("Confirmation")
            dialog.setMessage("This will clear cart")
            dialog.setPositiveButton("Yes") { text, listener ->
                val clearCart = Cart(this, 2).execute().get()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
            dialog.setNegativeButton("NO") { text, listener ->
            }
            dialog.create()
            dialog.show()
        }else{
            val clearCart = Cart(this, 2).execute().get()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }


    class Cart(val context: Context, val mode: Int): AsyncTask<Void,Void,Boolean>(){
        override fun doInBackground(vararg params: Void?): Boolean{
            val db = Room.databaseBuilder(context, CartDatabase::class.java, "cart-db").build()
            when(mode){
                1 -> {
                    val cartItems = db.cartDao().getCart()
                    println("CAART $cartItems")
                    return cartItems.isEmpty()
                }
                2 -> {
                    db.cartDao().clearCart()
                    return true
                }
            }
            return false
        }

    }
}





