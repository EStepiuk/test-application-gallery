package com.yevhen_stepiuk.testgallery.extension

import android.support.v7.widget.RecyclerView


val RecyclerView.Adapter<*>.isEmpty get() = itemCount == 0

val RecyclerView.Adapter<*>.isNotEmpty get() = !isEmpty