package com.example.washforme.di

import com.example.washforme.db.Api
import com.example.washforme.db.Repository
import com.example.washforme.utils.Constants
import com.example.washforme.utils.MyPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun retrofitProvider(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun okHttpClientProvider(preferenceManager: MyPreferenceManager): OkHttpClient =
        OkHttpClient.Builder()

            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            .retryOnConnectionFailure(true)
            .build()

    @Singleton
    @Provides
    fun apiProvider(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Singleton
    @Provides
    fun repoProvider(api: Api, preferenceManager: MyPreferenceManager) = Repository(api, preferenceManager)


}

