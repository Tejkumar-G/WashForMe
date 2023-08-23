package com.example.washforme.di

import com.example.washforme.core.data.dataSource.local.preferences.PreferenceManagerImpl
import com.example.washforme.featureBooking.data.dataSource.BookingApi
import com.example.washforme.featureBooking.data.repository.BookingRepositoryImpl
import com.example.washforme.featureBooking.domain.repository.BookingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BookingModule {

    @Provides
    @Singleton
    fun provideBookingApi(retrofit: Retrofit): BookingApi =
        retrofit.create(BookingApi::class.java)


    @Singleton
    @Provides
    fun provideRepo(api: BookingApi, preferenceManager: PreferenceManagerImpl): BookingRepository =
        BookingRepositoryImpl(api)
}