package com.example.foodrunner.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface CartDao {

    @Insert
    fun insertInCart(cartEntity: CartEntity)

    @Delete
    fun deleteFromCart(cartEntity: CartEntity)

    @Query("SELECT * FROM cart")
    fun getCart() : List<CartEntity>

    @Query("SELECT * FROM cart WHERE(id = :id )")
    fun getItem(id: Int): CartEntity

    @Query("DELETE FROM cart")
    fun clearCart()
}