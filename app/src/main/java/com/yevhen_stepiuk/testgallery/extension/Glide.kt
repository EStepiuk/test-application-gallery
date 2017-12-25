package com.yevhen_stepiuk.testgallery.extension

import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions


fun RequestBuilder<*>.resizePolitly(with: Int, height: Int) =
        apply(RequestOptions().override(with, height).centerCrop())

fun RequestBuilder<*>.resizePolitly(size: Int) =
        apply(RequestOptions().override(size).centerCrop())