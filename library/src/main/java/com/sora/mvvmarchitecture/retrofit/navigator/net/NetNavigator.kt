package com.sora.mvvmarchitecture.retrofit.navigator.net

/**
 * 网络的回调接口，用这个接口进行状态的返回
 */
interface NetNavigator<in T> {
    /**
     * 请求成功方法
     * @param data 对应的泛型数据
     */
    fun success(data: T?)

    /**
     * 请求失败方法
     * @param status 请求失败返回的错误信息
     */
    fun error(code: Int, status: String?)

    /**
     * 错误是因为网络有问题而回调的方法
     */
    fun netError()

    /**
     * 整个请求全部结束
     */
    fun end()

    /**
     * 请求开始
     * @param title 需要显示的Dialog的title，或者是Toast需要的状态
     */
    fun start(title: String)

    /**
     * 因为登录信息过期或者不存在所以需要重新登录的回调方法
     */
    fun needSignIn() {}

    fun anotherSignIn()
}