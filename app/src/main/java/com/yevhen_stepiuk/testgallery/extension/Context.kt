package com.yevhen_stepiuk.testgallery.extension

import android.content.Context
import com.yevhen_stepiuk.testgallery.R
import java.net.SocketTimeoutException


fun Context.getErrorMessage(throwable: Throwable) = when (throwable) {
    is SocketTimeoutException -> getString(R.string.check_your_network)
    else -> getString(R.string.something_goes_wrong)
}