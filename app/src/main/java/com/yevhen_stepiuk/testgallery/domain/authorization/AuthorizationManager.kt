package com.yevhen_stepiuk.testgallery.domain.authorization

import com.yevhen_stepiuk.testgallery.domain.authorization.entity.LoginParam
import com.yevhen_stepiuk.testgallery.domain.authorization.entity.SignUpParam
import io.reactivex.Completable


interface AuthorizationManager {

    fun login(loginParam: LoginParam): Completable

    fun signUp(signUpParam: SignUpParam): Completable
}