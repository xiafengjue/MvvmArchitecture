package com.sora.mvvmarchitecture.retrofit.navigator.net

import com.sora.mvvmarchitecture.retrofit.data.Upload

interface UpLoadNavigator {
    fun success(upload: Upload?)
}