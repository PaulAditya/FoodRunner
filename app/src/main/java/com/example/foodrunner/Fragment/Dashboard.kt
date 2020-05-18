package com.example.foodrunner.Fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
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
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodrunner.Dataclass.Book
import com.example.foodrunner.R
import com.example.foodrunner.RecyclerAdapter.RecyclerAdapter
import com.example.foodrunner.util.ConnectionManager


class Dashboard : Fragment() {


    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: RecyclerAdapter


    val bookList = arrayListOf<Book>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)
        layoutManager = LinearLayoutManager(activity)



        val queue = Volley.newRequestQueue(activity as Context)

        val url = "http://13.235.250.119/v1/book/fetch_books/"

        if (ConnectionManager().checkConnectivity(activity as Context)){
            val jsonReq = object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {

                val succes = it.getBoolean("success")

                if (succes) {
                    val data = it.getJSONArray("data")
                    println("Response $data")

                    for (i in 0 until data.length()) {
                        val book = data.getJSONObject(i)
                        val bookObject = Book(
                            book.getString("book_id"),
                            book.getString("name"),
                            book.getString("author"),
                            book.getString("rating"),
                            book.getString("price"),
                            book.getString("image")
                        )
                        bookList.add(bookObject)
                        recyclerAdapter =
                            RecyclerAdapter(
                                activity as Context,
                                bookList
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
