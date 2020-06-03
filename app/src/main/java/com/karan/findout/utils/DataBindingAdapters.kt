package com.karan.findout.utils

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.karan.findout.R

@BindingAdapter("app:firstOrBlank", "app:firstOrBlankSecondary", requireAll = false)
fun TextView.firstOrBlank(items: Array<String>?, secondaryItems: Array<String>?) {
    val first = items?.firstOrNull() ?: secondaryItems?.firstOrNull()
    text = first ?: ""
}

@BindingAdapter("goneUnless")
fun View.goneUnless(condition: Boolean) {
    visibility = if (condition) View.VISIBLE else View.GONE
}

@BindingAdapter("app:imageUrl")
fun ShapeableImageView.loadImage(imageUrl: String?) {
    Glide.with(this).load(imageUrl).placeholder(R.drawable.placeholder).into(this)
}
