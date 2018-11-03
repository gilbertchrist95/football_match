package com.treblig.footballmatch.ui.match_event

import com.treblig.footballmatch.api.TheSportApi
import com.treblig.footballmatch.db.MatchEventDB
import com.treblig.footballmatch.pojo.Event
import com.treblig.footballmatch.pojo.League
import com.treblig.footballmatch.pojo.LeagueResponse
import com.treblig.footballmatch.pojo.MatchEventResponse
import com.treblig.footballmatch.util.SchedulerRule
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.lang.Exception
import java.net.UnknownHostException

class MatchPresenterTest {
    @Mock
    lateinit var view: MatchView

    @Mock
    lateinit var theSportApi: TheSportApi

    @Mock
    lateinit var matchEventDB: MatchEventDB

    lateinit var presenter: MatchPresenter

    @get:Rule
    val trampolineSchedulerRule = SchedulerRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view)
        presenter.theSportApi = theSportApi
        presenter.matchEventDB = matchEventDB

    }

    @Test
    fun getFavorites_whenFavoritesIsExist_shouldReturnEvent() {
        val matchEventResponse = whenMatchEventResponse().events!!
        `when`(matchEventDB.getFavorites())
                .thenReturn(Observable.fromCallable<List<Event>> { matchEventResponse })
        presenter.showFavorite()
        Assert.assertEquals(Match.FAVORITE, presenter.matchType)
        verify(view).showLoading()
        verify(view).showEvents(matchEventResponse)
        verify(view).hideLoading()
    }

    @Test
    fun getFavorites_whenFavoritesIsEmpty_shouldReturnNoAvailableData() {
        val matchEventResponse = listOf<Event>()
        `when`(matchEventDB.getFavorites())
                .thenReturn(Observable.fromCallable<List<Event>> { matchEventResponse })
        presenter.showFavorite()
        Assert.assertEquals(Match.FAVORITE, presenter.matchType)
        verify(view).showLoading()
        verify(view).showNoDataAvailable()
        verify(view).hideLoading()
    }

    @Test
    fun getFavorites_whenFavoritesIsError_shouldReturnHideLoading() {
        val expection = Exception("Unknown Error")
        `when`(matchEventDB.getFavorites())
                .thenReturn(Observable.error { expection })
        presenter.showFavorite()
        Assert.assertEquals(Match.FAVORITE, presenter.matchType)
        verify(view).showLoading()
        verify(view).hideLoading()
    }

    @Test
    fun getAllLeagues_whenLeagueResponseIsExist_shouldShowAllLeague() {

        val leagueResponse = whenLeagueResponseIsExist()

        `when`(theSportApi.getAllLeagues())
                .thenReturn(Observable.fromCallable<LeagueResponse> { leagueResponse })

        presenter.getAllLeagues()

        verify(view).showLoading()
        verify(view).showAllLeagues(leagueResponse.leagues)
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
        val leagueId = "1234"
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
        val leagueId = "1234"
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
        val leagueId = "1234"
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
        val leagueId = "1234"
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
        val leagueId = "1234"
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
        val leagueId = "1234"
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
        val event1 = Event(576529, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)
        val event2 = Event(576530, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)
        eventList.add(event1)
        eventList.add(event2)
        return MatchEventResponse(eventList)
    }

    private fun whenLeagueResponseIsExist(): LeagueResponse {
        val leagueList = mutableListOf<League>()
        val league1 = League("1234", "English Premier League")
        val league2 = League("1235", "German Bundesliga")
        leagueList.add(league1)
        leagueList.add(league2)
        return LeagueResponse(leagueList)
    }
}