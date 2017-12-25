package com.yevhen_stepiuk.testgallery.data.preferences

import com.yevhen_stepiuk.testgallery.domain.prefs.Preferences


class PreferencesImpl(private val prefsWrapper: SharedPrefsWrapper) : Preferences {

    override val isLoggedIn: Boolean get() = prefsWrapper.apiToken != null
    override val avatarUriString: String? get() = prefsWrapper.avatarUriString

    override fun reset() {
        prefsWrapper.reset()
    }
}