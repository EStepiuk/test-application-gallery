package com.yevhen_stepiuk.testgallery.injection.app

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.yevhen_stepiuk.testgallery.BuildConfig
import com.yevhen_stepiuk.testgallery.data.preferences.SharedPrefsWrapper
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient


@Module
class UtilModule {

    @Provides
    @AppScope
    internal fun provideGson(): Gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

    @Provides
    @AppScope
    internal fun providePicasso(okHttpClient: OkHttpClient, context: Context): Picasso = Picasso.Builder(context)
            .downloader(OkHttp3Downloader(okHttpClient))
            .loggingEnabled(BuildConfig.DEBUG)
            .build()

    @Provides
    @AppScope
    internal fun providePrefsWrapper(sharedPreferences: SharedPreferences): SharedPrefsWrapper =
            SharedPrefsWrapper(sharedPreferences)
}