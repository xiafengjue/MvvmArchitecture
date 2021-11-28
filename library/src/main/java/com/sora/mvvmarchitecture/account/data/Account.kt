package com.sora.mvvmarchitecture.account.data

import com.google.gson.annotations.SerializedName
import com.sora.mvvmarchitecture.account.IAccount

class Account : IAccount {

    @SerializedName("access_token")
    var accessToken: String = ""

    @SerializedName("access_token_expire")
    var accessTokenExpire: Long = 0

    @SerializedName("refresh_token")
    var refreshToken: String = ""

    @SerializedName("refresh_token_expire")
    var refreshTokenExpire: Long = 0
    override fun accessToken(): String? {
        return accessToken
    }

    override fun refreshToken(): String? {
        return refreshToken
    }

    override fun accessTokenExpire(): Long? {
        return accessTokenExpire
    }

    override fun refreshTokenExpire(): Long? {
        return refreshTokenExpire
    }
}