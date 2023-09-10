package com.example.templateapp.utils

import androidx.databinding.BindingAdapter
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.google.android.material.imageview.ShapeableImageView

@BindingAdapter("avatar")
fun ShapeableImageView.avatar(
    url: String?
) {
    url?.let {
        val imageLoader = context.imageLoader
        val request = ImageRequest.Builder(context)
            .data(it)
            .target(this)
            .memoryCachePolicy(CachePolicy.DISABLED)
            .diskCachePolicy(CachePolicy.DISABLED)
            .build()
        imageLoader.enqueue(request)
    }
}