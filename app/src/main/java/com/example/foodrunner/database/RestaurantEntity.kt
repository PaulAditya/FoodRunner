package com.example.foodrunner.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurant")
data class RestaurantEntity (

    @PrimaryKey val id : Int,
    @ColumnInfo(name = "restaurant_name") val name : String,
    @ColumnInfo(name = "restaurant_price")val price : String,
    @ColumnInfo(name = "restaurant_rating")val rating : String,
    @ColumnInfo(name = "restaurant_image")val image : String
)