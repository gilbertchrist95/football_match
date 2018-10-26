package com.treblig.footballmatch.api

import com.treblig.footballmatch.BuildConfig
import com.treblig.footballmatch.pojo.LeagueResponse
import com.treblig.footballmatch.pojo.MatchEventResponse
import com.treblig.footballmatch.pojo.TeamResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface TheSportApi {
    @GET("${BuildConfig.TSDB_API_KEY}/all_leagues.php")
    fun getAllLeagues(): Observable<LeagueResponse>

    @GET("${BuildConfig.TSDB_API_KEY}/eventspastleague.php")
    fun getPrevLeagueEvents(@Query("id") id: String): Observable<MatchEventResponse>

    @GET("${BuildConfig.TSDB_API_KEY}/eventsnextleague.php")
    fun getNextLeagueEvents(@Query("id") id: String): Observable<MatchEventResponse>

    @GET("${BuildConfig.TSDB_API_KEY}/lookupteam.php")
    fun lookUpTeam(@Query("id") id: String): Observable<TeamResponse>
}






