package com.uavalley.demochat.domain.base

import com.yevhen_stepiuk.testgallery.common.Consumer
import io.reactivex.Single


abstract class NoParamSingleInteractor<R> : SingleInteractor<R, Void?>() {

    fun execute(onNext: Consumer<in R>, onError: Consumer<in Throwable> = {}) {
        execute(null, onNext, onError)
    }

    override final fun buildSingle(param: Void?) = buildSingle()

    protected abstract fun buildSingle(): Single<R>
}