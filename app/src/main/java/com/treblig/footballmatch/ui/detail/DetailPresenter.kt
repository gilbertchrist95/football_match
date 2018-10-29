package com.treblig.footballmatch.ui.detail

import com.treblig.footballmatch.api.TheSportApi
import com.treblig.footballmatch.db.MatchEventDB
import com.treblig.footballmatch.pojo.Event
import com.treblig.footballmatch.ui.base.BasePresenter
import com.treblig.footballmatch.util.ioThread
import com.treblig.footballmatch.util.mainThread
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject

class DetailPresenter(detailView: DetailView) : BasePresenter<DetailView>(detailView) {

    @Inject
    lateinit var theSportApi: TheSportApi

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
        doAsync {
            val isFavorite = MatchEventDB.isExist(view.getContext(), event.idEvent!!)
            uiThread {
                view.setViewIsFavorites(isFavorite)
            }
        }
    }

    fun changeFavorite(event: Event) {
        doAsync {
            val isFavorite = MatchEventDB.isExist(view.getContext(), event.idEvent!!)
            when (isFavorite) {
                true -> MatchEventDB.delete(view.getContext(), event.idEvent)
                false -> MatchEventDB.insert(context = view.getContext(), event = event)
            }

            uiThread {
                view.setViewIsFavorites(!isFavorite, true)
            }
        }
    }
}