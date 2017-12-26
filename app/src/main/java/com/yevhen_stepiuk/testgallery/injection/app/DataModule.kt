package com.yevhen_stepiuk.testgallery.injection.app

import android.content.Context
import com.yevhen_stepiuk.testgallery.data.location.LocationManagerImpl
import com.yevhen_stepiuk.testgallery.data.permission.PermissionManagerImpl
import com.yevhen_stepiuk.testgallery.data.preferences.PreferencesImpl
import com.yevhen_stepiuk.testgallery.data.preferences.SharedPrefsWrapper
import com.yevhen_stepiuk.testgallery.domain.location.LocationManager
import com.yevhen_stepiuk.testgallery.domain.permissions.PermissionManager
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
    internal fun providePermissionManagerImpl(context: Context): PermissionManagerImpl =
            PermissionManagerImpl(context)

    @Provides
    @AppScope
    internal fun providePermissionManager(permissionManagerImpl: PermissionManagerImpl): PermissionManager =
            permissionManagerImpl

    @Provides
    @AppScope
    internal fun provideLocationManager(context: Context, permissionManager: PermissionManager): LocationManager =
            LocationManagerImpl(context, permissionManager)
}
