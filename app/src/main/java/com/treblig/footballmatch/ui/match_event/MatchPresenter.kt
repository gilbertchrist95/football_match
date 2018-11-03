package com.treblig.footballmatch.ui.match_event

import com.treblig.footballmatch.api.TheSportApi
import com.treblig.footballmatch.db.MatchEventDB
import com.treblig.footballmatch.pojo.MatchEventResponse
import com.treblig.footballmatch.ui.base.BasePresenter
import com.treblig.footballmatch.util.ioThread
import com.treblig.footballmatch.util.mainThread
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MatchPresenter(matchView: MatchView) : BasePresenter<MatchView>(matchView) {
    @Inject
    lateinit var theSportApi: TheSportApi

    @Inject
    lateinit var matchEventDB: MatchEventDB

    private var allLeagues: Disposable? = null
    private var prevMatch: Disposable? = null
    private var nextMatch: Disposable? = null
    private var favorite: Disposable? = null

    var matchType = Match.PREV


    override fun onViewCreated() {
        super.onViewCreated()
        getAllLeagues()
    }

    fun getAllLeagues() {
        view.showLoading()

        allLeagues = theSportApi.getAllLeagues()
                .subscribeOn(ioThread())
                .observeOn(mainThread())
                .subscribe({
                    view.hideLoading()
                    view.showAllLeagues(it.leagues)
                }, {
                    view.hideLoading()
                    view.showError(it)
                })
    }

    internal fun getPrevMatch(id: String) {
        matchType = Match.PREV
        view.showLoading()
        prevMatch = theSportApi.getPrevLeagueEvents(id)
                .subscribeOn(ioThread())
                .observeOn(mainThread())
                .subscribe({
                    processEventResponse(it)
                }, {
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
                .subscribeOn(ioThread())
                .observeOn(mainThread())
                .subscribe({
                    processEventResponse(it)
                }, {
                    view.hideLoading()
                    view.showError(it)
                })
    }

    fun showFavorite() {
        matchType = Match.FAVORITE
        view.showLoading()
        favorite = matchEventDB.getFavorites()
                .subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline())
                .subscribe({ data ->
                    when (data != null && data.isNotEmpty()) {
                        true -> view.showEvents(data!!)
                        false -> view.showNoDataAvailable()
                    }
                    view.hideLoading()
                }, {
                    view.hideLoading()
                })

    }

    override fun onViewDestroyed() {
        super.onViewDestroyed()
        allLeagues?.dispose()
        prevMatch?.dispose()
        nextMatch?.dispose()
        favorite?.dispose()
    }

}






