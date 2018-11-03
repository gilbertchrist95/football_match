package com.treblig.footballmatch.ui.detail

import com.treblig.footballmatch.api.TheSportApi
import com.treblig.footballmatch.db.MatchEventDB
import com.treblig.footballmatch.pojo.Event
import com.treblig.footballmatch.ui.base.BasePresenter
import com.treblig.footballmatch.util.CoroutineContextProvider
import com.treblig.footballmatch.util.ioThread
import com.treblig.footballmatch.util.mainThread
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import javax.inject.Inject

class DetailPresenter(detailView: DetailView, private val context: CoroutineContextProvider = CoroutineContextProvider()) : BasePresenter<DetailView>(detailView) {

    @Inject
    lateinit var theSportApi: TheSportApi

    @Inject
    lateinit var matchEventDB: MatchEventDB

    fun getTeamDetail(teamId: String) {
        view.showLoading()

        theSportApi.lookUpTeam(teamId)
                .subscribeOn(ioThread())
                .observeOn(mainThread())
                .subscribe({ response ->
                    response?.let {
                        if (it.teams != null && it.teams.isNotEmpty()) {
                            val team = it.teams[0]
                            view.showTeamDetail(team)
                        } else {
                            view.hideLoading()
                        }
                    }
                }, {
                    view.hideLoading()
                    view.showError(it)
                }, {
                    view.hideLoading()
                })
    }

    fun refreshFavorite(event: Event) {
        async(context = context.main){
            val isFavorite = bg {
                matchEventDB.isExist(event.idEvent!!)
            }
            view.setViewIsFavorites(isFavorite.await())
        }
    }

    fun changeFavorite(event: Event) {
        async(context.main) {
            val isFavoriteData: Deferred<Boolean> = bg {
                matchEventDB.isExist(event.idEvent!!)
            }
            view.setViewIsFavorites(!isFavoriteData.await(), true)
            when (isFavoriteData.await()) {
                true -> bg {
                    matchEventDB.delete(event.idEvent!!)
                }
                false -> bg {
                    matchEventDB.insert(event = event)
                }
            }
        }
    }
}