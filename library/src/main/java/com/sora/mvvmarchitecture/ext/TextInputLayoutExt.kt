package com.sora.mvvmarchitecture.ext

import com.google.android.material.textfield.TextInputLayout

/**
 * Created by zheng on 17-10-30.
 * TextInputLayout扩展
 */

/**
 * @return TextInputLayout中EditText的内容
 */
fun TextInputLayout.content(): String = this.editText?.text.toString()
