package com.yevhen_stepiuk.testgallery.presentation.main.login

import com.yevhen_stepiuk.testgallery.presentation.base.BaseContract


object LoginContract {

    interface LoginView : BaseContract.View {

        var username: String
        var password: String
        var email: String
        var avatarUriString: String?

        fun setLoading(b: Boolean)

        fun showError(throwable: Throwable)

        fun showInputEmailMessage()

        fun showInputPasswordMessage()

        fun showInputUsernameMessage()
    }

    interface LoginRouter : BaseContract.Router {

        fun openMainScreen()

        fun openChooseAvatarScreenForResult()
    }

    interface LoginPresenter : BaseContract.Presenter<LoginView, LoginRouter> {

        fun onSignUpClick()

        fun onSignInClick()

        fun onAvatarClick()

        fun onAvatarPicked(avatarUriString: String)
    }
}