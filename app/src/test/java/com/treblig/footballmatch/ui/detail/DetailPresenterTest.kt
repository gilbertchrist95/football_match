package com.treblig.footballmatch.ui.detail

import com.treblig.footballmatch.api.TheSportApi
import com.treblig.footballmatch.db.MatchEventDB
import com.treblig.footballmatch.pojo.Event
import com.treblig.footballmatch.pojo.Team
import com.treblig.footballmatch.pojo.TeamResponse
import com.treblig.footballmatch.util.SchedulerRule
import com.treblig.footballmatch.util.TestContextProvider
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.net.UnknownHostException

class DetailPresenterTest {

    @Mock
    private lateinit var view: DetailView

    @Mock
    private lateinit var theSportApi: TheSportApi

    @Mock
    lateinit var matchEventDB: MatchEventDB

    private lateinit var presenter: DetailPresenter

    @get:Rule
    val trampolineSchedulerRule = SchedulerRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailPresenter(view, TestContextProvider())
        presenter.theSportApi = theSportApi
        presenter.matchEventDB = matchEventDB
    }

    /**
     * when team detail is exist
     * return view.showLoading(), view.showTeamDetail(), view.hideLoading()
     */
    @Test
    fun getTeamDetails_whenTeamResponseExist_shouldShowTeamDetail() {
        val teamId = "1234"
        val teamResponse = whenTeamResponseExist()

        `when`(theSportApi.lookUpTeam(teamId))
                .thenReturn(Observable.fromCallable<TeamResponse> { teamResponse })

        presenter.getTeamDetail(teamId)

        verify(view).showLoading()
        verify(view).showTeamDetail(teamResponse.teams!![0])
        verify(view).hideLoading()
    }

    /**
     * when team detail is empty
     * return view.showLoading(), view.hideLoading()
     */
    @Test
    fun getTeamDetails_whenTeamResponseIsEmpty_shouldOnlyShowAndHideLoading() {
        val teamId = "1234"
        val teamResponse = whenTeamResponseIsEmpty()

        `when`(theSportApi.lookUpTeam(teamId))
                .thenReturn(Observable.fromCallable<TeamResponse> { teamResponse })

        presenter.getTeamDetail(teamId)

        verify(view).showLoading()
        verify(view).hideLoading()
    }

    /**
     * when internet no exist
     * return view.showLoading(), view.showError(), view.hideLoading()
     */
    @Test
    fun getTeamDetails_whenNoInternetConnection_shouldShowError() {
        val teamId = "1234"
        val exception = UnknownHostException("No Internet connection")

        `when`(theSportApi.lookUpTeam(teamId))
                .thenReturn(Observable.error() { exception })

        presenter.getTeamDetail(teamId)

        verify(view).showLoading()
        verify(view).showError(exception)
        verify(view).hideLoading()
    }

    @Test
    fun changeFavorite_whenRecordIsNotExist_shouldViewIsFavoriteWithParamIsFavoriteTrue() {
        `when`(matchEventDB.isExist("576529")).thenReturn(false)
        presenter.changeFavorite(Event(576529, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "576529", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null))
        verify(view).setViewIsFavorites(true, true)
    }

    @Test
    fun changeFavorite_whenRecordIsExist_shouldViewIsFavoriteWithParamIsFavoriteFalse() {
        `when`(matchEventDB.isExist("576529")).thenReturn(true)
        presenter.changeFavorite(Event(576529, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "576529", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null))
        verify(view).setViewIsFavorites(false, true)
    }

    @Test
    fun refreshFavorite_whenIsFavoriteTrue_shouldViewIsFavoriteWithParamTrue() {
        `when`(matchEventDB.isExist("576529")).thenReturn(true)
        presenter.refreshFavorite(whenEventExist())
        verify(view).setViewIsFavorites(true, false)
    }

    private fun whenEventExist(): Event {
        return Event(576529, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "576529", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)
    }

    private fun whenTeamResponseExist(): TeamResponse {
        val teamList = mutableListOf<Team>()
        val team1 = Team("133604", "https://www.thesportsdb.com/images/media/team/badge/vrtrtp1448813175.png")
        val team2 = Team("133605", "https://www.thesportsdb.com/images/media/team/badge/vrtrtp1448813176.png")
        teamList.add(team1)
        teamList.add(team2)
        return TeamResponse(teamList)
    }

    private fun whenTeamResponseIsEmpty(): TeamResponse {
        val teamList = mutableListOf<Team>()
        val team1 = Team(null, null)
        teamList.add(team1)
        return TeamResponse(teamList)
    }
}