package io.fastpix.data.domain.state

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SessionServiceTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        SessionService.reset()
    }

    @After
    fun tearDown() {
        SessionService.reset()
    }

    @Test
    fun `validateSession returns false before initialization`() {
        assertFalse(SessionService.validateSession())
    }

    @Test
    fun `validateSession returns true after initialization`() {
        SessionService.initializeSession()
        assertTrue(SessionService.validateSession())
    }

    @Test
    fun `initializeSession sets sessionId and traceId`() {
        SessionService.initializeSession()
        assertNotNull(SessionService.getSessionId())
        assertNotNull(SessionService.getTraceId())
    }

    @Test
    fun `reset invalidates session`() {
        SessionService.initializeSession()
        assertTrue(SessionService.validateSession())

        SessionService.reset()
        assertFalse(SessionService.validateSession())
    }

    @Test
    fun `reset clears sessionId and traceId`() {
        SessionService.initializeSession()
        assertNotNull(SessionService.getSessionId())

        SessionService.reset()
        assert(SessionService.getSessionId() == null)
        assert(SessionService.getTraceId() == null)
    }

    @Test
    fun `validateSession returns false after reset even if recently active`() {
        SessionService.initializeSession()
        assertTrue(SessionService.validateSession())
        assertTrue(SessionService.validateSession())

        SessionService.reset()

        assertFalse(SessionService.validateSession())
    }

    @Test
    fun `consecutive validateSession calls succeed within timeout`() {
        SessionService.initializeSession()
        repeat(100) {
            assertTrue("validateSession should succeed on call #$it", SessionService.validateSession())
        }
    }

    @Test
    fun `re-initialization after reset creates new session`() {
        SessionService.initializeSession()
        val firstSessionId = SessionService.getSessionId()

        SessionService.reset()
        SessionService.initializeSession()
        val secondSessionId = SessionService.getSessionId()

        assertNotNull(firstSessionId)
        assertNotNull(secondSessionId)
        assertFalse(
            "New session should have different ID",
            firstSessionId == secondSessionId
        )
    }
}
