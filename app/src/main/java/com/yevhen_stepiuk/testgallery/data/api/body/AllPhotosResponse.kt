package com.yevhen_stepiuk.testgallery.data.api.body

import com.google.gson.annotations.SerializedName


data class AllPhotosResponse(val images: List<Image>) {

    data class Image(val description: String,
                     val hashtag: String,
                     val parameters: ImageParameters,
                     @SerializedName("smallImagePath") val smallImagePath: String,
                     @SerializedName("bigImagePath") val bigImagePath: String)
}