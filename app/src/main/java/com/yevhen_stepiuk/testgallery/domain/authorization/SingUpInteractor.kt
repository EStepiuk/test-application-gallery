package com.yevhen_stepiuk.testgallery.domain.authorization

import com.yevhen_stepiuk.testgallery.domain.authorization.entity.SignUpParam
import com.yevhen_stepiuk.testgallery.domain.base.CompletableInteractor
import io.reactivex.Completable
import javax.inject.Inject


class SingUpInteractor @Inject
internal constructor(private val authorizationManager: AuthorizationManager) : CompletableInteractor<SignUpParam>() {

    override fun buildCompletable(param: SignUpParam): Completable = authorizationManager.signUp(param)
}