package com.sora.mvvmarchitecture.components

import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.gyf.barlibrary.ImmersionBar
import com.sora.mvvmarchitecture.components.receiver.AccountReceiver

abstract class BaseBindingFragment<VDB : ViewDataBinding>(private val layoutId: Int) :
    Fragment() {
    private var mImmersionBar: ImmersionBar? = null
    private lateinit var receiver: AccountReceiver
    lateinit var mBinding: VDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isImmersionBarEnabled())
            initImmersionBar()
        onViewCreate(mBinding, savedInstanceState)
    }

    abstract fun onViewCreate(mBinding: VDB, savedInstanceState: Bundle?)
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        mImmersionBar?.takeIf { hidden }?.init()
    }

    /**
     * 是否在Fragment使用沉浸式
     *
     * @return the boolean
     */
    open fun isImmersionBarEnabled(): Boolean {
        return false
    }

    /**
     * 初始化沉浸式
     */
    open fun initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar?.keyboardEnable(true)?.navigationBarWithKitkatEnable(false)?.init()
    }

    override fun onDestroy() {
        super.onDestroy()
        mImmersionBar?.destroy()
    }
}