package com.yevhen_stepiuk.testgallery.data.api.body

import com.google.gson.annotations.SerializedName


data class AddPhotoResponse(val parameters: ImageParameters,
                            @SerializedName("smallImage") val smallImage: String,
                            @SerializedName("bigImage") val bigImage: String)