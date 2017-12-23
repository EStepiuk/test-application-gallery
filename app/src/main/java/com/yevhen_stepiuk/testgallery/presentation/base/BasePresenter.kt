package com.yevhen_stepiuk.testgallery.presentation.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.ReplaySubject
import kotlin.properties.Delegates


abstract class BasePresenter<V : BaseContract.View, R : BaseContract.Router> : BaseContract.Presenter<V, R> {

    override var view by Delegates.observable<V?>(null) { _, _, v -> if (v != null) onViewAttached(v) else onViewDetached() }
    override var router by Delegates.observable<R?>(null) { _, _, r -> if (r != null) onRouterAttached(r) else onRouterDetached() }

    private var viewSubject = ReplaySubject.createWithSize<V>(1)
    private var routerSubject = ReplaySubject.createWithSize<R>(1)

    private val viewMaybe get() = viewSubject.singleElement()
    private val routerMaybe get() = routerSubject.singleElement()
    private val disposables = CompositeDisposable()

    override fun onDispose() {
        disposables.clear()
    }

    protected open fun onViewAttached(view: V) {
        viewSubject.onNext(view)
        viewSubject.onComplete()
    }

    protected open fun onViewDetached() {
        synchronized(viewMaybe) { viewSubject = ReplaySubject.createWithSize<V>(1) }
    }

    protected open fun onRouterAttached(r: R) {
        routerSubject.onNext(r)
        routerSubject.onComplete()
    }

    protected open fun onRouterDetached() {
        synchronized(routerMaybe) { routerSubject = ReplaySubject.createWithSize<R>(1) }
    }

    protected fun onView(actions: V.() -> Unit) {
        disposables.add(viewMaybe.subscribe(actions))
    }

    protected fun onRouter(actions: R.() -> Unit) {
        disposables.add(routerMaybe.subscribe(actions))
    }
}