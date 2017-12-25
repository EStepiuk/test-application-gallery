package com.yevhen_stepiuk.testgallery.presentation.main.main

import com.yevhen_stepiuk.testgallery.domain.gallery.GetGifInteractor
import com.yevhen_stepiuk.testgallery.domain.gallery.GetPhotosInteractor
import com.yevhen_stepiuk.testgallery.domain.gallery.entity.Photo
import com.yevhen_stepiuk.testgallery.domain.prefs.Preferences
import com.yevhen_stepiuk.testgallery.presentation.base.BasePresenter


class MainPresenter(private val getPhotosInteractor: GetPhotosInteractor,
                    private val getGifInteractor: GetGifInteractor,
                    private val preferences: Preferences) :
        BasePresenter<MainContract.MainView, MainContract.MainRouter>(), MainContract.MainPresenter {

    init {
        fetchItems()
    }

    override fun onViewAttached(view: MainContract.MainView) {
        super.onViewAttached(view)

        view.setAvatarUriString(preferences.avatarUriString)
    }

    override fun onRetryClick() {
        fetchItems()
    }

    override fun onExitClick() {
        preferences.reset()
        onRouter { openLoginScreen() }
    }

    override fun onAddPhotoClick() {
        onRouter { openAddPhotoScreen() }
    }

    override fun onPlayGifClick() {
        getGifInteractor.execute({ onView { showGifDialog(it.gifUriString) } }, { onView { showGifError(it) } })
    }

    private fun fetchItems() {
        onView {
            setLoading(true)
            getPhotosInteractor.execute(this@MainPresenter::onNewItems, this@MainPresenter::onError)
        }
    }

    private fun onNewItems(items: List<Photo>) {
        onView {
            setLoading(false)
            addPhotoItems(items.map(PhotoVMMapper::map))
        }
    }

    private fun onError(throwable: Throwable) {
        onView {
            setLoading(false)
            showListError(throwable)
        }
    }
}