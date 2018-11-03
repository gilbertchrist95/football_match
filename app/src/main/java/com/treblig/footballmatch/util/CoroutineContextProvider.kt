package com.treblig.footballmatch.util

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

open class CoroutineContextProvider {
    open val main: CoroutineContext by lazy { UI }
    open val IO: CoroutineContext by lazy { CommonPool }
}