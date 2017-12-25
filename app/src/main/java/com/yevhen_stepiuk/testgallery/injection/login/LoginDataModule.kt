package com.yevhen_stepiuk.testgallery.injection.login

import android.content.ContentResolver
import com.google.gson.Gson
import com.yevhen_stepiuk.testgallery.data.api.GalleryAPI
import com.yevhen_stepiuk.testgallery.data.authorization.AuthorizationManagerImpl
import com.yevhen_stepiuk.testgallery.data.preferences.SharedPrefsWrapper
import com.yevhen_stepiuk.testgallery.domain.authorization.AuthorizationManager
import dagger.Module
import dagger.Provides


@Module
class LoginDataModule {

    @Provides
    @LoginScope
    internal fun provideAuthorizationManager(galleryAPI: GalleryAPI,
                                             contentResolver: ContentResolver,
                                             sharedPrefsWrapper: SharedPrefsWrapper,
                                             gson: Gson): AuthorizationManager =
            AuthorizationManagerImpl(galleryAPI, contentResolver, sharedPrefsWrapper, gson)
}