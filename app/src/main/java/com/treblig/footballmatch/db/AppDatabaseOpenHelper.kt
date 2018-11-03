package com.treblig.footballmatch.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.treblig.footballmatch.pojo.Event
import org.jetbrains.anko.db.*

class AppDatabaseOpenHelper(context: Context) : ManagedSQLiteOpenHelper(context, "MatchEvent.db", null, 1) {

    companion object {
        private var instance: AppDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabaseOpenHelper {
            if (instance == null) {
                instance = AppDatabaseOpenHelper(context)
            }
            return instance as AppDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(Event.TABLE_NAME, true,
                Event.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Event.AWAY_FORMATION to TEXT,
                Event.AWAY_GOAL_DETAILS to TEXT,
                Event.AWAY_LINEUP_DEFENSE to TEXT,
                Event.AWAY_LINEUP_FORWARD to TEXT,
                Event.AWAY_LINEUP_GOAL_KEEPER to TEXT,
                Event.AWAY_LINEUP_MIDFIELD to TEXT,
                Event.AWAY_LINEUP_SUBTITUTES to TEXT,
                Event.AWAY_RED_CARDS to TEXT,
                Event.AWAY_SCORE to TEXT,
                Event.AWAY_SHOTS to TEXT,
                Event.AWAY_TEAM to TEXT,
                Event.AWAY_TEAM_ID to TEXT,
                Event.AWAY_YELLOW_CARDS to TEXT,
                Event.BANNER to TEXT,
                Event.CIRCUIT to TEXT,
                Event.CITY to TEXT,
                Event.COUNTRY to TEXT,
                Event.DATE to TEXT,
                Event.DATE_EVENT to TEXT,
                Event.DESCRIPTION_EN to TEXT,
                Event.EVENT to TEXT,
                Event.EVENT_ID to TEXT + UNIQUE,
                Event.FANART to TEXT,
                Event.FILE_NAME to TEXT,
                Event.HOME_FORMATION to TEXT,
                Event.HOME_GOAL_DETAILS to TEXT,
                Event.HOME_LINEUP_DEFENSE to TEXT,
                Event.HOME_LINEUP_FORWARD to TEXT,
                Event.HOME_LINEUP_GOAL_KEEPER to TEXT,
                Event.HOME_LINEUP_MIDFIELD to TEXT,
                Event.HOME_LINEUP_SUBSTITUTES to TEXT,
                Event.HOME_RED_CARDS to TEXT,
                Event.HOME_SCORE to TEXT,
                Event.HOME_SHOTS to TEXT,
                Event.HOME_TEAM to TEXT,
                Event.HOME_TEAM_ID to TEXT,
                Event.HOME_YELLOW_CARDS to TEXT,
                Event.LEAGUE to TEXT,
                Event.LEAGUE_ID to TEXT,
                Event.LOCKED to TEXT,
                Event.MAP to TEXT,
                Event.POSTER to TEXT,
                Event.RESULT to TEXT,
                Event.ROUND to TEXT,
                Event.SEASON to TEXT,
                Event.SOCCER_XML_ID to TEXT,
                Event.SPECTATORS to TEXT,
                Event.SPORT to TEXT,
                Event.THUMB to TEXT,
                Event.TIME to TEXT,
                Event.TV_STATION to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(Event.TABLE_NAME, true)
    }

}

val Context.database: AppDatabaseOpenHelper
    get() = AppDatabaseOpenHelper.getInstance(context = this)






