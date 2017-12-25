package com.yevhen_stepiuk.testgallery.extension

import android.content.Context
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import com.yevhen_stepiuk.testgallery.R
import java.net.SocketTimeoutException
import java.net.UnknownHostException


fun Context.getErrorMessage(throwable: Throwable): String = when (throwable) {
    is SocketTimeoutException, is UnknownHostException -> getString(R.string.check_your_network)
    else -> getString(R.string.something_went_wrong)
}

fun Context.getColorCompat(@ColorRes res: Int) = ContextCompat.getColor(this, res)