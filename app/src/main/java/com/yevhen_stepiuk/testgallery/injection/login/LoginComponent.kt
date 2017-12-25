package com.yevhen_stepiuk.testgallery.injection.login

import com.yevhen_stepiuk.testgallery.presentation.main.login.LoginActivity
import dagger.Subcomponent


@LoginScope
@Subcomponent(modules = [LoginDataModule::class, LoginPresentationModule::class])
interface LoginComponent {

    fun inject(loginActivity: LoginActivity)

    @Subcomponent.Builder
    interface Builder {

        fun build(): LoginComponent

        fun dataModule(loginDataModule: LoginDataModule): Builder

        fun presentationModule(loginPresentationModule: LoginPresentationModule): Builder
    }
}