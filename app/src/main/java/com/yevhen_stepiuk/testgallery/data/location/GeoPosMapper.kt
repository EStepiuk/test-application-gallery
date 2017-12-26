package com.yevhen_stepiuk.testgallery.data.location

import android.location.Location
import com.yevhen_stepiuk.testgallery.common.Mapper
import com.yevhen_stepiuk.testgallery.domain.location.entity.GeoPos


object GeoPosMapper : Mapper<Location, GeoPos> {

    override fun map(from: Location) = GeoPos(
            from.latitude,
            from.longitude
    )
}