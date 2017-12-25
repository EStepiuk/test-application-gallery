package com.yevhen_stepiuk.testgallery.injection.app

import com.yevhen_stepiuk.testgallery.data.preferences.PreferencesImpl
import com.yevhen_stepiuk.testgallery.data.preferences.SharedPrefsWrapper
import com.yevhen_stepiuk.testgallery.domain.prefs.Preferences
import dagger.Module
import dagger.Provides


@Module
class DataModule {

    @Provides
    @AppScope
    internal fun providePreferences(sharedPrefsWrapper: SharedPrefsWrapper): Preferences =
            PreferencesImpl(sharedPrefsWrapper)
}