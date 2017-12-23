package com.yevhen_stepiuk.testgallery.injection.app

import android.content.ContentResolver
import com.google.gson.Gson
import com.yevhen_stepiuk.testgallery.data.api.GalleryAPI
import com.yevhen_stepiuk.testgallery.data.authorization.AuthorizationManagerImpl
import com.yevhen_stepiuk.testgallery.data.preferences.PreferencesImpl
import com.yevhen_stepiuk.testgallery.data.preferences.SharedPrefsWrapper
import com.yevhen_stepiuk.testgallery.domain.authorization.AuthorizationManager
import com.yevhen_stepiuk.testgallery.domain.prefs.Preferences
import dagger.Module
import dagger.Provides


@Module
class DataModule {

    @Provides
    @AppScope
    internal fun providePreferences(sharedPrefsWrapper: SharedPrefsWrapper): Preferences =
            PreferencesImpl(sharedPrefsWrapper)

    @Provides
    @AppScope
    internal fun provideAuthorizationManage(galleryAPI: GalleryAPI,
                                            contentResolver: ContentResolver,
                                            sharedPrefsWrapper: SharedPrefsWrapper,
                                            gson: Gson): AuthorizationManager =
            AuthorizationManagerImpl(galleryAPI, contentResolver, sharedPrefsWrapper, gson)
}