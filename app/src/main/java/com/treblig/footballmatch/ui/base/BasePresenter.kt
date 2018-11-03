package com.treblig.footballmatch.ui.base

import com.treblig.footballmatch.FootballApplication
import com.treblig.footballmatch.di.*
import com.treblig.footballmatch.ui.detail.DetailPresenter
import com.treblig.footballmatch.ui.match_event.MatchPresenter

abstract class BasePresenter<out V : BaseView>(protected val view: V) {

//    init {
//        inject()
//    }

    open fun onViewCreated() {}

    open fun onViewDestroyed() {}

    fun inject() {
        val component  = FootballApplication.applicationComponent()
        when (this) {
            is MatchPresenter -> component.inject(this)
            is DetailPresenter -> component.inject(this)
        }
    }
}