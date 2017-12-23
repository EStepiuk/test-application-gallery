package com.yevhen_stepiuk.testgallery.presentation.main.login

import android.app.Dialog
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.squareup.picasso.Picasso
import com.yevhen_stepiuk.testgallery.GalleryApp
import com.yevhen_stepiuk.testgallery.R
import com.yevhen_stepiuk.testgallery.domain.authorization.errors.AuthorizationException
import com.yevhen_stepiuk.testgallery.domain.authorization.errors.EmailError
import com.yevhen_stepiuk.testgallery.domain.authorization.errors.PasswordError
import com.yevhen_stepiuk.testgallery.domain.authorization.errors.UsernameError
import com.yevhen_stepiuk.testgallery.extension.getErrorMessage
import com.yevhen_stepiuk.testgallery.extension.input
import com.yevhen_stepiuk.testgallery.extension.resizePolitly
import com.yevhen_stepiuk.testgallery.presentation.base.BaseActivity
import icepick.State
import kotlinx.android.synthetic.main.login_activity.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.dip
import org.jetbrains.anko.indeterminateProgressDialog
import timber.log.Timber
import javax.inject.Inject


class LoginActivity : BaseActivity<LoginContract.LoginPresenter>(), LoginContract.LoginView, LoginContract.LoginRouter {

    @get:LayoutRes override val layoutRes: Int get() = R.layout.login_activity

    @Inject internal lateinit var picasso: Picasso

    @JvmField
    @State
    internal var isLoginState: Boolean = true
    @JvmField
    @State
    internal var isLoading: Boolean = false
    @JvmField
    @State
    internal var _avatarUriString: String? = null

    private var progressDialog: Dialog? = null

    override var username: String
        get() = etUsername.input
        set(value) {
            etUsername.setText(value)
        }
    override var password: String
        get() = etPassword.input
        set(value) {
            etPassword.setText(value)
        }
    override var email: String
        get() = etEmail.input
        set(value) {
            etEmail.setText(value)
        }
    override var avatarUriString
        get() = _avatarUriString
        set(value) {
            _avatarUriString = value
            onSetAvatarUriString(value)
        }

    override fun setLoading(b: Boolean) {
        isLoading = b

        if (b) {
            if (progressDialog?.isShowing != true) progressDialog = indeterminateProgressDialog(R.string.loading)
        } else {
            progressDialog?.dismiss()
            progressDialog = null
        }
    }

    override fun showError(throwable: Throwable) {
        if (throwable is AuthorizationException) {
            snackbar(root, throwable.message)
            when (throwable) {
                is EmailError -> etEmail.requestFocus()
                is UsernameError -> etEmail.requestFocus()
                is PasswordError -> etPassword.requestFocus()
            }
        } else {
            snackbar(root, getErrorMessage(throwable))
        }
    }

    override fun showInputEmailMessage() {
        snackbar(root, R.string.input_email)
        etEmail.requestFocus()
    }

    override fun showInputPasswordMessage() {
        snackbar(root, R.string.input_password)
        etPassword.requestFocus()
    }

    override fun showInputUsernameMessage() {
        snackbar(root, R.string.input_username)
        etUsername.requestFocus()
    }

    override fun openMainScreen() {
        //TODO: implement
        Timber.d("openMainScreen()")
    }

    override fun openChooseAvatarScreenForResult() {
        val getIntent = Intent(Intent.ACTION_GET_CONTENT).apply { type = "image/*" }
        val pickIntent = Intent(Intent.ACTION_PICK, EXTERNAL_CONTENT_URI).apply { type = "image/*" }
        val chooserIntent = Intent
                .createChooser(getIntent, getString(R.string.select_image))
                .putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

        startActivityForResult(chooserIntent, PICK_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            PICK_PHOTO -> data?.data?.let { presenter.onAvatarPicked(it.toString()) }
        }
    }

    override fun inject() {
        GalleryApp.getComponent(this).inject(this)
    }

    override fun onSetupView(savedInstanceState: Bundle?) {
        super.onSetupView(savedInstanceState)

        btnChangeState.setOnClickListener { setupWithState(!isLoginState) }
        imgAvatar.setOnClickListener { presenter.onAvatarClick() }

        setupWithState(isLoginState)
        setLoading(isLoading)
    }

    override fun onInitializePresenter() {
        super.onInitializePresenter()

        presenter.view = this
        presenter.router = this
    }

    override fun onDestroy() {
        progressDialog?.takeIf { it.isShowing }?.dismiss()
        presenter.view = null
        presenter.router = null

        super.onDestroy()
    }

    private fun setupWithState(isLoginState: Boolean) {
        this.isLoginState = isLoginState

        if (isLoginState) {
            tilUsername.visibility = INVISIBLE
            imgAvatar.visibility = INVISIBLE
            btnEnter.setOnClickListener { presenter.onSignInClick() }
            btnEnter.setText(R.string.sing_in)
            btnChangeState.setText(R.string.dont_have_account)
        } else {
            tilUsername.visibility = VISIBLE
            imgAvatar.visibility = VISIBLE
            onSetAvatarUriString(avatarUriString)
            btnEnter.setOnClickListener { presenter.onSignUpClick() }
            btnEnter.setText(R.string.sign_up)
            btnChangeState.setText(R.string.have_account)
        }
    }

    private fun onSetAvatarUriString(uriString: String?) {
        if (uriString != null) {
            imgAvatar.clearColorFilter()
            picasso.load(uriString)
                    .resizePolitly(dip(128), dip(128))
                    .into(imgAvatar)
        } else {
            imgAvatar.setImageResource(R.drawable.ic_add_a_photo_128dp)
            imgAvatar.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.MULTIPLY)
        }
    }

    companion object {
        const val PICK_PHOTO = 1
    }
}