package com.treblig.footballmatch.util

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class SchedulerRule : TestRule {

    override fun apply(base: Statement, d: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { _ -> Schedulers.trampoline() }
                RxJavaPlugins.setNewThreadSchedulerHandler { _ -> Schedulers.trampoline() }
                RxJavaPlugins.setComputationSchedulerHandler { _ -> Schedulers.trampoline() }
                RxJavaPlugins.setIoSchedulerHandler { _ -> Schedulers.trampoline() }
                RxJavaPlugins.setSingleSchedulerHandler { _ -> Schedulers.trampoline() }
                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}