package com.yevhen_stepiuk.testgallery.domain.gallery

import com.uavalley.demochat.domain.base.NoParamSingleInteractor
import com.yevhen_stepiuk.testgallery.domain.gallery.entity.Gif
import javax.inject.Inject


class GetGifInteractor @Inject
internal constructor(private val galleryRepository: GalleryRepository) : NoParamSingleInteractor<Gif>() {

    override fun buildSingle() = galleryRepository.getGif()
}