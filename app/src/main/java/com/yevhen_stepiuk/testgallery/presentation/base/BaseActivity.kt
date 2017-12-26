package com.yevhen_stepiuk.testgallery.presentation.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import com.yevhen_stepiuk.testgallery.data.permission.PermissionManagerImpl
import icepick.Icepick
import javax.inject.Inject


abstract class BaseActivity<P : BaseContract.Presenter<*, *>> : AppCompatActivity() {

    @get:LayoutRes protected abstract val layoutRes: Int

    @Inject protected lateinit var presenter: P
    @Inject internal lateinit var permissionManagerImpl: PermissionManagerImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        inject()
        permissionManagerImpl.bindHelper(this)

        Icepick.restoreInstanceState(this, savedInstanceState)

        onSetupView(savedInstanceState)
        onInitializePresenter()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        if (!isFinishing) Icepick.saveInstanceState(this, outState)

        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        if (isFinishing) presenter.onDispose()
        permissionManagerImpl.unbindHelper(this)

        super.onDestroy()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        permissionManagerImpl.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    protected open fun onSetupView(savedInstanceState: Bundle?) {}

    protected open fun onInitializePresenter() {}

    protected abstract fun inject()
}