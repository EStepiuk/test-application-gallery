package com.yevhen_stepiuk.testgallery.presentation.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import icepick.Icepick
import javax.inject.Inject


abstract class BaseActivity<P : BaseContract.Presenter<*, *>> : AppCompatActivity() {

    @get:LayoutRes protected abstract val layoutRes: Int

    @Inject protected lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        inject()
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

        super.onDestroy()
    }

    protected open fun onSetupView(savedInstanceState: Bundle?) {}

    protected open fun onInitializePresenter() {}

    protected abstract fun inject()
}