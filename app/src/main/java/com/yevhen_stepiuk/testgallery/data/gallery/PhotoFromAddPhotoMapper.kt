package com.yevhen_stepiuk.testgallery.data.gallery

import com.yevhen_stepiuk.testgallery.common.Mapper
import com.yevhen_stepiuk.testgallery.data.api.body.AddPhotoResponse
import com.yevhen_stepiuk.testgallery.domain.gallery.entity.Photo


object PhotoFromAddPhotoMapper : Mapper<AddPhotoResponse, Photo> {

    override fun map(from: AddPhotoResponse) = Photo(
            from.smallImage,
            from.parameters.weather,
            from.parameters.address ?: from.parameters.geoPos?.toString() ?: ""
    )
}