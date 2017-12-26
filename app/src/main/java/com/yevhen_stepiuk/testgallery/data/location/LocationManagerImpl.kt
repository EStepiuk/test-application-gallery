package com.yevhen_stepiuk.testgallery.data.location

import android.content.Context
import android.location.Location
import com.yevhen_stepiuk.testgallery.domain.location.LocationManager
import com.yevhen_stepiuk.testgallery.domain.location.entity.GeoPos
import com.yevhen_stepiuk.testgallery.domain.location.errors.LocationDisabledException
import com.yevhen_stepiuk.testgallery.domain.permissions.PermissionManager
import io.nlopez.smartlocation.SmartLocation
import io.nlopez.smartlocation.rx.ObservableFactory
import io.reactivex.Observable


class LocationManagerImpl(private val context: Context,
                          private val permissionManager: PermissionManager) : LocationManager {

    override fun getCurrentLocation(): Observable<GeoPos> = permissionManager
            .requestLocationPermission()
            .andThen(Observable.defer {
                if (!SmartLocation.with(context).location().state().locationServicesEnabled())
                    return@defer Observable.error<Location>(LocationDisabledException())

                return@defer ObservableFactory
                        .from(SmartLocation.with(context).location().oneFix())
            })
            .map { GeoPosMapper.map(it) }
}