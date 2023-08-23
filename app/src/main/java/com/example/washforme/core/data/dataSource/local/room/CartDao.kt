package com.example.washforme.core.data.dataSource.local.room

import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.example.washforme.core.domain.model.WashCategoryResponseModelItem
import com.example.washforme.core.domain.model.WashItemResponseModelItem

@Dao
interface CartDao {

    @Query("SELECT * FROM cart")
    fun getAll(): List<Cart>

    @Query("SELECT * from cart where id=:id")
    fun getItemWithCategoryId(id: Int): Cart

    @Insert
    fun insert(vararg cart: Cart)

    @Query("UPDATE CART SET ITEMS = :item WHERE ID = :id")
    fun update(item: WashItemResponseModelItem, id: Int)

    @Insert(onConflict = REPLACE)
    fun insertAll(varargs: List<Cart>)

    @Query("DELETE FROM CART WHERE ID = :id")
    fun delete(id: Int)

    @Query("DELETE FROM cart")
    fun deleteAll()

    @Transaction
    fun insertNewCart(cart: List<Cart>) {
        deleteAll()
        insertAll(cart)
    }

    @Query("SELECT * FROM CART WHERE CATEGORY= :category")
    fun getCart(category: WashCategoryResponseModelItem): List<Cart>
}