package com.yevhen_stepiuk.testgallery.injection.app

import com.yevhen_stepiuk.testgallery.domain.authorization.LoginInteractor
import com.yevhen_stepiuk.testgallery.domain.authorization.SingUpInteractor
import com.yevhen_stepiuk.testgallery.presentation.main.login.LoginContract
import com.yevhen_stepiuk.testgallery.presentation.main.login.LoginPresenter
import dagger.Module
import dagger.Provides


@Module
class PresentationModule {

    @Provides
    @AppScope
    internal fun provideLoginPresenter(loginInteractor: LoginInteractor,
                                       signUpInteractor: SingUpInteractor): LoginContract.LoginPresenter =
            LoginPresenter(loginInteractor, signUpInteractor)
}