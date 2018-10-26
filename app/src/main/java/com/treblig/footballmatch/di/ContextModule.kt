package com.treblig.footballmatch.di

import android.app.Application
import android.content.Context
import com.treblig.footballmatch.ui.base.BaseView
import dagger.Module
import dagger.Provides


@Module
object ContextModule {
    @Provides
    @JvmStatic
    internal fun provideContext(baseView: BaseView): Context {
        return baseView.getContext()
    }

    @Provides
    @JvmStatic
    internal fun provideApplication(context: Context): Application {
        return context.applicationContext as Application
    }
}






