package com.treblig.footballmatch.ui.detail

import com.treblig.footballmatch.api.TheSportApi
import com.treblig.footballmatch.db.MatchEventDB
import com.treblig.footballmatch.pojo.Event
import com.treblig.footballmatch.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject

class DetailPresenter(detailView: DetailView) : BasePresenter<DetailView>(detailView) {

    @Inject
    lateinit var theSportApi: TheSportApi

    fun getTeamDetail(homeId: String, awayId: String) {
        view.showLoading()

        theSportApi.lookUpTeam(homeId)
                .mergeWith(theSportApi.lookUpTeam(awayId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    response?.let {
                        if (it.teams != null) {
                            val team = it.teams[0]
                            when (team.idTeam) {
                                homeId -> view.showHomeTeamDetail(team)
                                else -> view.showAwayTeamDetail(team)
                            }
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