package com.yevhen_stepiuk.testgallery.domain.location

import com.yevhen_stepiuk.testgallery.domain.location.entity.GeoPos
import io.reactivex.Observable


interface LocationManager {

    fun getCurrentLocation(): Observable<GeoPos>
}