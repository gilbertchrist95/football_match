package com.treblig.footballmatch.ui.detail

import com.treblig.footballmatch.api.TheSportApi
import com.treblig.footballmatch.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
                },{
                    view.hideLoading()
                })
    }

}





