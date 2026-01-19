package io.fastpix.data.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import io.fastpix.data.di.DependencyContainer
import java.net.URI
import java.util.UUID

object Utils {

    fun randomV4(): String {
        return UUID.randomUUID().toString()
    }

    fun currentTimeStamp(): Long {
        return System.currentTimeMillis()
    }

    fun getDomain(url: String?): String? {
        return try {
            URI(url).host
        } catch (ex: Exception) {
            null
        }
    }

    /**
     * Check the current network type
     * This is equivalent to the Dart Utils.checkNetworkType() method
     */
    fun checkNetworkType(): String? {
        return try {
            val context = DependencyContainer.getContext()
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val currentNetwork = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(currentNetwork)

            val connectionType = when {
                capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> "wifi"
                capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> "cellular"
                capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) == true -> "ethernet"
                else -> null
            }

            // Update the SDK state with the connection type
            val sdkStateService = DependencyContainer.getSDKStateService()
            sdkStateService.updateConnectionType(connectionType)

            connectionType
        } catch (e: Exception) {
            null
        }
    }
}