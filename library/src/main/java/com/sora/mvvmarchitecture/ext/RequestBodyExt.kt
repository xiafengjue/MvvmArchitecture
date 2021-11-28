package com.sora.mvvmarchitecture.ext

import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject

/**
 * Created by zheng on 17-10-31.
 */
fun Any.createJSON(jsonObject: JSONObject): RequestBody {
    return RequestBody.create(MediaType.parse("application/json"), jsonObject.toString())
}