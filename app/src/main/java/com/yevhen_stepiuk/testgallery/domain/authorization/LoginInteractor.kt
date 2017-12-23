package com.yevhen_stepiuk.testgallery.domain.authorization

import com.yevhen_stepiuk.testgallery.domain.authorization.entity.LoginParam
import com.yevhen_stepiuk.testgallery.domain.base.CompletableInteractor
import javax.inject.Inject


class LoginInteractor @Inject
internal constructor(private val authorizationManager: AuthorizationManager) : CompletableInteractor<LoginParam>() {

    override fun buildCompletable(param: LoginParam) = authorizationManager.login(param)
}