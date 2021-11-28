package com.sora.bindingadapterlib

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import androidx.databinding.BindingAdapter

object ViewBindings {
    @BindingAdapter("view:visibility", "view:visibility_duration", requireAll = false)
    @JvmStatic
    fun <T> setVisible(view: View, visibility: Int, visibilityDuration: Long) {
        if (view.visibility == visibility) {
            return
        } else {
            val d = if (visibilityDuration == 0L) 200L else visibilityDuration
            when (visibility) {
                View.VISIBLE -> ObjectAnimator.ofFloat(view, "alpha", 0f, 1f).apply {
                    duration = d
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(p0: Animator?) {
                            view.visibility = visibility
                        }
                    })
                }.start()
                View.INVISIBLE, View.GONE -> ObjectAnimator.ofFloat(view, "alpha", 1f, 0f).apply {
                    duration = d
                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(p0: Animator?) {
                            view.visibility = visibility
                        }
                    })
                }.start()
            }
        }

    }
}