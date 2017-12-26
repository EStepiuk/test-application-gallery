package com.yevhen_stepiuk.testgallery.presentation.main.add_photo

import com.yevhen_stepiuk.testgallery.presentation.base.BaseContract


object AddPhotoContract {

    interface AddPhotoView : BaseContract.View {

        var description: String
        var hashtag: String

        fun setLoading(b: Boolean = true)

        fun showError(throwable: Throwable)

        fun showPhoto(photoUriString: String)

        fun showChoosePhotoMessage()

        fun showInputDescriptionMessage()

    }

    interface AddPhotoRouter : BaseContract.Router {

        fun openMainScreen()

        fun openChoosePhotoScreenForResult()
    }

    interface AddPhotoPresenter : BaseContract.Presenter<AddPhotoView, AddPhotoRouter> {

        fun onPhotoClick()

        fun onUploadClick()

        fun onCancelUploadClick()

        fun onPhotoSelected(photoUriString: String)
    }
}