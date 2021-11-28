package com.sora.mvvmarchitecture.components.receiver

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by zheng on 17-12-26.
 * 用户登录状态的通知
 */

class AccountReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == SIGN_IN_SUCCESS) {
            if (context is Callback) {
                context.signInSuccess()
            }
        } else if (intent.action == ANOTHER_SIGN_IN) {
            if (context is Activity) {
                context.finish()
            }
        }
    }

    interface Callback {
        /**
         * 登录成功的通知
         */
        fun signInSuccess()
    }

    companion object {
        /**
         * 登录成功的通知
         */
        @JvmStatic
        val SIGN_IN_SUCCESS = "com.yixun.easyauction.account.SIGN_IN_SUCCESS"
        @JvmStatic
        val ANOTHER_SIGN_IN = "com.yixun.easyauction.account.ANOTHER_SIGN_IN"
    }
}