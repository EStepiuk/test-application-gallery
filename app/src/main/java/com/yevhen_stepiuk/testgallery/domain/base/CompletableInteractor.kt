package com.yevhen_stepiuk.testgallery.domain.base

import com.yevhen_stepiuk.testgallery.common.Action
import com.yevhen_stepiuk.testgallery.common.Consumer
import io.reactivex.Completable
import io.reactivex.CompletableTransformer

abstract class CompletableInteractor<in P> : Interactor() {

    fun execute(param: P, onComplete: Action, onError: Consumer<in Throwable> = {}) {
        disposables.add(
                buildCompletable(param)
                        .compose(applySchedulers())
                        .subscribe(onComplete, onError)
        )
    }

    protected abstract fun buildCompletable(param: P): Completable

    private fun applySchedulers() = CompletableTransformer { it.subscribeOn(jobScheduler).observeOn(uiScheduler) }
}
