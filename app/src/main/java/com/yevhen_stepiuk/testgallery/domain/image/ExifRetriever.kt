package com.yevhen_stepiuk.testgallery.domain.image

import com.yevhen_stepiuk.testgallery.domain.location.entity.GeoPos


interface ExifRetriever {

    val geoPos: GeoPos?

    interface Factory {

        fun from(imageUriString: String): ExifRetriever
    }
}