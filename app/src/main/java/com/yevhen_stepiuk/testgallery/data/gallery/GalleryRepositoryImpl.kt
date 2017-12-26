package com.yevhen_stepiuk.testgallery.data.gallery

import android.content.ContentResolver
import android.net.Uri
import com.google.gson.Gson
import com.yevhen_stepiuk.testgallery.data.api.GalleryAPI
import com.yevhen_stepiuk.testgallery.data.api.body.CompositeError
import com.yevhen_stepiuk.testgallery.domain.gallery.GalleryRepository
import com.yevhen_stepiuk.testgallery.domain.gallery.entity.AddPhotoParam
import com.yevhen_stepiuk.testgallery.domain.gallery.entity.Gif
import com.yevhen_stepiuk.testgallery.domain.gallery.entity.Photo
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException


class GalleryRepositoryImpl(private val galleryAPI: GalleryAPI,
                            private val apiToken: String,
                            private val contentResolver: ContentResolver,
                            private val gson: Gson) : GalleryRepository {

    private val newPhotosSubject = PublishSubject.create<Photo>()

    override fun getPhotos(): Observable<List<Photo>> = Observable
            .concat(
                    galleryAPI.allPhotos(apiToken)
                            .map { it.images.map(PhotoFromImageMapper::map) }
                            .toObservable(),
                    newPhotosSubject.map { listOf(it) }
            )

    override fun getGif(): Single<Gif> = galleryAPI
            .getGif(apiToken)
            .map(GifMapper::map)

    override fun addPhoto(addPhotoParam: AddPhotoParam): Completable = Single
            .defer {
                val rb = RequestBody.create(MediaType.parse("image/*"), contentResolver.openInputStream(Uri.parse(addPhotoParam.photoUriString)).readBytes())
                val imagePart = MultipartBody.Part.createFormData("image", addPhotoParam.photoUriString, rb)

                galleryAPI.addImage(
                        apiToken,
                        addPhotoParam.description,
                        addPhotoParam.hashTag,
                        addPhotoParam.geoPos.latitude,
                        addPhotoParam.geoPos.longitude,
                        imagePart
                )
            }
            .doOnSuccess { newPhotosSubject.onNext(PhotoFromAddPhotoMapper.map(it)) }
            .onErrorResumeNext {
                if (it is HttpException && it.code() == 400) {
                    val addPhotoError = gson.fromJson(it.response().errorBody()?.charStream(), CompositeError::class.java)
                    val throwable = addPhotoError?.children?.let { AddPhotoErrorMapper.map(it) }
                    throwable?.let { return@onErrorResumeNext Single.error(it) }
                }
                return@onErrorResumeNext Single.error(it)
            }
            .toCompletable()
}