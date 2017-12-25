package com.yevhen_stepiuk.testgallery.data.gallery

import com.yevhen_stepiuk.testgallery.data.api.GalleryAPI
import com.yevhen_stepiuk.testgallery.domain.gallery.GalleryRepository
import com.yevhen_stepiuk.testgallery.domain.gallery.entity.AddPhotoParam
import com.yevhen_stepiuk.testgallery.domain.gallery.entity.Gif
import com.yevhen_stepiuk.testgallery.domain.gallery.entity.Photo
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single


class GalleryRepositoryImpl(private val galleryAPI: GalleryAPI,
                            private val apiToken: String) : GalleryRepository {

    //TODO: flat map request result
    override fun getPhotos(): Observable<List<Photo>> = galleryAPI
            .allPhotos(apiToken)
            .map { it.images.map(PhotoMapper::map) }
            .toObservable()

    override fun getGif(): Single<Gif> = galleryAPI
            .getGif(apiToken)
            .map(GifMapper::map)

    override fun addPhoto(addPhotoParam: AddPhotoParam): Completable = TODO()
}