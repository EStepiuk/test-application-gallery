package com.yevhen_stepiuk.testgallery.injection

import com.yevhen_stepiuk.testgallery.injection.scope.AppScope
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named


@Module
class DomainModule {

    companion object {
        const val JOB = "JOB"
        const val UI = "UI"
    }

    @Provides
    @AppScope
    @Named(JOB)
    internal fun provideJobScheduler(): Scheduler = Schedulers.computation()

    @Provides
    @AppScope
    @Named(UI)
    internal fun provideUIScheduler(): Scheduler = AndroidSchedulers.mainThread()
}