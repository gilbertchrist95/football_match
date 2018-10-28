package com.treblig.footballmatch.db

import android.content.Context
import com.treblig.footballmatch.pojo.Event
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

object MatchEventDB {

    fun isExist(context: Context, eventId: String): Boolean {
        var isExist = false
        context.database.use {
            select(Event.TABLE_NAME)
                    .whereArgs("(${Event.EVENT_ID} = {eventId})", "eventId" to eventId).exec {
                        isExist = this.count > 0
                    }
        }
        return isExist
    }

    fun insert(context: Context, event: Event): Long {
        var rowId = 0L
        context.database.use {
            rowId = insert(Event.TABLE_NAME,
                    Event.AWAY_FORMATION to event.strAwayFormation,
                    Event.AWAY_GOAL_DETAILS to event.strAwayGoalDetails,
                    Event.AWAY_LINEUP_DEFENSE to event.strAwayLineupDefense,
                    Event.AWAY_LINEUP_FORWARD to event.strAwayLineupForward,
                    Event.AWAY_LINEUP_GOAL_KEEPER to event.strAwayLineupGoalkeeper,
                    Event.AWAY_LINEUP_MIDFIELD to event.strAwayLineupMidfield,
                    Event.AWAY_LINEUP_SUBTITUTES to event.strAwayLineupSubstitutes,
                    Event.AWAY_RED_CARDS to event.strAwayRedCards,
                    Event.AWAY_SCORE to event.intAwayScore,
                    Event.AWAY_SHOTS to event.intAwayShots,
                    Event.AWAY_TEAM to event.strAwayTeam,
                    Event.AWAY_TEAM_ID to event.idAwayTeam,
                    Event.AWAY_YELLOW_CARDS to event.strAwayYellowCards,
                    Event.BANNER to event.strBanner,
                    Event.CIRCUIT to event.strCircuit,
                    Event.CITY to event.strCity,
                    Event.COUNTRY to event.strCountry,
                    Event.DATE to event.strDate,
                    Event.DATE_EVENT to event.dateEvent,
                    Event.DESCRIPTION_EN to event.strDescriptionEN,
                    Event.EVENT to event.strEvent,
                    Event.EVENT_ID to event.idEvent,
                    Event.FANART to event.strFanart,
                    Event.FILE_NAME to event.strFilename,
                    Event.HOME_FORMATION to event.strHomeFormation,
                    Event.HOME_GOAL_DETAILS to event.strHomeGoalDetails,
                    Event.HOME_LINEUP_DEFENSE to event.strHomeLineupDefense,
                    Event.HOME_LINEUP_FORWARD to event.strHomeLineupForward,
                    Event.HOME_LINEUP_GOAL_KEEPER to event.strHomeLineupGoalkeeper,
                    Event.HOME_LINEUP_MIDFIELD to event.strHomeLineupMidfield,
                    Event.HOME_LINEUP_SUBSTITUTES to event.strHomeLineupSubstitutes,
                    Event.HOME_RED_CARDS to event.strHomeRedCards,
                    Event.HOME_SCORE to event.intHomeScore,
                    Event.HOME_SHOTS to event.intHomeShots,
                    Event.HOME_TEAM to event.strHomeTeam,
                    Event.HOME_TEAM_ID to event.idHomeTeam,
                    Event.HOME_YELLOW_CARDS to event.strHomeYellowCards,
                    Event.LEAGUE to event.strLeague,
                    Event.LEAGUE_ID to event.idLeague,
                    Event.LOCKED to event.strLocked,
                    Event.MAP to event.strMap,
                    Event.POSTER to event.strPoster,
                    Event.RESULT to event.strResult,
                    Event.ROUND to event.intRound,
                    Event.SEASON to event.strSeason,
                    Event.SOCCER_XML_ID to event.idSoccerXML,
                    Event.SPECTATORS to event.intSpectators,
                    Event.SPORT to event.strSport,
                    Event.THUMB to event.strThumb,
                    Event.TIME to event.strTime,
                    Event.TV_STATION to event.strTVStation)
        }
        return rowId
    }

    fun delete(context: Context, eventId: String): Int {
        var deleted: Int
        context.database.use {
            deleted =  delete(Event.TABLE_NAME, "(${Event.EVENT_ID} = {eventId})",
                    "eventId" to eventId)
            return@use deleted
        }
        return 0
    }

    fun getFavorites(context: Context): List<Event>? {
        var result: List<Event>? = null
        context.database.use {
            val cursor = select(Event.TABLE_NAME)
            result = cursor.parseList(classParser())
        }
        return result
    }


}






