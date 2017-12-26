package com.yevhen_stepiuk.testgallery.data.gallery

import com.yevhen_stepiuk.testgallery.common.Mapper
import com.yevhen_stepiuk.testgallery.data.api.body.AllPhotosResponse
import com.yevhen_stepiuk.testgallery.domain.gallery.entity.Photo


object PhotoFromImageMapper : Mapper<AllPhotosResponse.Image, Photo> {

    override fun map(from: AllPhotosResponse.Image) = Photo(
            from.smallImagePath,
            from.parameters.weather,
            from.parameters.address ?: from.parameters.geoPos?.toString() ?: ""
    )
}