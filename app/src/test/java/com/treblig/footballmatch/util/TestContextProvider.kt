package com.treblig.footballmatch.util

import kotlinx.coroutines.experimental.Unconfined
import kotlin.coroutines.experimental.CoroutineContext

class TestContextProvider : CoroutineContextProvider() {
    override val main: CoroutineContext = Unconfined
    override val IO: CoroutineContext = Unconfined
}