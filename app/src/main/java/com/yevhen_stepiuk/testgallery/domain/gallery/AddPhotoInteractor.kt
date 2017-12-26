package com.yevhen_stepiuk.testgallery.domain.gallery

import com.yevhen_stepiuk.testgallery.domain.base.CompletableInteractor
import com.yevhen_stepiuk.testgallery.domain.gallery.entity.AddPhotoParam
import javax.inject.Inject


class AddPhotoInteractor @Inject
internal constructor(private val galleryRepository: GalleryRepository) : CompletableInteractor<AddPhotoParam>() {

    override fun buildCompletable(param: AddPhotoParam) = galleryRepository.addPhoto(param)
}