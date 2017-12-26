package com.yevhen_stepiuk.testgallery.data.api.body

import com.yevhen_stepiuk.testgallery.domain.location.entity.GeoPos


data class ImageParameters(val address: String?,
                           val latitude: Double?,
                           val longitude: Double?,
                           val weather: String) {
    val geoPos
        get() =
            if (latitude == null || longitude == null) null
            else GeoPos(latitude, longitude)
}