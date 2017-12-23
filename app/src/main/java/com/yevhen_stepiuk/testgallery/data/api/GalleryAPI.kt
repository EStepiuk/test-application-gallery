package com.yevhen_stepiuk.testgallery.data.api

import com.yevhen_stepiuk.testgallery.data.api.body.LoginResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*


interface GalleryAPI {

    @Multipart
    @POST("create")
    fun createUser(@Part("username") username: String,
                   @Part("email") email: String,
                   @Part("password") password: String,
                   @Part avatarPart: MultipartBody.Part?): Single<LoginResponse>

    @POST("login")
    @FormUrlEncoded
    fun login(@Field("email") email: String,
              @Field("password") password: String): Single<LoginResponse>
}