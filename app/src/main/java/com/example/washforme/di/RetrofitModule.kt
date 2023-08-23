package com.example.washforme.di

import com.example.washforme.BuildConfig
import com.example.washforme.core.data.dataSource.local.preferences.PrefConstants
import com.example.washforme.core.data.dataSource.local.preferences.PreferenceManagerImpl
import com.example.washforme.core.data.dataSource.remote.Api
import com.example.washforme.core.data.dataSource.remote.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun okHttpClientProvider(preferenceManager: PreferenceManagerImpl): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().also {
                    preferenceManager.getString(PrefConstants.TOKEN)?.let { token ->
                        it.addHeader(
                            "Authorization",
                            "Bearer $token"
                        )
                        it.header("Connection", "close")
                    }
                }.build())
            }
            .retryOnConnectionFailure(true)
            .build()

    @Singleton
    @Provides
    fun apiProvider(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Singleton
    @Provides
    fun repoProvider(api: Api, preferenceManager: PreferenceManagerImpl) =
        Repository(api, preferenceManager)
}

