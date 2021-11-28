package com.sora.mvvmarchitecture.ext

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment

/**
 * 跳转到下一个页面
 * @param cls 需要跳转的Activity的class对象
 * @param requestCode 返回码，不传就表示无返回值
 * @param function 回调intent用于传值
 */
fun Fragment.nextActivity(
    cls: Class<out Activity>,
    requestCode: Int? = null,
    function: ((intent: Intent) -> Unit)? = null
) {
    val intent = Intent(context, cls)
    function?.let { it(intent) }
    if (requestCode == null)
        startActivity(intent)
    else
        startActivityForResult(intent, requestCode)
}