package com.yevhen_stepiuk.testgallery.domain.base

import com.yevhen_stepiuk.testgallery.common.Consumer
import io.reactivex.Observable


abstract class NoParamObservableInteractor<R> : ObservableInteractor<R, Void?>() {

    fun execute(onNext: Consumer<in R>, onError: Consumer<in Throwable> = {}) {
        execute(null, onNext, onError)
    }

    override final fun buildObservable(param: Void?) = buildObservable()

    protected abstract fun buildObservable(): Observable<R>
}