package com.treblig.footballmatch.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class ApplicationModule(private val application: Application){
    @Singleton
    @Provides
    open fun provideApplication(): Application {
        return application
    }

    @Singleton
    @Provides
    open fun provideApplicationContext(): Context {
        return application
    }
}





