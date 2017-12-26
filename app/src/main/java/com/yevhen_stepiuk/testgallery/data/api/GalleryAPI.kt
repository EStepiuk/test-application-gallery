package com.yevhen_stepiuk.testgallery.data.api

import com.yevhen_stepiuk.testgallery.data.api.body.AddPhotoResponse
import com.yevhen_stepiuk.testgallery.data.api.body.AllPhotosResponse
import com.yevhen_stepiuk.testgallery.data.api.body.GifResponse
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

    @GET("all")
    fun allPhotos(@Header("token") token: String): Single<AllPhotosResponse>

    @GET("gif")
    fun getGif(@Header("token") token: String): Single<GifResponse>

    @Multipart
    @POST("image")
    fun addImage(@Header("token") token: String,
                 @Part("description") description: String,
                 @Part("hashtag") hashtag: String,
                 @Part("latitude") latitude: Double,
                 @Part("longitude") longitude: Double,
                 @Part imagePart: MultipartBody.Part): Single<AddPhotoResponse>
}