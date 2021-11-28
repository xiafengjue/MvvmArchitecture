package com.sora.mvvmarchitecture.retrofit.navigator.net

import androidx.databinding.ObservableInt
import com.sora.multistateview.MultiStateView
import com.sora.mvvmarchitecture.retrofit.data.SingleLiveEvent

/**
 * Start装填获取数据的网络数据回调
 *
 * 下面是构造函数默认的对象
 * @param state MultiStateView的状态控制对象
 * @param refreshEvent SmartRefreshLayout绑定的Event对象
 */
open class MultiNetNavigator<in T>(
    private val state: ObservableInt,
    private val refreshEvent: SingleLiveEvent<SmartRefresh>? = null,
    anOtherDialogEvent: SingleLiveEvent<String>? = null
) : NetNavigatorAdapter<T>(anOtherDialogEvent) {

    override fun needSignIn() {
        super.needSignIn()
//        state.set(MultiStateView.VIEW_STATE_NEED_SIGN_IN)
    }

    override fun success(data: T?) {
    }

    override fun error(code: Int, status: String?) {
        state.set(MultiStateView.VIEW_STATE_ERROR)
    }

    override fun netError() {
        state.set(MultiStateView.VIEW_STATE_NET_ERROR)
    }

    override fun end() {
        refreshEvent?.value = SmartRefresh.START
    }

    override fun start(title: String) {
        state.set(MultiStateView.VIEW_STATE_LOADING)
    }

}
