package com.sora.mvvmarchitecture.framwork.network

import android.content.Context
import android.net.NetworkInfo
import android.text.TextUtils
import com.sora.mvvmarchitecture.framwork.thread.ThreadPool
import java.util.*

/**
 * 监控网络变化回调，用于刷新拨测列表
 */
class ConnectivityChangeObserver(var context: Context) {
    private val eventListenerList = ArrayList<Event>()

    fun addEventListener(listener: Event) {
        eventListenerList.add(listener)
    }

    fun removeEventListener(listener: Event) {
        if (eventListenerList.contains(listener)) {
            eventListenerList.remove(listener)
        }
    }

    fun onNetworkChange() {
        val iterator = eventListenerList.iterator()
        while (iterator.hasNext()) {
            val listener = iterator.next()
            listener.onNetworkChange()
        }
    }

    /**
     * 网络状态改变，通知网络层
     *
     * @param activeNetInfo
     */
    fun checkConnect(activeNetInfo: NetworkInfo?) {
        ThreadPool.INSTANCE.execute {
            //无法获取当前有效连接状态
            when {
                activeNetInfo == null -> {
                    lastActiveNetworkInfo = null
                    onNetworkChange()
                }
                activeNetInfo.detailedState != NetworkInfo.DetailedState.CONNECTED -> {
                    if (lastConnected) {
                        lastActiveNetworkInfo = null
                    }
                    onNetworkChange()
                    lastConnected = false
                }
                else -> {
                    if (isNetworkChange(activeNetInfo)) {
                        onNetworkChange()
                    }
                    lastConnected = true
                }
            }
        }
    }

    private fun isNetworkChange(activeNetInfo: NetworkInfo?): Boolean {
        if (lastActiveNetworkInfo != null
            && lastActiveNetworkInfo!!.extraInfo != null && activeNetInfo!!.extraInfo != null
            && TextUtils.equals(lastActiveNetworkInfo!!.extraInfo, activeNetInfo.extraInfo)
            && lastActiveNetworkInfo!!.subtype == activeNetInfo.subtype
            && lastActiveNetworkInfo!!.type == activeNetInfo.type
        ) {
            return false

        } else if (lastActiveNetworkInfo != null
            && lastActiveNetworkInfo!!.extraInfo == null && activeNetInfo!!.extraInfo == null
            && lastActiveNetworkInfo!!.subtype == activeNetInfo.subtype
            && lastActiveNetworkInfo!!.type == activeNetInfo.type
        ) {
            return false
        }

        lastActiveNetworkInfo = activeNetInfo

        return true
    }

    /**
     * 监控网络状态变化
     */
    interface Event {
        fun onNetworkChange()
    }

    companion object {
        val TAG = "ConnectivityChangeObserver"

        var lastActiveNetworkInfo: NetworkInfo? = null
        var lastConnected = true
    }
}
