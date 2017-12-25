package com.yevhen_stepiuk.testgallery.injection.authorized

import com.yevhen_stepiuk.testgallery.presentation.main.main.MainActivity
import dagger.Subcomponent


@AuthorizedScope
@Subcomponent(modules = [AuthorizedDataModule::class, AuthorizedPresentationModule::class])
interface AuthorizedComponent {

    fun inject(mainActivity: MainActivity)

    @Subcomponent.Builder
    interface Builder {

        fun build(): AuthorizedComponent

        fun dataModule(authorizedDataModule: AuthorizedDataModule): Builder

        fun presentationModule(authorizedPresentationModule: AuthorizedPresentationModule): Builder
    }
}