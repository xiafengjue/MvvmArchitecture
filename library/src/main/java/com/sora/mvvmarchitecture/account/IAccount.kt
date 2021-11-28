package com.sora.mvvmarchitecture.account

/**
 * 账户接口
 */
interface IAccount {
    fun accessToken(): String?
    fun refreshToken(): String?
    fun accessTokenExpire(): Long?
    fun refreshTokenExpire(): Long?
}