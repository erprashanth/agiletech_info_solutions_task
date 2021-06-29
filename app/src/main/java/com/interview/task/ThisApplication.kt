package com.interview.task

import android.content.Context
import android.net.ConnectivityManager
import androidx.multidex.MultiDexApplication

class ThisApplication : MultiDexApplication() {

    private val connectivityManager: ConnectivityManager
        get() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun onCreate() {
        super.onCreate()
        instance = this
    }


    /**
     * Checking the internet connectivity
     *
     * @return true if the connection is available otherwise return false
     */
    fun hasNetworkConnection(): Boolean {

        val cm = connectivityManager
        var valid = false

        val wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (wifiNetwork != null && wifiNetwork.isConnectedOrConnecting) {
            valid = true
        }

        val mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (mobileNetwork != null && mobileNetwork.isConnectedOrConnecting) {
            valid = true
        }

        val activeNetwork = cm.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting) {
            valid = true
        }

        return valid
    }

    companion object {

        private val TAG = ThisApplication::class.java.simpleName

        var instance: ThisApplication? = null
            private set

    }
}
