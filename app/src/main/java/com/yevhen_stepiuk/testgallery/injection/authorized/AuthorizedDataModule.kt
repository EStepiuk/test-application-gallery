package com.yevhen_stepiuk.testgallery.injection.authorized

import com.yevhen_stepiuk.testgallery.data.api.GalleryAPI
import com.yevhen_stepiuk.testgallery.data.gallery.GalleryRepositoryImpl
import com.yevhen_stepiuk.testgallery.data.preferences.SharedPrefsWrapper
import com.yevhen_stepiuk.testgallery.domain.gallery.GalleryRepository
import dagger.Module
import dagger.Provides


@Module
class AuthorizedDataModule {

    @Provides
    @AuthorizedScope
    internal fun provideGalleryRepository(galleryAPI: GalleryAPI,
                                          sharedPrefsWrapper: SharedPrefsWrapper): GalleryRepository =
            GalleryRepositoryImpl(
                    galleryAPI,
                    sharedPrefsWrapper.apiToken ?: throw IllegalStateException("Api token must be initialized, at this moment"))
}