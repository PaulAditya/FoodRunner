package com.example.foodrunner.Activity

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
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodrunner.R
import com.example.foodrunner.database.BookDatabase
import com.example.foodrunner.database.BookEntity
import com.example.foodrunner.util.ConnectionManager
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
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_description)

        toolbar = findViewById(R.id.toolbar)
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

        setUpToolbar()

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

        if (ConnectionManager().checkConnectivity(this@BookDescription)){

            val jsonReq = object : JsonObjectRequest(Request.Method.POST, url, jsonParams, Response.Listener {
                try {


                    val success = it.getBoolean("success")

                    if (success) {
                        progressLayout.visibility = View.GONE
                        val bookData = it.getJSONObject("book_data")
                        txtName.text = bookData.getString("name")
                        txtAuthor.text = bookData.getString("author")
                        txtPrice.text = bookData.getString("price")
                        txtRating.text = bookData.getString("rating")
                        txtBookDescription.text = bookData.getString("description")
                        Picasso.get().load(bookData.getString("image")).into(imgBookImage)

                        val bookEntity = BookEntity(
                            bookId?.toInt() as Int,
                            bookData.getString("name"),
                            bookData.getString("author"),
                            bookData.getString("price"),
                            bookData.getString("rating"),
                            bookData.getString("description"),
                            bookData.getString("image")
                        )

                        val checkFav = Dbasync(
                            applicationContext,
                            bookEntity,
                            1
                        ).execute()
                        val inFav = checkFav.get()

                        if(inFav){
                            btnFav.text = "Remove from Favourites"
                            btnFav.setBackgroundColor(ContextCompat.getColor(applicationContext,
                                R.color.removeFav
                            ))
                        }



                        btnFav.setOnClickListener {

                            if(!(Dbasync(
                                    applicationContext,
                                    bookEntity,
                                    1
                                ).execute().get())){
                                if(Dbasync(
                                        applicationContext,
                                        bookEntity,
                                        2
                                    ).execute().get()){
                                    Toast.makeText(this@BookDescription, "Added to Favourites", Toast.LENGTH_SHORT).show()
                                    btnFav.text = "Remove from Favourites"
                                    btnFav.setBackgroundColor(ContextCompat.getColor(applicationContext,
                                        R.color.removeFav
                                    ))
                                }else{
                                    Toast.makeText(this@BookDescription, "Try Agian", Toast.LENGTH_SHORT).show()
                                }
                            }else{
                                if(Dbasync(
                                        applicationContext,
                                        bookEntity,
                                        3
                                    ).execute().get()){
                                    Toast.makeText(this@BookDescription, "Removed from Favourites", Toast.LENGTH_SHORT).show()
                                    btnFav.text = "Added to Favourites"
                                    btnFav.setBackgroundColor(ContextCompat.getColor(applicationContext,
                                        R.color.addFav
                                    ))
                                }else{
                                    Toast.makeText(this@BookDescription, "Try Agian", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                     }else{

                    }
                }catch (e : Exception){
                    Toast.makeText(this@BookDescription, "API Response failure ", Toast.LENGTH_SHORT).show()
                }

            }, Response.ErrorListener {
                Toast.makeText(this@BookDescription, "Volley Error", Toast.LENGTH_SHORT).show()
            }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "38ac8d48e87ad3"
                    return headers
                }
            }
            queue.add(jsonReq)
        }else{
            val dialog = AlertDialog.Builder(this@BookDescription)
            dialog.setTitle("Failure")
            dialog.setMessage("Internet Not Connected")
            dialog.setPositiveButton("Settings") { text, listener ->
                val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingIntent)
                finish()
            }
            dialog.setNegativeButton("Exit") { text, listener ->
                ActivityCompat.finishAffinity(this@BookDescription)
            }
            dialog.create()
            dialog.show()
        }
    }

    class Dbasync(val context: Context,val bookEntity: BookEntity,val mode : Int) : AsyncTask<Void, Void, Boolean>() {

        val db = Room.databaseBuilder(context, BookDatabase::class.java, "books-db").build()
        override fun doInBackground(vararg params: Void?): Boolean {

            when(mode){
                1-> {
                    val book = db.bookDao().getBookById(bookEntity.book_id.toString())
                    db.close()
                    return book != null
                }
                2-> {
                    db.bookDao().insertBook(bookEntity)
                    db.close()
                    return true
                }
                3-> {
                    db.bookDao().deleteBook(bookEntity)
                    db.close()
                    return true
                }
            }
            return false
        }
    }

    fun setUpToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }




}
