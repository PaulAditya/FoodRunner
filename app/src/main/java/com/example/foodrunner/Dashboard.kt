package com.example.foodrunner

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Dashboard : Fragment() {


    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: RecyclerAdapter

    val bookInfoList = arrayListOf<Book>(
        Book("P.S. I love You", "Cecelia Ahern", "Rs. 299", "4.5"),
        Book("The Great Gatsby", "F. Scott Fitzgerald", "Rs. 399", "4.1"),
        Book("Anna Karenina", "Leo Tolstoy", "Rs. 199", "4.3"),
        Book("Madame Bovary", "Gustave Flaubert", "Rs. 500", "4.0"),
        Book("War and Peace", "Leo Tolstoy", "Rs. 249", "4.8"),
        Book("Lolita", "Vladimir Nabokov", "Rs. 349", "3.9"),
        Book("Middlemarch", "George Eliot", "Rs. 599", "4.2"),
        Book("The Adventures of Huckleberry Finn", "Mark Twain", "Rs. 699", "4.5"),
        Book("Moby-Dick", "Herman Melville", "Rs. 499", "4.5"),
        Book("The Lord of the Rings", "J.R.R Tolkien", "Rs. 749", "5.0")
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)
        layoutManager = LinearLayoutManager(activity)
        recyclerAdapter = RecyclerAdapter(activity as Context, bookInfoList)
        recyclerDashboard.adapter = recyclerAdapter
        recyclerDashboard.layoutManager = layoutManager
        recyclerDashboard.addItemDecoration(
            DividerItemDecoration(
                recyclerDashboard.context,
                (layoutManager as LinearLayoutManager).orientation
            )
        )


        return view
    }

}
