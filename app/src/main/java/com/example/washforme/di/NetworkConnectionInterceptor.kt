package com.example.washforme.di
import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

@Suppress("DEPRECATION")
class NetworkConnectionInterceptor @Inject constructor(context: Context) : Interceptor {

    private val applicationContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable())
            throw IOException("No internet Connection")
        return chain.proceed(chain.request())
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }
    }
}