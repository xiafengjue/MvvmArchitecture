package com.sora.mvvmarchitecture.retrofit.navigator.net

import com.sora.mvvmarchitecture.retrofit.data.SingleLiveEvent

open class NetNavigatorAdapter<in T>
    (private val anOtherDialogEvent: SingleLiveEvent<String>? = null) : NetNavigator<T> {
    override fun anotherSignIn() {
        anOtherDialogEvent?.value = "AnotherSignIn"
    }

    override fun success(data: T?) {
    }

    override fun error(code: Int, status: String?) {
    }

    override fun netError() {
    }

    override fun end() {
    }

    override fun start(title: String) {
    }

    override fun needSignIn() {
        super.needSignIn()
        anOtherDialogEvent?.value = "NeedSignIn"
    }
}