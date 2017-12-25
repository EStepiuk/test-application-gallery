package com.yevhen_stepiuk.testgallery.data.preferences

import android.content.SharedPreferences
import com.yevhen_stepiuk.testgallery.common.Action
import com.yevhen_stepiuk.testgallery.extension.putString


class SharedPrefsWrapper(private val prefs: SharedPreferences) {

    var resetListener: Action? = null

    companion object {
        private const val API_TOKEN = "API_TOKEN"
        private const val AVATAR = "AVATAR"
    }

    var apiToken: String?
        get() = prefs.getString(API_TOKEN, null)
        set(value) = prefs.putString(API_TOKEN, value)
    var avatarUriString: String?
        get() = prefs.getString(AVATAR, null)
        set(value) = prefs.putString(AVATAR, value)

    fun reset() {
        prefs.edit().clear().apply()
        resetListener?.invoke()
    }
}