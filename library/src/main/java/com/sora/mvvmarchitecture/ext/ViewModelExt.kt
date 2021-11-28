package com.sora.mvvmarchitecture.ext

import android.app.Application
import androidx.databinding.ObservableInt
import androidx.lifecycle.AndroidViewModel
import com.sora.multistateview.MultiStateView
import com.sora.mvvmarchitecture.R

/**
 * Created by zheng on 17-10-31.
 */
fun AndroidViewModel.getNetError(): String =
    this.getApplication<Application>().getString(R.string.net_error)

fun AndroidViewModel.getString(res: Int): String = this.getApplication<Application>().getString(res)
fun ObservableInt.isEmpty(list: List<*>, min: Int = 0) {
    if (list.size > min) {
        this.set(MultiStateView.VIEW_STATE_CONTENT)
    } else {
        this.set(MultiStateView.VIEW_STATE_EMPTY)
    }
}