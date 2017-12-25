package com.uavalley.demochat.domain.base


import com.yevhen_stepiuk.testgallery.common.Consumer
import com.yevhen_stepiuk.testgallery.domain.base.Interactor
import io.reactivex.Single
import io.reactivex.SingleTransformer
import timber.log.Timber

abstract class SingleInteractor<R, in P> : Interactor() {

    fun execute(param: P, onSuccess: Consumer<in R>, onError: Consumer<in Throwable> = {}) {
        disposables.add(
                buildSingle(param)
                        .compose(applySchedulers())
                        .doOnError { throwable ->
                            Timber.tag(javaClass.simpleName)
                            Timber.d(throwable)
                        }
                        .subscribe(onSuccess, onError)
        )
    }

    protected abstract fun buildSingle(param: P): Single<R>

    private fun <T> applySchedulers() = SingleTransformer<T, T> { it.subscribeOn(jobScheduler).observeOn(uiScheduler) }
}
