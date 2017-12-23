package com.yevhen_stepiuk.testgallery.domain.base


import com.yevhen_stepiuk.testgallery.injection.app.DomainModule
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named

abstract class Interactor {

    internal lateinit var jobScheduler: Scheduler
    internal lateinit var uiScheduler: Scheduler

    protected val disposables = CompositeDisposable()

    fun dispose() {
        disposables.clear()
    }

    @Inject
    internal fun injectJobScheduler(@Named(DomainModule.JOB) jobScheduler: Scheduler) {
        this.jobScheduler = jobScheduler
    }

    @Inject
    internal fun injectUiScheduler(@Named(DomainModule.UI) uiScheduler: Scheduler) {
        this.uiScheduler = uiScheduler
    }
}
