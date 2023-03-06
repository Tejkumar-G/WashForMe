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
import java.util.concurrent.TimeUnit
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
            .connectTimeout(2, TimeUnit.MINUTES) // connect timeout
            .writeTimeout(2, TimeUnit.MINUTES) // write timeout
            .readTimeout(2, TimeUnit.MINUTES) // read timeout
//            .addInterceptor { chain ->
//                chain.proceed(
//                    chain.request().newBuilder().addHeader(
//                        "Authorization", "Token ${preferenceManager.getString(Constants.TOKEN)}"
//                    ).build()
//                )
//            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
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

