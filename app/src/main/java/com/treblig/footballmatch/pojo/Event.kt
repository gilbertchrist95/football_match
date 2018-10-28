package com.treblig.footballmatch.pojo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event(
        val rowId: Long?,
        val strAwayFormation: String?,
        val strAwayGoalDetails: String?,
        val strAwayLineupDefense: String?,
        val strAwayLineupForward: String?,
        val strAwayLineupGoalkeeper: String?,
        val strAwayLineupMidfield: String?,
        val strAwayLineupSubstitutes: String?,
        val strAwayRedCards: String?,
        val intAwayScore: String?,
        val intAwayShots: String?,
        val strAwayTeam: String?,
        val idAwayTeam: String?,
        val strAwayYellowCards: String?,
        val strBanner: String?,
        val strCircuit: String?,
        val strCity: String?,
        val strCountry: String?,
        val strDate: String?,
        val dateEvent: String?,
        val strDescriptionEN: String?,
        val strEvent: String?,
        val idEvent: String?,
        val strFanart: String?,
        val strFilename: String?,
        val strHomeFormation: String?,
        val strHomeGoalDetails: String?,
        val strHomeLineupDefense: String?,
        val strHomeLineupForward: String?,
        val strHomeLineupGoalkeeper: String?,
        val strHomeLineupMidfield: String?,
        val strHomeLineupSubstitutes: String?,
        val strHomeRedCards: String?,
        val intHomeScore: String?,
        val intHomeShots: String?,
        val strHomeTeam: String?,
        val idHomeTeam: String?,
        val strHomeYellowCards: String?,
        val strLeague: String?,
        val idLeague: String?,
        val strLocked: String?,
        val strMap: String?,
        val strPoster: String?,
        val strResult: String?,
        val intRound: String?,
        val strSeason: String?,
        val idSoccerXML: String?,
        val intSpectators: String?,
        val strSport: String?,
        val strThumb: String?,
        val strTime: String?,
        val strTVStation: String?

) : Parcelable {

    companion object {
        const val TABLE_NAME = "Event"
        const val ID = "_id"
        const val AWAY_FORMATION = "AWAY_FORMATION"
        const val AWAY_GOAL_DETAILS = "AWAY_GOAL_DETAILS"
        const val AWAY_LINEUP_DEFENSE = "AWAY_LINEUP_DEFENSE"
        const val AWAY_LINEUP_FORWARD = "AWAY_LINEUP_FORWARD"
        const val AWAY_LINEUP_GOAL_KEEPER = "AWAY_LINEUP_GOAL_KEEPER"
        const val AWAY_LINEUP_MIDFIELD = "AWAY_LINEUP_MIDFIELD"
        const val AWAY_LINEUP_SUBTITUTES = "AWAY_LINEUP_SUBTITUTES"
        const val AWAY_RED_CARDS = "AWAY_RED_CARDS"
        const val AWAY_SCORE = "AWAY_SCORE"
        const val AWAY_SHOTS = "AWAY_SHOTS"
        const val AWAY_TEAM = "AWAY_TEAM"
        const val AWAY_TEAM_ID = "AWAY_TEAM_ID"
        const val AWAY_YELLOW_CARDS = "AWAY_YELLOW_CARDS"
        const val BANNER = "BANNER"
        const val CIRCUIT = "CIRCUIT"
        const val CITY = "CITY"
        const val COUNTRY = "COUNTRY"
        const val DATE = "DATE"
        const val DATE_EVENT = "DATE_EVENT"
        const val DESCRIPTION_EN = "DESCRIPTION_EN"
        const val EVENT = "EVENT"
        const val EVENT_ID = "EVENT_ID"
        const val FANART = "FANART"
        const val FILE_NAME = "FILE_NAME"
        const val HOME_FORMATION = "HOME_FORMATION"
        const val HOME_GOAL_DETAILS = "HOME_GOAL_DETAILS"
        const val HOME_LINEUP_DEFENSE = "HOME_LINEUP_DEFENSE"
        const val HOME_LINEUP_FORWARD = "HOME_LINEUP_FORWARD"
        const val HOME_LINEUP_GOAL_KEEPER = "HOME_LINEUP_GOAL_KEEPER"
        const val HOME_LINEUP_MIDFIELD = "HOME_LINEUP_MIDFIELD"
        const val HOME_LINEUP_SUBSTITUTES = "HOME_LINEUP_SUBSTITUTES"
        const val HOME_RED_CARDS = "HOME_RED_CARDS"
        const val HOME_SCORE = "HOME_SCORE"
        const val HOME_SHOTS = "HOME_SHOTS"
        const val HOME_TEAM = "HOME_TEAM"
        const val HOME_TEAM_ID = "HOME_TEAM_ID"
        const val HOME_YELLOW_CARDS = "HOME_YELLOW_CARDS"
        const val LEAGUE = "LEAGUE"
        const val LEAGUE_ID = "LEAGUE_ID"
        const val LOCKED = "LOCKED"
        const val MAP = "MAP"
        const val POSTER = "POSTER"
        const val RESULT = "RESULT"
        const val ROUND = "ROUND"
        const val SEASON = "SEASON"
        const val SOCCER_XML_ID = "SOCCER_XML_ID"
        const val SPECTATORS = "SPECTATORS"
        const val SPORT = "SPORT"
        const val THUMB = "THUMB"
        const val TIME = "TIME"
        const val TV_STATION = "TV_STATION"

    }
}