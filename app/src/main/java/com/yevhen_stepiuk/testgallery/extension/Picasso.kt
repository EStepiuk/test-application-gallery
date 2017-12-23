package com.yevhen_stepiuk.testgallery.extension

import android.support.annotation.DrawableRes
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator


fun Picasso.load(path: String?, @DrawableRes placeholder: Int): RequestCreator =
        path?.let { load(it).placeholder(placeholder) } ?: load(placeholder)

fun RequestCreator.resizePolitly(targetWidth: Int, targetHeight: Int): RequestCreator =
        resize(targetWidth, targetHeight).centerCrop()