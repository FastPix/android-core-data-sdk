package io.fastpix.data.utils

import android.util.Log

class Logger private constructor() {

    companion object {
        private const val TAG = "FastPixSDK"
        private var isLoggingEnabled = false
        
        /**
         * Enable logging for the SDK
         */
        fun enableLogging() {
            isLoggingEnabled = true
        }
        
        /**
         * Disable logging for the SDK
         */
        fun disableLogging() {
            isLoggingEnabled = false
        }
        
        /**
         * Check if logging is enabled
         */
        fun isLoggingEnabled(): Boolean = isLoggingEnabled
        
        /**
         * Log a debug message
         */
        fun log(message: String) {
            if (isLoggingEnabled) {
                Log.d(TAG, message)
            }
        }
        
        /**
         * Log a debug message with tag
         */
        fun log(tag: String, message: String) {
            if (isLoggingEnabled) {
                Log.d(tag, message)
            }
        }
        
        /**
         * Log an error message
         */
        fun logError(message: String, throwable: Throwable? = null) {
            if (isLoggingEnabled) {
                Log.e(TAG, message, throwable)
            }
        }
        
        /**
         * Log an error message with tag
         */
        fun logError(tag: String, message: String, throwable: Throwable? = null) {
            if (isLoggingEnabled) {
                Log.e(tag, message, throwable)
            }
        }
        
        /**
         * Log an info message
         */
        fun logInfo(message: String) {
            if (isLoggingEnabled) {
                Log.i(TAG, message)
            }
        }
        
        /**
         * Log an info message with tag
         */
        fun logInfo(tag: String, message: String) {
            if (isLoggingEnabled) {
                Log.i(tag, message)
            }
        }
        
        /**
         * Log a warning message
         */
        fun logWarning(message: String) {
            if (isLoggingEnabled) {
                Log.w(TAG, message)
            }
        }
        
        /**
         * Log a warning message with tag
         */
        fun logWarning(tag: String, message: String) {
            if (isLoggingEnabled) {
                Log.w(tag, message)
            }
        }
    }
}
