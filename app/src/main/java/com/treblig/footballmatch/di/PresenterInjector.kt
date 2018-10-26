package com.treblig.footballmatch.di

import com.treblig.footballmatch.ui.base.BaseView
import com.treblig.footballmatch.ui.detail.DetailPresenter
import com.treblig.footballmatch.ui.match_event.MatchPresenter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ContextModule::class), (NetworkModule::class)])
interface PresenterInjector {

    fun inject(matchPresenter: MatchPresenter)

    fun inject(detailPresenter: DetailPresenter)

    @Component.Builder
    interface Builder {
        fun build(): PresenterInjector
        fun networkModule(networkModule: NetworkModule): Builder
        fun contextModule(contextModule: ContextModule): Builder

        @BindsInstance
        fun baseView(baseView: BaseView): Builder
    }
}






