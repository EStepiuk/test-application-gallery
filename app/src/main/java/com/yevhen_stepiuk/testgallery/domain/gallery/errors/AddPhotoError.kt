package com.yevhen_stepiuk.testgallery.domain.gallery.errors


class AddPhotoError(val errorField: ErrorField, override val message: String) : Throwable(message) {

    enum class ErrorField { DESCRIPTION, HASHTAG, IMAGE }
}