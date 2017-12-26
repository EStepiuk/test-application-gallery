package com.yevhen_stepiuk.testgallery.data.permission

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.yevhen_stepiuk.testgallery.domain.permissions.PermissionManager
import com.yevhen_stepiuk.testgallery.domain.permissions.errors.LocationPermissionDeniedException
import io.reactivex.Completable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject


class PermissionManagerImpl(private val context: Context) : PermissionManager {

    private var helperSubject = ReplaySubject.createWithSize<AppCompatActivity>(1)
    private val helperSingle get() = helperSubject.singleOrError()

    private var locationPermissionSubject = PublishSubject.create<Unit>()

    override fun requestLocationPermission(): Completable = Completable.defer {
        if (ContextCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED)
            return@defer Completable.complete()

        return@defer helperSingle
                .doOnSuccess { ActivityCompat.requestPermissions(it, arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION), LOCATION_REQUEST) }
                .flatMapCompletable { synchronized(this) { locationPermissionSubject.ignoreElements() } }
    }

    fun bindHelper(activity: AppCompatActivity) {
        synchronized(this) {
            if (helperSubject.hasComplete()) helperSubject = ReplaySubject.createWithSize(1)
            helperSubject.onNext(activity)
            helperSubject.onComplete()
        }
    }

    fun unbindHelper(activity: AppCompatActivity) {
        synchronized(this) {
            if (helperSubject.hasValue() && helperSubject.value == activity)
                helperSubject = ReplaySubject.createWithSize(1)
        }
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_REQUEST -> {
                synchronized(this) {
                    if (grantResults.any { it == PERMISSION_DENIED }) locationPermissionSubject.onError(LocationPermissionDeniedException())
                    else locationPermissionSubject.onComplete()
                    locationPermissionSubject = PublishSubject.create()
                }
            }
        }
    }

    private companion object {
        private const val LOCATION_REQUEST = 1212
    }
}