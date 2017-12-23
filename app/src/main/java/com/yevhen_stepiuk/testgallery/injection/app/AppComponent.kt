package com.yevhen_stepiuk.testgallery.injection.app

import com.yevhen_stepiuk.testgallery.injection.NetModule
import com.yevhen_stepiuk.testgallery.presentation.main.login.LoginActivity
import dagger.Component


@AppScope
@Component(
        modules = [
            AppModule::class,
            DomainModule::class,
            PresentationModule::class,
            NetModule::class,
            UtilModule::class,
            DataModule::class
        ]
)
interface AppComponent {

    fun inject(loginActivity: LoginActivity)
}