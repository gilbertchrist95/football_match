package com.treblig.footballmatch.ui.match_event

import com.treblig.footballmatch.api.TheSportApi
import com.treblig.footballmatch.pojo.Event
import com.treblig.footballmatch.pojo.League
import com.treblig.footballmatch.pojo.LeagueResponse
import com.treblig.footballmatch.pojo.MatchEventResponse
import com.treblig.footballmatch.util.SchedulerRule
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.net.UnknownHostException

class MatchPresenterTest {
    @Mock
    private lateinit var view: MatchView

    @Mock
    private lateinit var theSportApi: TheSportApi

    private lateinit var presenter: MatchPresenter

    @get:Rule
    val trampolineSchedulerRule = SchedulerRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view)
        presenter.theSportApi = theSportApi
    }

    @Test
    fun getAllLeagues_whenLeagueResponseIsExist_shouldShowAllLeague() {

        val leagueResponse = whenLeagueResponseIsExist()

        `when`(theSportApi.getAllLeagues())
                .thenReturn(Observable.fromCallable<LeagueResponse> { leagueResponse })

        presenter.getAllLeagues()

        verify(view).showLoading()
        verify(view).showAllLeagues(leagueResponse!!.leagues)
        verify(view).hideLoading()
    }

    @Test
    fun getAllLeagues_whenResponseIsError_shouldShowError() {

        val expection = UnknownHostException("No Internet")

        `when`(theSportApi.getAllLeagues())
                .thenReturn(Observable.error { expection })

        presenter.getAllLeagues()

        verify(view).showLoading()
        verify(view).showError(expection)
        verify(view).hideLoading()
    }

    @Test
    fun getPrevLeagueEvent_whenEventResponseIsExist_shouldShowAllLeague() {
        val leagueId = "4328"
        val matchEventResponse = whenMatchEventResponse()
        `when`(theSportApi.getPrevLeagueEvents(leagueId))
                .thenReturn(Observable.fromCallable<MatchEventResponse> { matchEventResponse })

        presenter.getPrevMatch(leagueId)

        Assert.assertEquals(Match.PREV, presenter.matchType)

        verify(view).showLoading()
        verify(view).showEvents(matchEventResponse.events!!)
        verify(view).hideLoading()
    }

    @Test
    fun getPrevLeagueEvent_whenEventResponseIsEmpty_shouldShowNoAvailableData() {
        val leagueId = "4328"
        val eventList = mutableListOf<Event>()
        val matchEventResponse = MatchEventResponse(eventList)
        `when`(theSportApi.getPrevLeagueEvents(leagueId))
                .thenReturn(Observable.fromCallable<MatchEventResponse> { matchEventResponse })

        presenter.getPrevMatch(leagueId)

        Assert.assertEquals(Match.PREV, presenter.matchType)

        verify(view).showLoading()
        verify(view).showNoDataAvailable()
        verify(view).hideLoading()
    }

    @Test
    fun getPrevLeagueEvent_whenResponseIsError_shouldShowError() {
        val leagueId = "4328"
        val expection = UnknownHostException("No Internet")
        `when`(theSportApi.getPrevLeagueEvents(leagueId))
                .thenReturn(Observable.error { expection })

        presenter.getPrevMatch(leagueId)

        Assert.assertEquals(Match.PREV, presenter.matchType)

        verify(view).showLoading()
        verify(view).showError(expection)
        verify(view).hideLoading()
    }

    @Test
    fun getNextLeagueEvent_whenEventResponseIsExist_shouldShowAllLeague() {
        val leagueId = "4328"
        val matchEventResponse = whenMatchEventResponse()
        `when`(theSportApi.getNextLeagueEvents(leagueId))
                .thenReturn(Observable.fromCallable<MatchEventResponse> { matchEventResponse })

        presenter.getNextMatch(leagueId)

        Assert.assertEquals(Match.NEXT, presenter.matchType)

        verify(view).showLoading()
        verify(view).showEvents(matchEventResponse.events!!)
        verify(view).hideLoading()
    }


    @Test
    fun getNextLeagueEvent_whenEventResponseIsEmpty_shouldShowNoAvailableData() {
        val leagueId = "4328"
        val eventList = mutableListOf<Event>()
        val matchEventResponse = MatchEventResponse(eventList)
        `when`(theSportApi.getNextLeagueEvents(leagueId))
                .thenReturn(Observable.fromCallable<MatchEventResponse> { matchEventResponse })

        presenter.getNextMatch(leagueId)

        Assert.assertEquals(Match.NEXT, presenter.matchType)

        verify(view).showLoading()
        verify(view).showNoDataAvailable()
        verify(view).hideLoading()
    }

    @Test
    fun getNextLeagueEvent_whenResponseIsError_shouldShowError() {
        val leagueId = "4328"
        val expection = UnknownHostException("No Internet")
        `when`(theSportApi.getNextLeagueEvents(leagueId))
                .thenReturn(Observable.error { expection })

        presenter.getNextMatch(leagueId)

        Assert.assertEquals(Match.NEXT, presenter.matchType)

        verify(view).showLoading()
        verify(view).showError(expection)
        verify(view).hideLoading()
    }

    private fun whenMatchEventResponse(): MatchEventResponse {
        val eventList = mutableListOf<Event>()
        val event1 = Event(576529, null,null,null,null, null,null,null,null,null,null,null,null,null,null,null,null, null,null,null,null,null,null,null,null,null,null,null,null, null,null,null,null,null,null,null,null,null,null,null,null, null,null,null,null,null,null,null,null,null,null,null)
        val event2 = Event(576530, null,null,null,null, null,null,null,null,null,null,null,null,null,null,null,null, null,null,null,null,null,null,null,null,null,null,null,null, null,null,null,null,null,null,null,null,null,null,null,null, null,null,null,null,null,null,null,null,null,null,null)
        eventList.add(event1)
        eventList.add(event2)
        return MatchEventResponse(eventList)
    }

    private fun whenLeagueResponseIsExist(): LeagueResponse {
        val leagueList = mutableListOf<League>()
        val league1 = League("4328", "English Premier League")
        val league2 = League("4329", "German Bundesliga")
        leagueList.add(league1)
        leagueList.add(league2)
        return LeagueResponse(leagueList)
    }
}