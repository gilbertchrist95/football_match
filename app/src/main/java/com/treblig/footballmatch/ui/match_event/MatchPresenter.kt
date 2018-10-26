package com.treblig.footballmatch.ui.match_event

import com.treblig.footballmatch.api.TheSportApi
import com.treblig.footballmatch.pojo.MatchEventResponse
import com.treblig.footballmatch.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MatchPresenter(matchView: MatchView) : BasePresenter<MatchView>(matchView) {
    @Inject
    lateinit var theSportApi: TheSportApi

    private var allLeagues: Disposable? = null
    private var prevMatch: Disposable? = null
    private var nextMatch: Disposable? = null

    var matchType = Match.PREV

    override fun onViewCreated() {
        super.onViewCreated()
        getAllLeagues()
    }

    private fun getAllLeagues() {
        view.showLoading()

        allLeagues = theSportApi.getAllLeagues()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    view.hideLoading()
                    view.showAllLeagues(it.leagues)
                    val league = view.getSelectedLeague()

                    when (matchType) {
                        Match.PREV -> getPrevMatch(league.id)
                        Match.NEXT -> getNextMatch(league.id)
                    }

                }, {
                    view.hideLoading()
                    view.showError(it)
                })
    }

    internal fun getPrevMatch(id: String) {
        matchType = Match.PREV
        view.showLoading()
        prevMatch = theSportApi.getPrevLeagueEvents(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    processEventResponse(it)
                    view.hideLoading()
                },{
                    view.hideLoading()
                    view.showError(it)
                })

    }

    private fun processEventResponse(response: MatchEventResponse?) {
        when {
            response != null -> when {
                response.events != null && response.events.isNotEmpty() -> view.showEvents(response.events)
                else -> view.showNoDataAvailable()
            }
            else -> view.showNoDataAvailable()
        }
        view.hideLoading()
    }

    internal fun getNextMatch(id: String) {
        matchType = Match.NEXT
        view.showLoading()
        prevMatch = theSportApi.getNextLeagueEvents(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    processEventResponse(it)
                    view.hideLoading()
                },{
                    view.hideLoading()
                    view.showError(it)
                })
    }

    override fun onViewDestroyed() {
        super.onViewDestroyed()
        allLeagues?.dispose()
        prevMatch?.dispose()
        nextMatch?.dispose()
    }
}






