package com.yevhen_stepiuk.testgallery.domain.location.entity


data class GeoPos(val latitude: Double, val longitude: Double) {
    override fun toString() = "$latitude, $longitude"
}