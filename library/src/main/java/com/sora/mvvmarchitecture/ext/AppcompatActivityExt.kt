package com.sora.mvvmarchitecture.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.sora.multistateview.MultiStateView
import com.sora.mvvmarchitecture.retrofit.navigator.net.SmartRefresh

//Fragment的扩展文件

/**
 * 通过Tag字符串来获取Fragment对象
 * @param tag 添加Fragment时候使用的字符串
 * @return 泛型对应的Fragment对象
 */
fun AppCompatActivity.getFragment(tag: String): Fragment? =
    supportFragmentManager.findFragmentByTag(tag)

/**
 * 添加Fragment
 * @param id Fragment依附的ViewGroup的resId
 * @param fragment Fragment对象
 * @param tag Fragment保存的字符
 */
fun AppCompatActivity.addFragment(id: Int, fragment: Fragment, tag: String) {
    supportFragmentManager.beginTransaction().add(id, fragment, tag).commit()
}

/**
 * 显示Fragment
 * @param fragment Fragment对象
 */
fun AppCompatActivity.showFragment(fragment: Fragment?) {
    fragment?.let {
        supportFragmentManager.beginTransaction().show(it).commit()
    }
}

/**
 * 隐藏Fragment
 * @param fragment Fragment对象
 */
fun AppCompatActivity.hideFragment(fragment: Fragment) {
    supportFragmentManager.beginTransaction().hide(fragment).commit()
}

/**
 * 通过Tag字符串来移除Fragment
 * @param tag 字符串
 */
fun AppCompatActivity.removeFragment(tag: String) {
    getFragment(tag)?.let {
        removeFragment(it)
    }
}

/**
 * 移除Fragment
 * @param fragment Fragment对象
 */
fun AppCompatActivity.removeFragment(fragment: Fragment) {
    supportFragmentManager.beginTransaction().remove(fragment).commit()
}

/**
 * 从传入的Context跳转到调用方法的Activity，这个方法通常写在Activity的component对象里
 * @param context 从哪里来
 */
fun AppCompatActivity.start(context: Context) {
    context.startActivity(Intent(context, this::class.java))
}

/**
 * 跳转到下一个Activity
 */

/**
 * 跳转到下一个页面
 * @param cls 需要跳转的Activity的class对象
 * @param intent 跳转信息
 * @param requestCode 返回码，不传就表示无返回值
 */
fun AppCompatActivity.nextActivity(
    cls: Class<out Activity>,
    requestCode: Int? = null,
    function: ((intent: Intent) -> Unit)? = null
) {
    val intent = Intent(this, cls)
    function?.let { it(intent) }
    if (requestCode == null)
        startActivity(intent)
    else
        startActivityForResult(intent, requestCode)
}

/**
 * AppCompatActivity注册Toolbar对象
 * @param toolbar Toolbar对象
 */
fun AppCompatActivity.setupToolbar(toolbar: Toolbar) {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setDisplayShowHomeEnabled(true)
}

/**
 * AppCompatActivity注册Toolbar对象
 * @param toolbar Toolbar对象
 */
fun AppCompatActivity.setupToolbarNotBack(toolbar: Toolbar) {
    setSupportActionBar(toolbar)
}

/**
 * 隐藏软件盘。有时候点击了按钮后，需要进行软键盘隐藏
 */
fun Activity.hideKeyboard() {
    val view: View? = this.currentFocus
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    view?.let { imm.hideSoftInputFromWindow(it.windowToken, 0) }
}

/**
 * 注册获取数据的方法
 * @param multiStateView MultiStateView对象，这个对象是用来显示各个不同的状态的
 * @param smartRefreshLayout SmartRefreshLayout对象，这个对象是用来负责刷新的
 * @param getData 获取数据的具体方法 SmartRefresh表示当前获取数据的状态
 */
fun setupGetData(
    multiStateView: MultiStateView?,
    smartRefreshLayout: SmartRefreshLayout?,
    getData: (smart: SmartRefresh) -> Unit
) {
    multiStateView?.setReloadListener { getData(SmartRefresh.START) }
    smartRefreshLayout?.setOnLoadMoreListener { getData(SmartRefresh.LOAD_MORE) }
    smartRefreshLayout?.setOnRefreshListener { getData(SmartRefresh.REFRESH) }
}

/**
 * @return 快速获取布局生成器
 */
fun Context.inflater() = LayoutInflater.from(this)!!

fun Context.toast(@StringRes id: Int, time: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, id, time).show()
}

fun Context.toast(id: String, time: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, id, time).show()
}

