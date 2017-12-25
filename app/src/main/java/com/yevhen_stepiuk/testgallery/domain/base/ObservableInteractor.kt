package com.yevhen_stepiuk.testgallery.domain.base


import com.yevhen_stepiuk.testgallery.common.Action
import com.yevhen_stepiuk.testgallery.common.Consumer
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import timber.log.Timber

abstract class ObservableInteractor<R, in P> : Interactor() {

    fun execute(param: P, onNext: Consumer<in R>, onError: Consumer<in Throwable> = {}, onCompleted: Action = {}) {
        disposables.add(
                buildObservable(param)
                        .compose(applySchedulers())
                        .doOnError { throwable ->
                            Timber.tag(javaClass.simpleName)
                            Timber.d(throwable)
                        }
                        .subscribe(onNext, onError, onCompleted)
        )
    }

    protected abstract fun buildObservable(param: P): Observable<R>

    private fun <T> applySchedulers() = ObservableTransformer<T, T> { it.subscribeOn(jobScheduler).observeOn(uiScheduler) }
}
