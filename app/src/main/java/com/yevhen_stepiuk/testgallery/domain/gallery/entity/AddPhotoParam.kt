package com.yevhen_stepiuk.testgallery.domain.gallery.entity

import com.yevhen_stepiuk.testgallery.domain.location.entity.GeoPos


data class AddPhotoParam(val photoUriString: String,
                         val description: String,
                         val hashTag: String,
                         val geoPos: GeoPos)