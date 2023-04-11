package com.example.washforme.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query

@Dao
interface CartDao {

    @Query("SELECT * FROM cart")
    fun getAll(): List<Cart>

    @Query("SELECT * from cart where id=:id")
    fun getItemWithCategoryId(id: Int): Cart

    @Insert
    fun insert(vararg cart: Cart)

    @Insert(onConflict = REPLACE)
    fun insertAll(varargs: ArrayList<Cart>)

    @Delete
    fun delete(cart: Cart)

    @Query("DELETE FROM  cart")
    fun deleteAll()
}