package com.sora.mvvmarchitecture.retrofit

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.sora.mvvmarchitecture.preference.WebViewCookiePersistor
import com.sora.mvvmarchitecture.retrofit.navigator.net.NetNavigator
import retrofit2.Call
import retrofit2.Response

/**
 * Created by zheng on 17-10-30.
 * 登录专用的Callback回调
 *
 */
open class SignInCallback<T>(private val context: Context, private val netNavigator: NetNavigator<T>? = null) :
    retrofit2.Callback<DataResult<T>> {
    override fun onFailure(call: Call<DataResult<T>>?, t: Throwable?) {
        netNavigator?.end()
        netNavigator?.netError()
        onNetError()

    }

    /**
     * 数据错误
     */
    open fun onError(status: String?) {}

    /**
     * 网络错误
     */
    open fun onNetError() {}

    override fun onResponse(call: Call<DataResult<T>>?, response: Response<DataResult<T>>) {
        netNavigator?.end()
        val list = response.headers().values("Set-Cookie")
        if (list.isNotEmpty()) {
            WebViewCookiePersistor(context).save(list)
        }
        if (response.isSuccessful) {
            val body = response.body()
            if (body?.status_code == 0) {
                onSuccess(body.data)
                netNavigator?.success(body.data)
            } else {
                onError(body?.msg)
                netNavigator?.error(body!!.status_code, body.msg)
            }
        } else {
            var error: String
            try {
                if (response.code() == 401) {
                    onError("用户授权已过时，请重新登录。")
                    netNavigator?.needSignIn()
                    return
                }
                if (response.code() >= 500) {
                    onError("服务器异常，请稍后再试。")
                    netNavigator?.error(500, "服务器异常，请稍后再试。")
                    return
                }
                val errorData: DataResult<*>? = Gson().fromJson(response.errorBody()?.string(), DataResult::class.java)
                error = "出现了错误，错误码：${response.code()}"
                Log.d("errorCode", error)
                onError(error)
                netNavigator?.error(response.code(), error)
            } catch (e: Exception) {
                error = "服务器异常，请稍后再试。"
                Log.d("Gson", e.message?:"")
                onError(error)
                netNavigator?.error(response.code(), error)
            }
        }
    }

    /**
     * 成功请求
     */
    open fun onSuccess(data: T?) {}
}