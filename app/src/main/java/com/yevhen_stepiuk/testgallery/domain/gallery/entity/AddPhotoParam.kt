package com.yevhen_stepiuk.testgallery.domain.gallery.entity


data class AddPhotoParam(val photoUriString: String,
                         val description: String,
                         val hashTag: String,
                         val latitude: Double,
                         val longitude: Double)