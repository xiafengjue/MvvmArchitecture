package com.sora.mvvmarchitecture.ext

import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.sora.mvvmarchitecture.retrofit.data.SingleLiveEvent
import com.sora.mvvmarchitecture.retrofit.navigator.net.SmartRefresh

/**
 * Created by zheng on 17-10-30.
 */
/**
 * Transforms static java function Snackbar.make() to an extension function on View.
 */
fun View.showSnackbar(snackbarText: String, timeLength: Int) {
    Snackbar.make(this, snackbarText, timeLength).show()
}

/**
 * Triggers a snackbar message when the value contained by snackbarTaskMessageLiveEvent is modified.
 */
fun View.setupSnackbar(
    lifecycleOwner: LifecycleOwner,
    snackbarMessageLiveEvent: SingleLiveEvent<String>, timeLength: Int = Snackbar.LENGTH_LONG
) {
    snackbarMessageLiveEvent.observe(lifecycleOwner, Observer {
        it?.let { showSnackbar(it, timeLength) }
    })
}

fun SmartRefreshLayout.setup(
    lifecycleOwner: LifecycleOwner,
    refreshEvent: SingleLiveEvent<SmartRefresh>
) {
    refreshEvent.observe(lifecycleOwner, Observer { it ->
        when (it) {
            SmartRefresh.LOAD_MORE -> this.finishLoadMore()
            SmartRefresh.REFRESH -> this.finishRefresh()
            SmartRefresh.MORE_END -> this.finishLoadMoreWithNoMoreData() //全部加载完成
            SmartRefresh.MORE_MORE -> this.finishLoadMore() //还有可以加载的
            else -> {
            }
        }
    })
}

/**
 * 获得以DP为单位的Int数据
 */
fun Int.dp(context: Context): Int = (TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    context.resources.displayMetrics
) + 0.5).toInt()

fun Int.sp(context: Context): Int = (TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_SP,
    this.toFloat(),
    context.resources.displayMetrics
) + 0.5).toInt()



