package com.yevhen_stepiuk.testgallery.injection.app

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides


@Module
class AppModule(private var app: Application) {

    @Provides
    @AppScope
    internal fun provideContext(): Context = app

    @Provides
    @AppScope
    internal fun provideContentResolver(context: Context): ContentResolver = context.contentResolver

    @Provides
    @AppScope
    internal fun provideSharedPreferences(context: Context): SharedPreferences =
            context.getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE)
}