package io.fastpix.data.domain.wallclock

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

object ViewWatchCounter {
    private const val intervalMillis: Long = 250L
    private var job: Job? = null
    private var scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private var _value: Long = 0
    val value: Long
        get() = _value


    fun start() {
        if (job?.isActive == true) return // Already running
        job = scope.launch {
            while (isActive) {
                delay(intervalMillis)
                _value += intervalMillis
            }
        }
    }

    fun pause() {
        job?.cancel()
        job = null
    }

    fun reset() {
        _value = 0
    }

    fun destroy() {
        pause()
        _value = 0
        scope.cancel()
        scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }
}