package com.sora.mvvmarchitecture.retrofit.navigator.net

import com.sora.mvvmarchitecture.retrofit.data.SingleLiveEvent


/**
 * 加载更多的网络数据回调
 *
 * 下面是构造函数默认的对象
 * @param snackBarEvent Snackbar绑定的Event对象
 * @param refreshEvent SmartRefreshLayout绑定的Event对象
 * @param netError 默认的网络错误显示的字符串
 */
abstract class MoreNetNavigator<in T>(
    private val snackBarEvent: SingleLiveEvent<String>?,
    private val refreshEvent: SingleLiveEvent<SmartRefresh>?,
    anOtherDialogEvent: SingleLiveEvent<String>? = null,
    private val netError: String
) : NetNavigatorAdapter<T>(anOtherDialogEvent) {

    override fun error(code: Int, status: String?) {
        snackBarEvent?.value = status
    }

    override fun netError() {
        snackBarEvent?.value = netError
    }

    override fun end() {
        refreshEvent?.value = SmartRefresh.LOAD_MORE
    }

    override fun start(title: String) {
    }

}