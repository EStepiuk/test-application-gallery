package com.yevhen_stepiuk.testgallery

import android.app.Application
import android.content.Context
import com.yevhen_stepiuk.testgallery.injection.NetModule
import com.yevhen_stepiuk.testgallery.injection.app.*
import timber.log.Timber


class GalleryApp : Application() {

    companion object {

        fun getComponent(context: Context) = (context.applicationContext as GalleryApp).component
    }

    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .domainModule(DomainModule())
                .presentationModule(PresentationModule())
                .netModule(NetModule())
                .utilModule(UtilModule())
                .dataModule(DataModule())
                .build()
    }
}