package com.example.mvvmattempt.ui.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.mvvmattempt.R


@BindingAdapter("app:visibility")
fun showHide(v: View, isVisible: Boolean) {
    v.visibility = if (isVisible) View.VISIBLE else View.GONE
}

// https://github.com/bumptech/glide/issues/4074
// without use of headers image won't load (IOException)
@BindingAdapter("app:imageFromUrl")
fun imageFromUrl(v: ImageView, url: String) {
    val fromPlaceholderCom = url.contains("via.placeholder.com")
    Glide.with(v.context)
        .load(if (fromPlaceholderCom) GlideUrl(url, LazyHeaders.Builder().addHeader("User-Agent", "MVVMAttempt").build()) else url)
        .fitCenter()
        .placeholder(R.drawable.placeholder)
        .into(v)
}