package com.yevhen_stepiuk.testgallery.presentation.main.main

import com.yevhen_stepiuk.testgallery.common.Mapper
import com.yevhen_stepiuk.testgallery.domain.gallery.entity.Photo


object PhotoVMMapper : Mapper<Photo, PhotoVM> {

    override fun map(from: Photo) = PhotoVM(
            from.photoUriString,
            from.weather,
            from.address
    )
}