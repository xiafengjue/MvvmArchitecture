package com.sora.mvvmarchitecture.retrofit.navigator.net

import com.sora.mvvmarchitecture.retrofit.data.SingleLiveEvent

/**
 * 刷新数据的网络数据回调
 *
 * 下面是构造函数默认的对象
 * @param snackbarEvent Snackbar绑定的Event对象
 * @param refreshEvent SmartRefreshLayout绑定的Event对象
 * @param netError 默认的网络错误显示的字符串
 */
abstract class RefreshNetNavigator<in T>(
    private val snackbarEvent: SingleLiveEvent<String>?,
    private val refreshEvent: SingleLiveEvent<SmartRefresh>?,
    anOtherDialogEvent: SingleLiveEvent<String>? = null,
    val netError: String
) : NetNavigatorAdapter<T>(anOtherDialogEvent) {

    override fun error(code: Int, status: String?) {
        snackbarEvent?.value = status
    }

    override fun netError() {
        snackbarEvent?.value = netError
    }

    override fun end() {
        refreshEvent?.value = SmartRefresh.REFRESH
    }

    override fun start(title: String) {
    }

}