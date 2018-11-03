package com.treblig.footballmatch.di

import com.treblig.footballmatch.FootballApplication
import com.treblig.footballmatch.ui.detail.DetailPresenter
import com.treblig.footballmatch.ui.match_event.MatchPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class, DatabaseModule::class])
interface ApplicationComponent {
    fun inject(matchPresenter: MatchPresenter)
    fun inject(detailPresenter: DetailPresenter)
}






