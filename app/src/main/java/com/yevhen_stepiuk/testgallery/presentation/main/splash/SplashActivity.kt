package com.yevhen_stepiuk.testgallery.presentation.main.splash

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yevhen_stepiuk.testgallery.GalleryApp
import com.yevhen_stepiuk.testgallery.domain.prefs.Preferences
import com.yevhen_stepiuk.testgallery.presentation.main.login.LoginActivity
import javax.inject.Inject


class SplashActivity : AppCompatActivity() {

    @Inject internal lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()

        if (preferences.isLoggedIn) {
            //TODO: start MainActivity
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()
    }

    private fun inject() {
        GalleryApp.getComponent(this).inject(this)
    }
}