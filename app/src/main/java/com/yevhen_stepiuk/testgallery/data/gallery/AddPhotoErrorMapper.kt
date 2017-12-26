package com.yevhen_stepiuk.testgallery.data.gallery

import com.yevhen_stepiuk.testgallery.common.Mapper
import com.yevhen_stepiuk.testgallery.data.api.body.Errors
import com.yevhen_stepiuk.testgallery.domain.gallery.errors.AddPhotoError
import com.yevhen_stepiuk.testgallery.extension.concatToSingleString


object AddPhotoErrorMapper : Mapper<Map<String, Errors>, AddPhotoError?> {

    override fun map(from: Map<String, Errors>): AddPhotoError? {
        val image = from["image"]?.errors?.concatToSingleString()
        if (image?.isNotEmpty() == true) return AddPhotoError(AddPhotoError.ErrorField.IMAGE, image)

        val description = from["description"]?.errors?.concatToSingleString()
        if (description?.isNotEmpty() == true) return AddPhotoError(AddPhotoError.ErrorField.DESCRIPTION, description)

        val hashtag = from["hashtag"]?.errors?.concatToSingleString()
        if (hashtag?.isNotEmpty() == true) return AddPhotoError(AddPhotoError.ErrorField.HASHTAG, hashtag)

        return null
    }
}