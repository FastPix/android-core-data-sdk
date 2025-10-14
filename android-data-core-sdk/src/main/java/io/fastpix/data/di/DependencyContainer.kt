package io.fastpix.data.di

import android.annotation.SuppressLint
import android.content.Context
import io.fastpix.data.domain.calculator.EventDataCalculator
import io.fastpix.data.domain.repository.EventApiService
import io.fastpix.data.domain.repository.EventDispatcher
import io.fastpix.data.domain.state.SDKStateService
import io.fastpix.data.domain.state.SessionService
import io.fastpix.data.domain.wallclock.ViewWatchCounter
import io.fastpix.data.utils.DeviceInfoUtility
import io.fastpix.data.utils.Logger
import io.fastpix.data.utils.NetworkTracker
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import android.os.Build
import io.fastpix.data.sdkBuild.SDKBuildConfig
import okhttp3.Interceptor

/**
 * Manual dependency injection container
 * Replaces Hilt/Dagger for dependency management
 */
@SuppressLint("StaticFieldLeak")
object DependencyContainer {

    private var context: Context? = null
    private var isInitialized = false

    // Singleton instances
    private var _okHttpClient: OkHttpClient? = null
    private var _retrofit: Retrofit? = null
    private var _eventApiService: EventApiService? = null
    private var _networkTracker: NetworkTracker? = null
    private var _eventDispatcher: EventDispatcher? = null
    private var _sdkStateService: SDKStateService? = null
    private var _eventDataCalculator: EventDataCalculator? = null
    private var _deviceInfoUtility: DeviceInfoUtility? = null

    /**
     * Initialize the dependency container with application context
     */
    fun initialize(context: Context) {
        if (isInitialized) {
            Logger.logWarning("DependencyContainer", "Already initialized")
            return
        }

        this.context = context.applicationContext
        isInitialized = true
        Logger.log("DependencyContainer", "Initialized successfully")
    }

    /**
     * Get application context
     */
    fun getContext(): Context {
        return context
            ?: throw IllegalStateException("DependencyContainer not initialized. Call initialize() first.")
    }

    /**
     * Get OkHttpClient instance
     */
    fun getOkHttpClient(): OkHttpClient {
        if (_okHttpClient == null) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }

            // User-Agent interceptor to set proper Android user agent
            val userAgentInterceptor = Interceptor { chain ->
                val request = chain.request()
                val vmVersion = System.getProperty("java.vm.version")
                val userAgent =
                    "Dalvik/${vmVersion} (Linux; U; Android ${Build.VERSION.RELEASE}; ${Build.MODEL} Build/${Build.ID})"

                val newRequest = request.newBuilder()
                    .header("User-Agent", userAgent)
                    .build()

                chain.proceed(newRequest)
            }

            _okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(userAgentInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
        }
        return _okHttpClient!!
    }

    /**
     * Get Retrofit instance
     */
    fun getRetrofit(): Retrofit {
        if (_retrofit == null) {
            _retrofit = Retrofit.Builder()
                .baseUrl(SDKBuildConfig.SDK_URL) // Replace with your actual API base URL
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return _retrofit!!
    }

    /**
     * Get EventApiService instance
     */
    fun getEventApiService(): EventApiService {
        if (_eventApiService == null) {
            _eventApiService = getRetrofit().create(EventApiService::class.java)
        }
        return _eventApiService!!
    }

    /**
     * Get NetworkTracker instance
     */
    fun getNetworkTracker(): NetworkTracker {
        if (_networkTracker == null) {
            _networkTracker = NetworkTracker(getContext())
        }
        return _networkTracker!!
    }

    /**
     * Get SDKStateService instance
     */
    fun getSDKStateService(): SDKStateService {
        if (_sdkStateService == null) {
            _sdkStateService = SDKStateService()
        }
        return _sdkStateService!!
    }

    /**
     * Get EventDataCalculator instance
     */
    fun getEventDataCalculator(): EventDataCalculator {
        if (_eventDataCalculator == null) {
            _eventDataCalculator = EventDataCalculator()
        }
        return _eventDataCalculator!!
    }

    /**
     * Get DeviceInfoUtility instance
     */
    fun getDeviceInfoUtility(): DeviceInfoUtility {
        if (_deviceInfoUtility == null) {
            _deviceInfoUtility = DeviceInfoUtility(getContext())
        }
        return _deviceInfoUtility!!
    }

    /**
     * Get EventDispatcher instance
     */
    fun getEventDispatcher(): EventDispatcher {
        if (_eventDispatcher == null) {
            _eventDispatcher = EventDispatcher(
                getEventApiService(),
                getNetworkTracker()
            )
        }
        return _eventDispatcher!!
    }

    /**
     * Reset all instances (useful for testing)
     */
    fun reset() {
        _eventDataCalculator?.reset()
        _sdkStateService?.clearSdkState()
        ViewWatchCounter.destroy()
        SessionService.reset()
        _okHttpClient = null
        _retrofit = null
        _eventApiService = null
        _networkTracker = null
        _eventDispatcher = null
        _sdkStateService = null
        _eventDataCalculator = null
        _deviceInfoUtility = null
        context = null
        isInitialized = false
        Logger.log("DependencyContainer", "Reset completed")
    }
}
