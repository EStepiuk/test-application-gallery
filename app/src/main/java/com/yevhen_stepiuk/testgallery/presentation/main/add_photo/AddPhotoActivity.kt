package com.yevhen_stepiuk.testgallery.presentation.main.add_photo

import android.app.Dialog
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.provider.MediaStore
import android.support.annotation.LayoutRes
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.yevhen_stepiuk.testgallery.GalleryApp
import com.yevhen_stepiuk.testgallery.R
import com.yevhen_stepiuk.testgallery.domain.gallery.errors.AddPhotoError
import com.yevhen_stepiuk.testgallery.domain.location.errors.LocationDisabledException
import com.yevhen_stepiuk.testgallery.domain.permissions.errors.LocationPermissionDeniedException
import com.yevhen_stepiuk.testgallery.extension.getColorCompat
import com.yevhen_stepiuk.testgallery.extension.getErrorMessage
import com.yevhen_stepiuk.testgallery.extension.input
import com.yevhen_stepiuk.testgallery.presentation.base.BaseActivity
import icepick.State
import kotlinx.android.synthetic.main.add_photo_activity.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.indeterminateProgressDialog


class AddPhotoActivity : BaseActivity<AddPhotoContract.AddPhotoPresenter>(),
        AddPhotoContract.AddPhotoView, AddPhotoContract.AddPhotoRouter {

    @get:LayoutRes override val layoutRes: Int get() = R.layout.add_photo_activity

    @JvmField
    @State
    internal var photoUriString: String? = null
    @JvmField
    @State
    internal var isLoading = false

    private var progressDialog: Dialog? = null

    override var description: String
        get() = etDescription.input
        set(value) {
            etDescription.setText(value)
        }
    override var hashtag: String
        get() = etHashtag.input
        set(value) {
            etHashtag.setText(value)
        }

    override fun setLoading(b: Boolean) {
        isLoading = b

        if (b) {
            if (progressDialog?.isShowing != true) progressDialog = indeterminateProgressDialog(R.string.loading) {
                setOnCancelListener { presenter.onCancelUploadClick() }
            }
        } else {
            progressDialog?.dismiss()
            progressDialog = null
        }
    }

    override fun showPhoto(photoUriString: String) {
        this.photoUriString = photoUriString
        imgPhoto.clearColorFilter()
        imgPhoto.scaleType = ImageView.ScaleType.CENTER_CROP
        Glide.with(this).load(photoUriString).into(imgPhoto)
    }

    override fun showError(throwable: Throwable) {
        val message = when (throwable) {
            is LocationPermissionDeniedException -> getString(R.string.allow_location)
            is LocationDisabledException -> getString(R.string.enable_location)
            is AddPhotoError -> {
                when (throwable.errorField) {
                    AddPhotoError.ErrorField.DESCRIPTION -> etDescription.requestFocus()
                    AddPhotoError.ErrorField.HASHTAG -> etHashtag.requestFocus()
                }
                throwable.message
            }
            else -> getErrorMessage(throwable)
        }
        snackbar(root, message)
    }

    override fun showChoosePhotoMessage() {
        snackbar(root, R.string.choose_photo_to_upload)
    }

    override fun showInputDescriptionMessage() {
        snackbar(root, R.string.add_description)
        etDescription.requestFocus()
    }

    override fun openMainScreen() {
        finish()
    }

    override fun openChoosePhotoScreenForResult() {
        val getIntent = Intent(Intent.ACTION_GET_CONTENT).apply { type = "image/*" }
        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply { type = "image/*" }
        val chooserIntent = Intent
                .createChooser(getIntent, getString(R.string.select_image))
                .putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

        startActivityForResult(chooserIntent, PICK_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            PICK_PHOTO -> data?.data?.let { presenter.onPhotoSelected(it.toString()) }
        }
    }

    override fun onSetupView(savedInstanceState: Bundle?) {
        super.onSetupView(savedInstanceState)

        imgPhoto.setOnClickListener { presenter.onPhotoClick() }
        setupActionBar()

        photoUriString?.let(this::showPhoto) ?: imgPhoto.setColorFilter(getColorCompat(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP)
        setLoading(isLoading)
    }

    override fun onInitializePresenter() {
        super.onInitializePresenter()

        presenter.view = this
        presenter.router = this
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.done_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.menu_done -> presenter.onUploadClick()
            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }

    override fun onDestroy() {
        progressDialog?.dismiss()

        presenter.view = null
        presenter.router = null

        super.onDestroy()
    }

    override fun inject() {
        GalleryApp.getAuthorizedComponent(this).inject(this)
    }

    private fun setupActionBar() {
        supportActionBar?.run {
            setTitle(R.string.add_photo)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    companion object {
        private const val PICK_PHOTO = 1
    }
}