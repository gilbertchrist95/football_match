package com.treblig.footballmatch

import android.app.Application
import com.treblig.footballmatch.di.*

class FootballApplication : Application() {

    companion object {

        //the ApplicationComponent for depedency injection context
        @JvmStatic
        private lateinit var appComponent: ApplicationComponent

        // the AppModule which was created during the setup
        @JvmStatic
        private lateinit var appModule: ApplicationModule

        // the NetworkModule which was created during the setup
        @JvmStatic
        private lateinit var networkModule: NetworkModule

        // the DatabaseModule which was created during the setup
        @JvmStatic
        private lateinit var dbModule: DatabaseModule

        fun applicationComponent(): ApplicationComponent {
            return appComponent
        }
    }

    private val component: ApplicationComponent by lazy {
        appModule = ApplicationModule(this)
        dbModule = DatabaseModule()
        networkModule = NetworkModule()
        DaggerApplicationComponent.builder()
                .applicationModule(appModule)
                .networkModule(networkModule)
                .databaseModule(dbModule).build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = component
    }
}






