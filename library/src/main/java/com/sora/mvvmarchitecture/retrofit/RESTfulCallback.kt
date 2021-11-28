package com.sora.mvvmarchitecture.retrofit

import android.util.Log
import com.google.gson.Gson
import com.sora.mvvmarchitecture.retrofit.navigator.net.NetNavigator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by zheng on 17-10-30.
 *
 */
open class RESTfulCallback<T>(private val netNavigator: NetNavigator<T>? = null) : Callback<DataResult<T>> {
    private lateinit var bodyString: String

    override fun onFailure(call: Call<DataResult<T>>?, t: Throwable?) {
        Log.e("onFailure", t?.message?:"")
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
        if (response.isSuccessful) {
            val body = response.body()
            when (body!!.status_code) {
                0 -> {
                    onSuccess(body.data)
                    netNavigator?.success(body.data)
                }
                1005 -> {
                    onError("用户授权已过时，请重新登录。")
                    netNavigator?.needSignIn()
                }
                1105 -> {
                    onError("用户授权已过时，请重新登录。")
                    netNavigator?.needSignIn()
                }
                1106 -> {
                    onError("改账号在另一个设备登录。")
                    netNavigator?.anotherSignIn()
                }
                3401 -> {
                    val str = "获取验证码出现问题，请稍后重试"
                    onError(str)
                    netNavigator?.error(body.status_code, str)
                }
                3500 -> {
                    val str = "该身份证已经被实名认证过了。"
                    onError(str)
                    netNavigator?.error(body.status_code, str)
                }
                3501 -> {
                    val str = "用户未进行实名认证。"
                    onError(str)
                    netNavigator?.error(body.status_code, str)
                }
                3502 -> {
                    val str = "用户已进行过实名认证，不能重复认证。"
                    onError(str)
                    netNavigator?.error(body.status_code, str)
                }
                else -> {
                    onError(body.msg)
                    netNavigator?.error(body.status_code, body.msg)

                }
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
                    netNavigator?.error(response.code(), "服务器异常，请稍后再试。")
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