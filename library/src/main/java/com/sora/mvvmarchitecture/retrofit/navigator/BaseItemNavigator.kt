package com.sora.mvvmarchitecture.retrofit.navigator

/**
 * description: <一句话功能简述>
 * @author tt11
 * @time  2017/9/14.
 */
interface BaseItemNavigator<in T> {
    fun dataDetail(t: T)
}