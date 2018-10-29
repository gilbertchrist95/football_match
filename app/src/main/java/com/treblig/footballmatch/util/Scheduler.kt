package com.treblig.footballmatch.util

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun mainThread(): Scheduler = AndroidSchedulers.mainThread()

fun ioThread(): Scheduler = Schedulers.io()

fun computationThread(): Scheduler = Schedulers.computation()