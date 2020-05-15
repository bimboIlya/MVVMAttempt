package com.example.mvvmattempt.ui.util

import android.view.View
import androidx.databinding.BindingAdapter


@BindingAdapter("app:visibility")
fun showHide(v: View, isVisible: Boolean) {
    v.visibility = if (isVisible) View.VISIBLE else View.GONE
}