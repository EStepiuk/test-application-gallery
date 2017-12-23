package com.yevhen_stepiuk.testgallery.presentation.base


object BaseContract {

    interface View
    interface Router
    interface Presenter<V : View, R : Router> {

        var view: V?
        var router: R?

        fun onDispose() {}
    }
}