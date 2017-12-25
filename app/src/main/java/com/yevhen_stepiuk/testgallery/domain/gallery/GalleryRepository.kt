package com.yevhen_stepiuk.testgallery.domain.gallery

import com.yevhen_stepiuk.testgallery.domain.gallery.entity.AddPhotoParam
import com.yevhen_stepiuk.testgallery.domain.gallery.entity.Gif
import com.yevhen_stepiuk.testgallery.domain.gallery.entity.Photo
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single


interface GalleryRepository {

    fun getPhotos(): Observable<List<Photo>>

    fun getGif(): Single<Gif>

    fun addPhoto(addPhotoParam: AddPhotoParam): Completable
}