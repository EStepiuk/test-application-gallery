package com.yevhen_stepiuk.testgallery.injection.app

import android.content.ContentResolver
import android.content.SharedPreferences
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yevhen_stepiuk.testgallery.data.image.ExifRetrieverFactoryImpl
import com.yevhen_stepiuk.testgallery.data.preferences.SharedPrefsWrapper
import com.yevhen_stepiuk.testgallery.domain.image.ExifRetriever
import dagger.Module
import dagger.Provides


@Module
class UtilModule {

    @Provides
    @AppScope
    internal fun provideGson(): Gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

    @Provides
    @AppScope
    internal fun providePrefsWrapper(sharedPreferences: SharedPreferences): SharedPrefsWrapper =
            SharedPrefsWrapper(sharedPreferences)

    @Provides
    @AppScope
    internal fun provideExifRetrieverFactory(contentResolver: ContentResolver): ExifRetriever.Factory =
            ExifRetrieverFactoryImpl(contentResolver)
}