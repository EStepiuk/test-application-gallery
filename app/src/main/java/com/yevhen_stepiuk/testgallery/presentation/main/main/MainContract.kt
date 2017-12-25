package com.yevhen_stepiuk.testgallery.presentation.main.main

import com.yevhen_stepiuk.testgallery.presentation.base.BaseContract


object MainContract {

    interface MainView : BaseContract.View {

        fun setAvatarUriString(avatarUriString: String?)

        fun setLoading(b: Boolean)

        fun showListError(throwable: Throwable)

        fun hideListError()

        fun addPhotoItems(items: List<PhotoVM>)

        fun addPhotoItem(item: PhotoVM) {
            addPhotoItems(listOf(item))
        }

        fun showGifDialog(gifUriString: String)

        fun showGifError(throwable: Throwable)
    }

    interface MainRouter : BaseContract.Router {

        fun openAddPhotoScreen()

        fun openLoginScreen()
    }

    interface MainPresenter : BaseContract.Presenter<MainView, MainRouter> {

        fun onRetryClick()

        fun onExitClick()

        fun onAddPhotoClick()

        fun onPlayGifClick()
    }
}