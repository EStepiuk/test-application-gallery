package com.yevhen_stepiuk.testgallery

import android.app.Application
import android.content.Context
import com.yevhen_stepiuk.testgallery.data.preferences.SharedPrefsWrapper
import com.yevhen_stepiuk.testgallery.injection.app.*
import com.yevhen_stepiuk.testgallery.injection.authorized.AuthorizedComponent
import com.yevhen_stepiuk.testgallery.injection.authorized.AuthorizedDataModule
import com.yevhen_stepiuk.testgallery.injection.authorized.AuthorizedPresentationModule
import com.yevhen_stepiuk.testgallery.injection.login.LoginComponent
import com.yevhen_stepiuk.testgallery.injection.login.LoginDataModule
import com.yevhen_stepiuk.testgallery.injection.login.LoginPresentationModule
import timber.log.Timber
import javax.inject.Inject


class GalleryApp : Application() {

    companion object {

        fun getComponent(context: Context) = (context.applicationContext as GalleryApp).component

        fun getLoginComponent(context: Context) = (context.applicationContext as GalleryApp).loginComponent

        fun getAuthorizedComponent(context: Context) = (context.applicationContext as GalleryApp).authorizedComponent
    }

    private lateinit var component: AppComponent

    private var _loginComponent: LoginComponent? = null
    private val loginComponent: LoginComponent get() = _loginComponent ?: createLoginComponent()

    private var _authorizedComponent: AuthorizedComponent? = null
    private val authorizedComponent: AuthorizedComponent get() = _authorizedComponent ?: createAuthorizedComponent()

    @Inject internal lateinit var prefsWrapper: SharedPrefsWrapper

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .domainModule(DomainModule())
                .netModule(NetModule())
                .utilModule(UtilModule())
                .dataModule(DataModule())
                .build()

        component.inject(this)
        prefsWrapper.resetListener = this::onPreferencesReset
    }

    private fun onPreferencesReset() {
        synchronized(this) { _authorizedComponent = null }
    }

    private fun createLoginComponent(): LoginComponent {
        synchronized(this) {
            val res = component.loginSubcomponentBuilder()
                    .dataModule(LoginDataModule())
                    .presentationModule(LoginPresentationModule())
                    .build()
            _loginComponent = res
            return res
        }
    }

    private fun createAuthorizedComponent(): AuthorizedComponent {
        synchronized(this) {
            val res = component.authorizedSubcomponentBuilder()
                    .dataModule(AuthorizedDataModule())
                    .presentationModule(AuthorizedPresentationModule())
                    .build()
            _authorizedComponent = res
            return res
        }
    }
}