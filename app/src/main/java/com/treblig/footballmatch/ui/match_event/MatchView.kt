package com.treblig.footballmatch.ui.match_event

import com.treblig.footballmatch.pojo.League
import com.treblig.footballmatch.pojo.Event
import com.treblig.footballmatch.ui.base.BaseView

interface MatchView: BaseView{
    fun showAllLeagues(leagueList: List<League>?)
    fun showEvents(eventList: List<Event>)
    fun showNoDataAvailable()
    fun getSelectedLeague(): League
}






