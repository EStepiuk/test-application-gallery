package com.yevhen_stepiuk.testgallery.domain.permissions

import io.reactivex.Completable


interface PermissionManager {

    fun requestLocationPermission(): Completable
}