package com.example.foodrunner

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.lang.Exception

class BookDescription : AppCompatActivity() {

    lateinit var btnFav : Button
    lateinit var progressLayout : RelativeLayout
    lateinit var progressBar : ProgressBar
    lateinit var txtPrice : TextView
    lateinit var txtName : TextView
    lateinit var txtAuthor : TextView
    lateinit var txtBookDescription : TextView
    lateinit var txtRating : TextView
    lateinit var imgBookImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_description)

        btnFav = findViewById(R.id.btnFav)
        imgBookImage = findViewById(R.id.imgBookImage)
        progressLayout = findViewById(R.id.progressLayout)
        progressBar = findViewById(R.id.progressBar)
        txtPrice = findViewById(R.id.txtPrice)
        txtName = findViewById(R.id.txtName)
        txtAuthor = findViewById(R.id.txtAuthor)
        txtBookDescription = findViewById(R.id.txtBookDescription)
        txtRating = findViewById(R.id.txtRating)
        progressBar.visibility = View.VISIBLE
        progressLayout.visibility = View.VISIBLE

        var bookId :  String? = "100"

        if(intent != null){
            bookId = intent.getStringExtra("book_id")
        }else{
            finish()
            Toast.makeText(this@BookDescription, "Something went wrong", Toast.LENGTH_SHORT).show()
        }

        if(bookId == "100"){
            finish()
            Toast.makeText(this@BookDescription, "Something went wrong", Toast.LENGTH_SHORT).show()
        }

        val queue = Volley.newRequestQueue(this@BookDescription)
        val url = "http://13.235.250.119/v1/book/get_book/"
        val jsonParams = JSONObject()
        jsonParams.put("book_id", bookId)

        val jsonReq = object : JsonObjectRequest(Request.Method.POST, url, jsonParams, Response.Listener {
            try {
                val succes = it.getBoolean("success")

                if (succes) {
                    progressLayout.visibility = View.GONE
                    val bookData = it.getJSONObject("book_data")
                    txtName.text = bookData.getString("name")
                    txtAuthor.text = bookData.getString("author")
                    txtPrice.text = bookData.getString("price")
                    txtRating.text = bookData.getString("rating")
                    txtBookDescription.text = bookData.getString("description")
                    Picasso.get().load(bookData.getString("image")).into(imgBookImage)

                }else{

                }
            }catch (e : Exception){

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
}
