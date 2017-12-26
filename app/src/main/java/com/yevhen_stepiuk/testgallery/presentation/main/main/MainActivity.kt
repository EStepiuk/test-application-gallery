package com.yevhen_stepiuk.testgallery.presentation.main.main

import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.design.widget.NavigationView
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.bumptech.glide.Glide
import com.yevhen_stepiuk.testgallery.GalleryApp
import com.yevhen_stepiuk.testgallery.R
import com.yevhen_stepiuk.testgallery.extension.getColorCompat
import com.yevhen_stepiuk.testgallery.extension.getErrorMessage
import com.yevhen_stepiuk.testgallery.extension.headerView
import com.yevhen_stepiuk.testgallery.presentation.base.BaseActivity
import com.yevhen_stepiuk.testgallery.presentation.main.add_photo.AddPhotoActivity
import com.yevhen_stepiuk.testgallery.presentation.main.login.LoginActivity
import icepick.State
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_content.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import org.jetbrains.anko.Underline
import org.jetbrains.anko.append
import org.jetbrains.anko.buildSpanned
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.foregroundColor
import javax.inject.Inject

class MainActivity : BaseActivity<MainContract.MainPresenter>(), NavigationView.OnNavigationItemSelectedListener,
        MainContract.MainView, MainContract.MainRouter {

    @get:LayoutRes override val layoutRes: Int get() = R.layout.main_activity

    @Inject internal lateinit var photosAdapter: PhotosAdapter

    @JvmField
    @State
    internal var isLoading = false
    @JvmField
    @State
    internal var isListError = false

    override fun setAvatarUriString(avatarUriString: String?) {
        if (avatarUriString != null)
            Glide.with(this).load(avatarUriString).into(navigationView.headerView.imgAvatar)
    }

    override fun setLoading(b: Boolean) {
        isLoading = b
        if (b) {
            progressBar.visibility = VISIBLE
            tvListError.visibility = INVISIBLE
        } else {
            progressBar.visibility = INVISIBLE
            tvListError.visibility = if (isListError) VISIBLE else INVISIBLE
        }
    }

    override fun showListError(throwable: Throwable) {
        isListError = true
        setLoading(false)

        longSnackbar(coordinator, getErrorMessage(throwable), getString(R.string.retry)) { presenter.onRetryClick() }
    }

    override fun hideListError() {
        isListError = false
        setLoading(isLoading)
    }

    override fun addPhotoItems(items: List<PhotoVM>) {
        hideListError()
        photosAdapter.addItems(items)
    }

    override fun showGifDialog(gifUriString: String) {
        GifDialog.newInstance(gifUriString).show(supportFragmentManager, GifDialog::class.java.simpleName)
    }

    override fun showGifError(throwable: Throwable) {
        snackbar(coordinator, getErrorMessage(throwable))
    }

    override fun openAddPhotoScreen() {
        startActivity(Intent(this, AddPhotoActivity::class.java))
    }

    override fun openLoginScreen() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onSetupView(savedInstanceState: Bundle?) {
        super.onSetupView(savedInstanceState)

        photosAdapter.restoreInstanceState(savedInstanceState)

        setupActionBar()
        setupListErrorView()
        setupNavigationView()
        setupRecyclerView()
        fabAdd.setOnClickListener { presenter.onAddPhotoClick() }

        setLoading(isLoading)
    }

    override fun onInitializePresenter() {
        super.onInitializePresenter()

        presenter.view = this
        presenter.router = this
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_action_bar_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.toolbar_play_gif -> presenter.onPlayGifClick()
            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }

    override fun onDestroy() {
        presenter.view = null
        presenter.router = null

        super.onDestroy()
    }

    override fun inject() {
        GalleryApp.getAuthorizedComponent(this).inject(this)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        photosAdapter.saveInstanceState(outState)

        super.onSaveInstanceState(outState)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_exit -> presenter.onExitClick()
            else -> return false
        }
        drawerLayout.closeDrawers()
        return true
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.gallery)
    }

    private fun setupListErrorView() {
        tvListError.text = buildSpanned {
            append(getString(R.string.something_went_wrong))
            append(". ")
            append("${getString(R.string.retry)}?", Underline, foregroundColor(getColorCompat(R.color.colorAccent)))
        }
        tvListError.setOnClickListener { presenter.onRetryClick() }
    }

    private fun setupNavigationView() {
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)
    }

    private fun setupRecyclerView() {
        recylerView.layoutManager = GridLayoutManager(this, 2)
        recylerView.adapter = photosAdapter
    }

}
