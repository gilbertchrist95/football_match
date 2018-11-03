package com.treblig.footballmatch.di

import android.app.Application
import android.content.Context
import com.treblig.footballmatch.db.MatchEventDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
open class DatabaseModule {

    @Provides
    @Singleton
    open fun provideMatchDb(context: Context): MatchEventDB {
        return MatchEventDB(context)
    }
}