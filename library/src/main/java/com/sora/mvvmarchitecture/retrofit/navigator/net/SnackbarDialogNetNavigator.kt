package com.sora.mvvmarchitecture.retrofit.navigator.net

import com.sora.mvvmarchitecture.retrofit.data.SingleLiveEvent

/**
 * Created by zheng on 17-11-17.
 */
abstract class SnackbarDialogNetNavigator<in T>(
    private var snackbarEvent: SingleLiveEvent<String>?,
    private var dialogEvent: SingleLiveEvent<String>?,
    anOtherDialogEvent: SingleLiveEvent<String>? = null,
    private var netError: String
) : NetNavigatorAdapter<T>(anOtherDialogEvent) {
    override fun error(code: Int, status: String?) {
        snackbarEvent?.value = status
        end()
    }

    override fun netError() {
        error(500, netError)
    }

    override fun end() {
        dialogEvent?.call()
    }

    override fun start(title: String) {
        dialogEvent?.value = title
    }
}