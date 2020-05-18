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
import com.example.foodrunner.Dataclass.Book
import com.example.foodrunner.R
import com.example.foodrunner.RecyclerAdapter.FavBookRecycler
import com.example.foodrunner.RecyclerAdapter.RecyclerAdapter
import com.example.foodrunner.database.BookDatabase
import com.example.foodrunner.database.BookEntity


class Favourites : Fragment() {

    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: FavBookRecycler

    var favBookList = listOf<BookEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_favourites, container, false)
        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)
        layoutManager = GridLayoutManager(activity as Context, 2 )

        favBookList = RetrieveFavBook(activity as Context).execute().get()

        if(activity != null){
            recyclerAdapter =
                FavBookRecycler(
                    activity as Context,
                    favBookList
                )
            recyclerDashboard.adapter = recyclerAdapter
            recyclerDashboard.layoutManager = layoutManager
        }

        return view
    }

    class RetrieveFavBook(val context: Context): AsyncTask<Void, Void, List<BookEntity>>() {
        override fun doInBackground(vararg params: Void?): List<BookEntity> {
            val db = Room.databaseBuilder(context, BookDatabase::class.java, "books-db").build()

            return db.bookDao().getAllBook()
        }
    }

}
