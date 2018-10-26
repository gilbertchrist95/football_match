package com.treblig.footballmatch.ui.base

import com.treblig.footballmatch.di.ContextModule
import com.treblig.footballmatch.di.DaggerPresenterInjector
import com.treblig.footballmatch.di.NetworkModule
import com.treblig.footballmatch.di.PresenterInjector
import com.treblig.footballmatch.ui.detail.DetailPresenter
import com.treblig.footballmatch.ui.match_event.MatchPresenter

abstract class BasePresenter<out V : BaseView>(protected val view: V) {

    private val injector: PresenterInjector = DaggerPresenterInjector
            .builder()
            .baseView(view)
            .contextModule(ContextModule)
            .networkModule(NetworkModule)
            .build()

    init {
        inject()
    }

    open fun onViewCreated() {}

    open fun onViewDestroyed() {}

    private fun inject() {
        when (this) {
            is MatchPresenter -> injector.inject(this)
            is DetailPresenter -> injector.inject(this)
        }
    }
}