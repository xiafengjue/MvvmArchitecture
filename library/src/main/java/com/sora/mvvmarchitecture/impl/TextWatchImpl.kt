package com.sora.mvvmarchitecture.impl

import android.text.Editable
import android.text.TextWatcher

/**
 * EditText的输入监听器
 */
open class TextWatchImpl : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun afterTextChanged(p0: Editable?) {
    }
}