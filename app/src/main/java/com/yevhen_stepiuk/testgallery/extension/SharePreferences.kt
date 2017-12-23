package com.yevhen_stepiuk.testgallery.extension

import android.content.SharedPreferences


fun SharedPreferences.putString(name: String, value: String?) {
    edit().putString(name, value).apply()
}