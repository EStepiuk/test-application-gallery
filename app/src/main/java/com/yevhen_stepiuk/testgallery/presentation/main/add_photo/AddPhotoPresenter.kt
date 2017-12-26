package com.yevhen_stepiuk.testgallery.presentation.main.add_photo

import com.yevhen_stepiuk.testgallery.domain.gallery.AddPhotoInteractor
import com.yevhen_stepiuk.testgallery.domain.gallery.entity.AddPhotoParam
import com.yevhen_stepiuk.testgallery.domain.image.ExifRetriever
import com.yevhen_stepiuk.testgallery.domain.location.GetLocationInteractor
import com.yevhen_stepiuk.testgallery.domain.location.entity.GeoPos
import com.yevhen_stepiuk.testgallery.presentation.base.BasePresenter


class AddPhotoPresenter(private val exifRetrieverFactory: ExifRetriever.Factory,
                        private val addPhotoInteractor: AddPhotoInteractor,
                        private val getLocationInteractor: GetLocationInteractor) :
        AddPhotoContract.AddPhotoPresenter, BasePresenter<AddPhotoContract.AddPhotoView, AddPhotoContract.AddPhotoRouter>() {

    private var photoUriString: String? = null
    private var geoPos: GeoPos? = null

    override fun onPhotoClick() {
        onRouter { openChoosePhotoScreenForResult() }
    }

    override fun onUploadClick() {
        val photoUriString = this.photoUriString
        val geoPos = this.geoPos
        onView {
            when {
                photoUriString == null -> showChoosePhotoMessage()
                description.isEmpty() -> showInputDescriptionMessage()
                geoPos == null -> {
                    setLoading()
                    fetchLocationAndUploadPhoto(photoUriString, description, hashtag)
                }
                else -> {
                    setLoading()
                    uploadPhoto(geoPos, photoUriString, description, hashtag)
                }
            }
        }
    }

    override fun onCancelUploadClick() {
        getLocationInteractor.dispose()
        addPhotoInteractor.dispose()
        onView { setLoading(false) }
    }

    override fun onPhotoSelected(photoUriString: String) {
        this.photoUriString = photoUriString
        exifRetrieverFactory.from(photoUriString).geoPos?.let { geoPos = it }
        onView { showPhoto(photoUriString) }
    }

    override fun onDispose() {
        photoUriString = null
        geoPos = null

        addPhotoInteractor.dispose()
        getLocationInteractor.dispose()

        super<AddPhotoContract.AddPhotoPresenter>.onDispose()
        super<BasePresenter>.onDispose()
    }

    private fun fetchLocationAndUploadPhoto(photoUriString: String, description: String, hashtag: String) {
        getLocationInteractor.execute(
                {
                    getLocationInteractor.dispose()
                    geoPos = it
                    uploadPhoto(it, photoUriString, description, hashtag)
                },
                {
                    onView {
                        setLoading(false)
                        showError(it)
                    }
                }
        )
    }

    private fun uploadPhoto(geoPos: GeoPos, photoUriString: String, description: String, hashtag: String) {
        addPhotoInteractor.execute(
                AddPhotoParam(photoUriString, description, hashtag, geoPos),
                {
                    onView { setLoading(false) }
                    onRouter { openMainScreen() }
                },
                {
                    onView {
                        setLoading(false)
                        showError(it)
                    }
                }
        )
    }
}