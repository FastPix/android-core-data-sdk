package io.fastpix.data.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import io.fastpix.data.di.DependencyContainer
import io.fastpix.data.domain.state.SDKStateService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted

class NetworkTracker(
    context: Context
) {

    private lateinit var stateService: SDKStateService

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    
    // Companion object to hold shared state
    companion object {
        // Global shared flow across all instances
        private var globalSharedFlow: Flow<Boolean>? = null
        private var globalCallback: ConnectivityManager.NetworkCallback? = null
        private var globalConnectivityManager: ConnectivityManager? = null
        private var globalScope: CoroutineScope? = null
        private var isGlobalCallbackRegistered = false
        
        // Synchronization for thread safety
        private val lock = Any()
        
        /**
         * Cleanup global state (for testing)
         */
        fun cleanupGlobal() {
            synchronized(lock) {
                if (isGlobalCallbackRegistered && globalCallback != null) {
                    try {
                        globalConnectivityManager?.unregisterNetworkCallback(globalCallback!!)
                        Logger.log("NetworkTracker", "Global network callback unregistered successfully")
                    } catch (e: Exception) {
                        Logger.logError("NetworkTracker", "Failed to unregister global network callback", e)
                    } finally {
                        globalCallback = null
                        isGlobalCallbackRegistered = false
                        globalSharedFlow = null
                        globalConnectivityManager = null
                        globalScope = null
                    }
                }
            }
        }
    }

    init {
        initializeDependencies()
        // Initialize the shared flow on first instance
        synchronized(lock) {
            if (globalSharedFlow == null) {
                globalScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
                globalConnectivityManager = connectivityManager
                globalSharedFlow = createSharedFlow()
            }
        }
    }
    
    private fun initializeDependencies() {
        stateService = DependencyContainer.getSDKStateService()
    }
    
    private fun createSharedFlow(): Flow<Boolean> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    trySend(true)
                }

                override fun onLost(network: Network) {
                    trySend(false)
                }

                override fun onCapabilitiesChanged(
                    network: Network, networkCapabilities: NetworkCapabilities
                ) {
                    val hasInternet =
                        networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) && networkCapabilities.hasCapability(
                            NetworkCapabilities.NET_CAPABILITY_VALIDATED
                        )
                    trySend(hasInternet)
                }
            }

            val networkRequest =
                NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    .build()

            try {
                globalConnectivityManager?.registerNetworkCallback(networkRequest, callback)
                globalCallback = callback
                isGlobalCallbackRegistered = true
                Logger.log("NetworkTracker", "Network callback registered successfully")
            } catch (e: Exception) {
                Logger.logError("NetworkTracker", "Failed to register network callback", e)
            }

            // Check initial network state
            val currentNetwork = globalConnectivityManager?.activeNetwork
            val capabilities = globalConnectivityManager?.getNetworkCapabilities(currentNetwork)
            val isConnected =
                capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true && capabilities.hasCapability(
                    NetworkCapabilities.NET_CAPABILITY_VALIDATED
                )
            trySend(isConnected)

            awaitClose {
                // Don't unregister here - it will be done in cleanup()
            }
        }.distinctUntilChanged().shareIn(
            globalScope ?: CoroutineScope(Dispatchers.IO + SupervisorJob()),
            SharingStarted.Lazily,
            replay = 1
        )
    }

    /**
     * Flow that emits true when network is available, false otherwise
     * This Flow is shared across all instances to prevent multiple callback registrations
     */
    val isNetworkAvailable: Flow<Boolean> = globalSharedFlow!!

    /**
     * Check if network is currently available
     */
    fun isCurrentlyConnected(): Boolean {
        val currentNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(currentNetwork) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) && capabilities.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_VALIDATED
        )
    }

    /**
     * Get the current connection type: WIFI, MOBILE, ETHERNET, or OTHER
     */
    fun connectionType(capabilities: NetworkCapabilities?) {
        val connectionType = when {
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> "wifi"
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> "mobile"
            capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) == true -> "ethernet"
            else -> "other"
        }
        
        // Update the SDK state with the new connection type
        stateService.updateConnectionType(connectionType)
    }
    
    /**
     * Cleanup network callback registration
     */
    fun cleanup() {
        synchronized(lock) {
            if (isGlobalCallbackRegistered && globalCallback != null) {
                try {
                    globalConnectivityManager?.unregisterNetworkCallback(globalCallback!!)
                    Logger.log("NetworkTracker", "Network callback unregistered successfully")
                } catch (e: Exception) {
                    Logger.logError("NetworkTracker", "Failed to unregister network callback", e)
                } finally {
                    globalCallback = null
                    isGlobalCallbackRegistered = false
                    globalSharedFlow = null
                    globalConnectivityManager = null
                    globalScope = null
                }
            }
        }
    }
}
