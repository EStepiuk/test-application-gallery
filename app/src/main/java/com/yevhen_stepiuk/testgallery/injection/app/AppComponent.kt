package com.yevhen_stepiuk.testgallery.injection.app

import com.yevhen_stepiuk.testgallery.GalleryApp
import com.yevhen_stepiuk.testgallery.injection.authorized.AuthorizedComponent
import com.yevhen_stepiuk.testgallery.injection.login.LoginComponent
import com.yevhen_stepiuk.testgallery.presentation.main.main.GifDialog
import com.yevhen_stepiuk.testgallery.presentation.main.splash.SplashActivity
import dagger.Component
import okhttp3.OkHttpClient


@AppScope
@Component(
        modules = [
            AppModule::class,
            DomainModule::class,
            NetModule::class,
            UtilModule::class,
            DataModule::class
        ]
)
interface AppComponent {

    fun inject(galleryApp: GalleryApp)

    fun inject(splashActivity: SplashActivity)

    fun inject(gifDialog: GifDialog)

    fun loginSubcomponentBuilder(): LoginComponent.Builder

    fun authorizedSubcomponentBuilder(): AuthorizedComponent.Builder

    //This used to provide OkHttpClient to GlideConfiguration
    fun provideOkHttpClient(): OkHttpClient
}