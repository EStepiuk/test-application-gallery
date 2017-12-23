package com.yevhen_stepiuk.testgallery.extension


import android.widget.EditText
import android.widget.TextView

val TextView.isEmpty get() = text.isEmpty()

val EditText.input get() = text.toString()