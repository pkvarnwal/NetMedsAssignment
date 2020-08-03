package com.netmedsassignment.data.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.netmedsassignment.app.MyApplication
import okhttp3.Interceptor
import okhttp3.Response

// This interceptor is used by OkHttp to throw NoConnectivityException Exception when there is no internet.
class ConnectivityInterceptor : Interceptor {

    var isOnline: Boolean

    init {
        isOnline = isNetworkAvailable(MyApplication.mInstance)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!isNetworkAvailable(MyApplication.mInstance)) throw NoConnectivityException()
        else chain.proceed(chain.request())

    }

    private fun isNetworkAvailable(context: Context): Boolean {
        var result = false

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfoCompat = connectivityManager.activeNetwork ?: return false

        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(networkInfoCompat) ?: return false

        result = when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }


        return result
    }

}