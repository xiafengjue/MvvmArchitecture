package com.sora.mvvmarchitecture.components

import android.content.IntentFilter
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.gyf.barlibrary.ImmersionBar
import com.sora.mvvmarchitecture.components.receiver.AccountReceiver

abstract class BaseBindingActivity<VDB : ViewDataBinding>(private val activityId: Int) :
    AppCompatActivity() {
    private var mImmersionBar: ImmersionBar? = null
    private lateinit var receiver: AccountReceiver
    lateinit var mBinding: VDB

    //返回子类Activity对象
    abstract fun getChild(): BaseBindingActivity<VDB>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(getChild(), activityId)
        if (isImmersionBarEnabled())
            initImmersionBar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun receiveBroadcast() {
        receiver = AccountReceiver()
        registerReceiver(receiver, IntentFilter(AccountReceiver.ANOTHER_SIGN_IN))
    }

    fun unReceiveBroadcast() {
        unregisterReceiver(receiver)
    }

    /**
     * 是否使用沉浸式
     *
     * @return the boolean
     */
    open fun isImmersionBarEnabled(): Boolean {
        return false
    }

    open fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.keyboardEnable(true)?.navigationBarWithKitkatEnable(false)
            ?.init()
    }

    override fun onDestroy() {
        super.onDestroy()
        mImmersionBar?.destroy()
    }
}