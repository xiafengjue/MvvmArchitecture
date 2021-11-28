package com.sora.mvvmarchitecture.ext

import androidx.databinding.ObservableInt
import androidx.lifecycle.AndroidViewModel
import com.sora.mvvmarchitecture.retrofit.data.SingleLiveEvent
import com.sora.mvvmarchitecture.retrofit.navigator.net.*
import  com.sora.mvvmarchitecture.retrofit.navigator.net.SmartRefresh
import com.sora.mvvmarchitecture.retrofit.navigator.net.NetNavigator

/**
 * 创建SRLNetNavigator,SRL的意思是Start，Refresh,LoadMore状态，分别对应了SmartRefresh这个枚举几个类型
 *
 * Start：对应SmartRefresh.Start 表示最开始的获取数据操作，通常是布局中央显示一个Progressbar的情况
 *
 * Refresh：对应SmartRefresh.Refresh 表示下拉列表或布局进行数据进行更新操作，或者手动点击刷新按钮进行更新
 *
 * LoadMore，对应Smart.LOAD_MORE 表示上拉列表或布局进行加载更多的操作
 *
 * 通过这三个不同的状态，来区分需要返回的NetNavigator对象
 *
 *  @see com.sora.mvvmarchitecture.retrofit.navigator.net.SmartRefresh
 *  @see com.sora.mvvmarchitecture.retrofit.navigator.net.NetNavigator
 *
 *  @param smartRefresh SmartRefresh对象，表示请求的状态
 *  @param state MultiStateView切换时候需要的状态，传入这个值可以改变MultiStateView的状态
 *  @param snackbarEvent Snackbar对象绑定的Event，传入这个对象可以控制Snackbar的提示信息
 *  @param refreshEvent SmartRefreshLayout绑定的Event，传入这个可以控制SmartRefreshLayout的各个状态切换
 *  @param loadMore 加载更多的状态获取数据成功后的回调方法
 *  @param refresh 刷新状态获取数据成功后的回调方法
 *  @param start Start状态获取数据成功后的回调方法
 *  @return 返回对应状态的NetNavigator对象，主要由MoreNetNavigator，RefreshNetNavigator，MultiNetNavigator中的一个来产生
 */
fun <T> AndroidViewModel.createSRLNetNavigator(
    smartRefresh: SmartRefresh,
    state: ObservableInt,
    snackbarEvent: SingleLiveEvent<String>,
    anotherDialogEvent: SingleLiveEvent<String>,
    refreshEvent: SingleLiveEvent<SmartRefresh>? = null,
    loadMore: ((data: T?) -> Unit?)? = null,
    refresh: ((data: T?) -> Unit?)? = null,
    start: ((data: T?) -> Unit?)? = null,
    error: ((code: Int) -> Unit?)? = null,
    errorString: ((code: Int) -> String?)? = null
): NetNavigator<T>? {
    return when (smartRefresh) {
        SmartRefresh.LOAD_MORE -> object :
            MoreNetNavigator<T>(snackbarEvent, refreshEvent, anotherDialogEvent, getNetError()) {
            override fun success(data: T?) {
                loadMore?.invoke(data)
            }

            override fun error(code: Int, status: String?) {
                val str: String? = errorString?.invoke(code)
                error?.invoke(code) ?: super.error(code, str ?: status)
            }
        }
        SmartRefresh.REFRESH -> object :
            RefreshNetNavigator<T>(snackbarEvent, refreshEvent, anotherDialogEvent, getNetError()) {
            override fun success(data: T?) {
                //如果是刷新和开始，就清空数据
                snackbarEvent.value = "数据已刷新"
//                val toast = Toast.makeText(getApplication(), "数据已刷新", Toast.LENGTH_SHORT)
//                toast.setGravity(Gravity.CENTER, 0, 0)
//                toast.show()
                refresh?.invoke(data)
            }

            override fun error(code: Int, status: String?) {
                val str: String? = errorString?.invoke(code)
                error?.invoke(code) ?: super.error(code, str ?: status)
            }
        }
        SmartRefresh.START -> object :
            MultiNetNavigator<T>(state, refreshEvent, anotherDialogEvent) {
            override fun success(data: T?) {
                super.success(data)
                start?.invoke(data)
            }

            override fun error(code: Int, status: String?) {
                val str: String? = errorString?.invoke(code)
                error?.invoke(code) ?: super.error(code, str ?: status)
            }

        }
        else -> {
            null
        }
    }
}

/**
 * 创建SDNetNavigator，S代表Snackbar，D代表Dialog，使用场景是常见的获取数据展示Dialog，获取成功或者失败就显示一个Toast或者Snackbar
 * @param snackbarEvent Snackbar对象绑定的Event，传入这个对象可以控制Snackbar的提示信息
 * @param dialogEvent Dialog对象绑定的Event，传入这个对象可以控制Dialog的提示信息、显示、关闭
 */
fun <T> AndroidViewModel.createSDNetNavigator(
    snackbarEvent: SingleLiveEvent<String>,
    dialogEvent: SingleLiveEvent<String>,
    anotherDialogEvent: SingleLiveEvent<String>,
    success: ((data: T?) -> Unit?)? = null,
    error: ((code: Int) -> Unit?)? = null,
    errorString: ((code: Int) -> String?)? = null
): NetNavigator<T> {
    return object : SnackbarDialogNetNavigator<T>(
        snackbarEvent,
        dialogEvent,
        anotherDialogEvent,
        getNetError()
    ) {
        override fun success(data: T?) {
            success?.invoke(data)
        }

        override fun error(code: Int, status: String?) {
            val str: String? = errorString?.invoke(code)
            error?.invoke(code) ?: super.error(code, str ?: status)
        }
    }

}