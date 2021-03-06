package com.yevhen_stepiuk.testgallery.injection.authorized

import com.yevhen_stepiuk.testgallery.domain.gallery.AddPhotoInteractor
import com.yevhen_stepiuk.testgallery.domain.gallery.GetGifInteractor
import com.yevhen_stepiuk.testgallery.domain.gallery.GetPhotosInteractor
import com.yevhen_stepiuk.testgallery.domain.image.ExifRetriever
import com.yevhen_stepiuk.testgallery.domain.location.GetLocationInteractor
import com.yevhen_stepiuk.testgallery.domain.prefs.Preferences
import com.yevhen_stepiuk.testgallery.presentation.main.add_photo.AddPhotoContract
import com.yevhen_stepiuk.testgallery.presentation.main.add_photo.AddPhotoPresenter
import com.yevhen_stepiuk.testgallery.presentation.main.main.MainContract
import com.yevhen_stepiuk.testgallery.presentation.main.main.MainPresenter
import dagger.Module
import dagger.Provides


@Module
class AuthorizedPresentationModule {

    @Provides
    @AuthorizedScope
    internal fun provideMainPresenter(getPhotosInteractor: GetPhotosInteractor,
                                      getGifInteractor: GetGifInteractor,
                                      preferences: Preferences): MainContract.MainPresenter =
            MainPresenter(getPhotosInteractor, getGifInteractor, preferences)

    @Provides
    @AuthorizedScope
    internal fun provideAddPhotoPresenter(exifRetrieverFactory: ExifRetriever.Factory,
                                          addPhotoInteractor: AddPhotoInteractor,
                                          getLocationInteractor: GetLocationInteractor): AddPhotoContract.AddPhotoPresenter =
            AddPhotoPresenter(exifRetrieverFactory, addPhotoInteractor, getLocationInteractor)
}