package io.fastpix.data.domain.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.UUID

object SessionService {

    private var sessionId: String? = null
    private var sessionStartTime: Long? = null
    private var lastEventTime: Long? = null
    private var isSessionValid: Boolean = false
    private var sessionExpiryTime: Long? = null
    private val _sessionState = MutableLiveData<Boolean>()
    val sessionState: LiveData<Boolean> get() = _sessionState

    private const val SESSION_TIMEOUT_MS = 60 * 60 * 1000L // 1 hour

    /**
     * Start a new session (only after expiry or reset).
     */
    fun initializeSession() {
        val currentTime = System.currentTimeMillis()
        sessionId = UUID.randomUUID().toString()
        sessionStartTime = currentTime
        sessionExpiryTime = currentTime + SESSION_TIMEOUT_MS
        lastEventTime = currentTime
        isSessionValid = true
        _sessionState.postValue(true)
    }

    /**
     * Check if session is still valid.
     * If expired, invalidate session and return false.
     */
    fun validateSession(): Boolean {
        if (!isSessionValid || lastEventTime == null) {
            return false
        }

        val currentTime = System.currentTimeMillis()
        if (currentTime - lastEventTime!! >= SESSION_TIMEOUT_MS) {
            invalidateSession()
            return false
        }
        return true
    }

    /**
     * Expire the current session.
     */
    private fun invalidateSession() {
        isSessionValid = false
        sessionId = null
        _sessionState.postValue(false)
    }

    /**
     * Manually reset all session data.
     */
    fun reset() {
        isSessionValid = false
        sessionId = null
        sessionStartTime = null
        lastEventTime = null
        _sessionState.postValue(false)
    }

    fun getSessionId(): String? = sessionId
    fun getSessionStartTime(): Long? = sessionStartTime
    fun getLastEventTime(): Long? = lastEventTime
    fun getSessionExpireTime(): Long? = sessionExpiryTime
    fun isSessionValid(): Boolean = validateSession()
}
