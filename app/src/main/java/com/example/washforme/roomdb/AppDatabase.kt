package com.example.washforme.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cart::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        private var roomDatabase: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase? {
            if (roomDatabase!=null)
                return roomDatabase
            else
                synchronized(AppDatabase::class) {
                    roomDatabase = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java, "cart.db"
                    ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
                }
            return roomDatabase
        }
    }
}