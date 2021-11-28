package com.sora.mvvmarchitecture.framwork.network

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

class ConnectivityChangeReceiver : BroadcastReceiver() {

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            if (isInitialStickyBroadcast) {
                try {
                    val manager: ConnectivityManager =
                        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    NetToolKit.getConnectivityChangeObserver().checkConnect(manager.activeNetworkInfo)
                } catch (e: Exception) {
                }

            }
        }
    }
}
