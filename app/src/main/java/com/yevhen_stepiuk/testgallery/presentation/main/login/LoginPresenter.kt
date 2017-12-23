package com.yevhen_stepiuk.testgallery.presentation.main.login

import com.yevhen_stepiuk.testgallery.domain.authorization.LoginInteractor
import com.yevhen_stepiuk.testgallery.domain.authorization.SingUpInteractor
import com.yevhen_stepiuk.testgallery.domain.authorization.entity.LoginParam
import com.yevhen_stepiuk.testgallery.domain.authorization.entity.SignUpParam
import com.yevhen_stepiuk.testgallery.presentation.base.BasePresenter


class LoginPresenter(private val loginInteractor: LoginInteractor,
                     private val signUpInteractor: SingUpInteractor) :
        BasePresenter<LoginContract.LoginView, LoginContract.LoginRouter>(), LoginContract.LoginPresenter {

    override fun onSignUpClick() {
        onView {
            when {
                email.isEmpty() -> showInputEmailMessage()
                password.isEmpty() -> showInputPasswordMessage()
                username.isEmpty() -> showInputUsernameMessage()
                else -> {
                    setLoading(true)
                    signUpInteractor.execute(
                            SignUpParam(username, email, password, avatarUriString),
                            {
                                onView { setLoading(false) }
                                onRouter { openMainScreen() }
                            },
                            {
                                onView {
                                    setLoading(false)
                                    showError(it)
                                }
                            })
                }
            }
        }
    }

    override fun onSignInClick() {
        onView {
            when {
                email.isEmpty() -> showInputEmailMessage()
                password.isEmpty() -> showInputPasswordMessage()
                else -> {
                    setLoading(true)
                    loginInteractor.execute(
                            LoginParam(email, password),
                            {
                                onView { setLoading(false) }
                                onRouter { openMainScreen() }
                            },
                            {
                                onView {
                                    setLoading(false)
                                    showError(it)
                                }
                            })
                }
            }
        }
    }

    override fun onAvatarClick() {
        onRouter { openChooseAvatarScreenForResult() }
    }

    override fun onAvatarPicked(avatarUriString: String) {
        onView { this@onView.avatarUriString = avatarUriString }
    }
}