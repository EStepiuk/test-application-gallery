package com.yevhen_stepiuk.testgallery.domain.location

import com.yevhen_stepiuk.testgallery.domain.base.NoParamObservableInteractor
import com.yevhen_stepiuk.testgallery.domain.location.entity.GeoPos
import io.reactivex.Observable
import javax.inject.Inject


class GetLocationInteractor @Inject
internal constructor(private val locationManager: LocationManager) : NoParamObservableInteractor<GeoPos>() {

    override fun buildObservable(): Observable<GeoPos> = locationManager.getCurrentLocation()
}