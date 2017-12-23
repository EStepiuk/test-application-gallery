package com.yevhen_stepiuk.testgallery.data.api.body

import com.google.gson.annotations.SerializedName


data class CreateError(val children: Children?) {

    data class Children(@SerializedName("username") val usernameErrors: Errors,
                        @SerializedName("email") val emailErrors: Errors,
                        @SerializedName("password") val passwordErrors: Errors,
                        @SerializedName("avatar") val avatarErrors: Errors) {

        data class Errors(val errors: List<String>?) {

            val error: String? = errors?.firstOrNull()

        }
    }
}