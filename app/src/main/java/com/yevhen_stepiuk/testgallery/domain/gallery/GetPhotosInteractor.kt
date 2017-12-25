package com.yevhen_stepiuk.testgallery.domain.gallery

import com.yevhen_stepiuk.testgallery.domain.base.NoParamObservableInteractor
import com.yevhen_stepiuk.testgallery.domain.gallery.entity.Photo
import javax.inject.Inject


class GetPhotosInteractor @Inject
internal constructor(private val galleryRepository: GalleryRepository) : NoParamObservableInteractor<List<Photo>>() {

    override fun buildObservable() = galleryRepository.getPhotos()
}