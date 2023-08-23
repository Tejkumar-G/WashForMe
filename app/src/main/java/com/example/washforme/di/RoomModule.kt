package com.example.washforme.di

import android.content.Context
import androidx.room.Room
import com.example.washforme.core.data.dataSource.local.room.AppDatabase
import com.example.washforme.core.data.dataSource.local.room.CartDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideRoomInstance(@ApplicationContext context: Context) : AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "cart.db"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

    @Singleton
    @Provides
    fun provideCartDao(database: AppDatabase): CartDao = database.cartDao()

}