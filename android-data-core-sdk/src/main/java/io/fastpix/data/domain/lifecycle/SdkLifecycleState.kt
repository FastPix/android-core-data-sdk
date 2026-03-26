package io.fastpix.data.domain.lifecycle

import io.fastpix.data.utils.Logger

/**
 * Lifecycle state of the SDK. Events are only dispatched when state is [INITIALIZED].
 *
 * Valid transitions:
 * NOT_INITIALIZED → INITIALIZING → INITIALIZED → RELEASING → RELEASED
 */
enum class SdkLifecycleState {
    NOT_INITIALIZED,
    INITIALIZING,
    INITIALIZED,
    RELEASING,
    RELEASED;

    /**
     * Returns true if the transition from this state to [target] is valid.
     */
    fun canTransitionTo(target: SdkLifecycleState): Boolean {
        return when (this) {
            NOT_INITIALIZED -> target == INITIALIZING
            INITIALIZING -> target == INITIALIZED || target == NOT_INITIALIZED
            INITIALIZED -> target == RELEASING
            RELEASING -> target == RELEASED
            RELEASED -> false
        }
    }

    /**
     * Attempts to transition to [target]. Returns true if transition was valid and applied.
     * Logs and returns false for invalid transitions.
     */
    fun transitionTo(target: SdkLifecycleState): Boolean {
        return if (canTransitionTo(target)) true
        else {
            Logger.logWarning(
                "SdkLifecycleState",
                "Invalid transition: $this -> $target"
            )
            false
        }
    }

    fun isAcceptingEvents(): Boolean = this == INITIALIZED
    fun isReleased(): Boolean = this == RELEASED
}
