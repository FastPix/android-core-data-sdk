package io.fastpix.data.domain.model.events

import io.fastpix.data.domain.SDKConfiguration
import io.fastpix.data.domain.state.SessionService
import io.fastpix.data.utils.Utils
import io.fastpix.data.sdkBuild.SDKBuildConfig
import io.fastpix.data.di.DependencyContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ViewBeginEvent(
    override var workSpaceId: String? = null,
    override var viewId: String? = null,
    override var viewSequenceNumber: String? = null,
    override var playerSequenceNumber: String? = null,
    override var beaconDomain: String? = null,
    override var playheadTime: String? = null,
    override var viewerTimeStamp: String? = null,
    override var playerInstanceId: String? = null,
    override var viewWatchTime: String? = null,
    override var connectionType: String? = null,
    @SerialName("vest") var viewBegin: String? = null,
    @SerialName("snid") var sessionId: String? = null,
    @SerialName("snst") var sessionStart: String? = null,
    @SerialName("vdsour") var videoSourceUrl: String? = null,
    @SerialName("vdsohn") var videoHostName: String? = null,
    @SerialName("snepti") var sessionExpires: String? = null,
    @SerialName("fpviid") var fpViewerId: String? = null,
    @SerialName("vdtt") var videoTitle: String? = null,
    @SerialName("vdid") var videoId: String? = null,
    @SerialName("plna") var playerName: String? = null,
    @SerialName("dena") var deviceName: String? = null,
    @SerialName("decg") var deviceCategory: String? = null,
    @SerialName("demr") var deviceManufacturer: String? = null,
    @SerialName("demo") var deviceModel: String? = null,
    @SerialName("plvn") var playerVersion: String? = null,
    @SerialName("plwt") var playerWidth: String? = null,
    @SerialName("plht") var playerHeight: String? = null,
    @SerialName("rqvdwt") var videoWidth: String? = null,
    @SerialName("rqvdht") var videoHeight: String? = null,
    @SerialName("plswna") var softwareName: String? = null,
    @SerialName("plswvn") var softwareVersion: String? = null,
    @SerialName("osna") var osName: String? = null,
    @SerialName("osvn") var osVersion: String? = null,
    @SerialName("plfpsdna") var fpSDKName: String? = null,
    @SerialName("plfpsdvn") var fpSDKVersion: String? = null,
    @SerialName("vdsmty") var streamType: String? = null,
    @SerialName("plauon") var autoPlay: String? = null,
    @SerialName("vesnid") var viewSessionId: String? = null,
    @SerialName("vdsomity") var mimeType: String? = null,
    @SerialName("fpaivn") var fastPixApiVersion: String? = null,
    @SerialName("vdsocc") var videoCodec: String? = null,
    @SerialName("vdlncd") var videoLanguage: String? = null,
    @SerialName("vdsodu") var videoDuration: String? = null,
    @SerialName("vdpour") var videoThumbnail: String? = null,
    @SerialName("vdsr") var videoSeries: String? = null,
    @SerialName("vdpd") var videoProducer: String? = null,
    @SerialName("vdctty") var videoContentType: String? = null,
    @SerialName("vdvana") var videoVariant: String? = null,
    @SerialName("br") var applicationName: String? = null,
    @SerialName("brvn") var applicationVersion: String? = null,
    @SerialName("vddmty") var drmType: String? = null,
    @SerialName("cm1") var cm1: String? = null,
    @SerialName("cm2") var cm2: String? = null,
    @SerialName("cm3") var cm3: String? = null,
    @SerialName("cm4") var cm4: String? = null,
    @SerialName("cm5") var cm5: String? = null,
    @SerialName("cm6") var cm6: String? = null,
    @SerialName("cm7") var cm7: String? = null,
    @SerialName("cm8") var cm8: String? = null,
    @SerialName("cm9") var cm9: String? = null,
    @SerialName("cm10") var cm10: String? = null,
    @SerialName("evna") var eventName: String = "viewBegin"
) : BaseEvent(
    workSpaceId, viewId, viewSequenceNumber, playerSequenceNumber, beaconDomain,
    playheadTime, viewerTimeStamp, playerInstanceId, viewWatchTime, connectionType
) {

    override fun toJson(): Map<String, String?> {
        val baseJson = super.toJson()
        return baseJson + mapOf(
            "vest" to viewBegin,
            "snid" to sessionId,
            "snst" to sessionStart,
            "vdsour" to videoSourceUrl,
            "vdsohn" to videoHostName,
            "snepti" to sessionExpires,
            "fpviid" to fpViewerId,
            "vdtt" to videoTitle,
            "vdid" to videoId,
            "plna" to playerName,
            "dena" to deviceName,
            "decg" to deviceCategory,
            "demr" to deviceManufacturer,
            "demo" to deviceModel,
            "plvn" to playerVersion,
            "plwt" to playerWidth,
            "plht" to playerHeight,
            "vdsowt" to videoWidth,
            "vdsoht" to videoHeight,
            "plswna" to softwareName,
            "plswvn" to softwareVersion,
            "osna" to osName,
            "osvn" to osVersion,
            "plfpsdna" to fpSDKName,
            "plfpsdvn" to fpSDKVersion,
            "vdsmty" to streamType,
            "plauon" to autoPlay,
            "vesnid" to viewSessionId,
            "vdsomity" to mimeType,
            "fpaivn" to fastPixApiVersion,
            "vdsocc" to videoCodec,
            "vdlncd" to videoLanguage,
            "vdsodu" to videoDuration,
            "vdpour" to videoThumbnail,
            "vdsr" to videoSeries,
            "vdpd" to videoProducer,
            "vdctty" to videoContentType,
            "vdvana" to videoVariant,
            "br" to applicationName,
            "brvn" to applicationVersion,
            "vddmty" to drmType,
            "cm1" to cm1,
            "cm2" to cm2,
            "cm3" to cm3,
            "cm4" to cm4,
            "cm5" to cm5,
            "cm6" to cm6,
            "cm7" to cm7,
            "cm8" to cm8,
            "cm9" to cm9,
            "cm10" to cm10,
            "evna" to eventName
        )
    }

    companion object {
        /**
         * Create a ViewBeginEvent with all data populated
         */
        fun createViewBeginEvent(configService: SDKConfiguration): ViewBeginEvent {
            val sdkStateService = DependencyContainer.getSDKStateService()
            val deviceInfoUtility = DependencyContainer.getDeviceInfoUtility()
            val baseData = getBaseEventData(configService)
            sdkStateService.updateViewBeginTime()
            sdkStateService.viewSequenceNumber()
            sdkStateService.playerSequenceNumber()
            val playerListener = configService.playerListener

            return ViewBeginEvent(
                workSpaceId = baseData["wsid"],
                viewId = baseData["veid"],
                viewSequenceNumber = baseData["vesqnu"],
                playerSequenceNumber = baseData["plsqnu"],
                beaconDomain = baseData["bedn"],
                playheadTime = baseData["plphti"],
                viewerTimeStamp = baseData["vitp"],
                playerInstanceId = baseData["plinid"],
                viewWatchTime = baseData["vewati"],
                connectionType = baseData["vicity"],
                viewBegin = baseData["vitp"], // Use timestamp as view begin time
                sessionId = SessionService.getSessionId(),
                sessionStart = SessionService.getSessionStartTime()?.toString(),
                videoSourceUrl = configService.videoData?.videoSourceUrl
                    ?: playerListener.sourceUrl(),
                videoHostName = Utils.getDomain(
                    configService.videoData?.videoSourceUrl ?: playerListener.sourceUrl()
                ),
                sessionExpires = SessionService.getSessionExpireTime()?.toString(),
                fpViewerId = DependencyContainer.getViewerPref()?.getViewerId(),
                videoTitle = configService.videoData?.videoTitle,
                videoId = configService.videoData?.videoId,
                playerName = configService.playerData?.playerName,
                deviceName = deviceInfoUtility.getDeviceName(),
                deviceCategory = deviceInfoUtility.getDeviceCategory(),
                deviceManufacturer = deviceInfoUtility.getDeviceManufacturer(),
                deviceModel = deviceInfoUtility.getDeviceModel(),
                playerVersion = configService.playerData?.playerVersion,
                playerWidth = playerListener.playerWidth()?.toString(),
                playerHeight = playerListener.playerHeight()?.toString(),
                videoWidth = playerListener.videoSourceWidth()?.toString(),
                videoHeight = playerListener.videoSourceHeight()?.toString(),
                softwareName = playerListener.getSoftwareName(),
                softwareVersion = playerListener.getSoftwareVersion(),
                osName = deviceInfoUtility.getOSName(),
                osVersion = deviceInfoUtility.getOSVersion(),
                fpSDKName = playerListener.getFastPixSDKName(),
                fpSDKVersion = playerListener.getFastPixSDKVersion(),
                streamType = if (playerListener.isLive() == true) "live" else "on-demand",
                autoPlay = playerListener.isAutoPlay()?.toString(),
                viewSessionId = SessionService.getSessionId(),
                mimeType = playerListener.mimeType()?.lowercase(),
                fastPixApiVersion = "1.0",
                videoCodec = playerListener.playerCodec(),
                videoLanguage = configService.videoData?.videoLanguage,
                videoDuration = playerListener.sourceDuration()?.toString(),
                videoThumbnail = configService.videoData?.videoThumbnail,
                videoSeries = configService.videoData?.videoSeries,
                videoVariant = configService.videoData?.videoVariant,
                videoProducer = configService.videoData?.videoProducer,
                videoContentType = configService.videoData?.videoContentType,
                applicationName = deviceInfoUtility.getBrowser(),
                applicationVersion = deviceInfoUtility.getBrowserVersion(),
                drmType = configService.videoData?.videoDrmType,
                cm1 = configService.customData?.customField1,
                cm2 = configService.customData?.customField2,
                cm3 = configService.customData?.customField3,
                cm4 = configService.customData?.customField4,
                cm5 = configService.customData?.customField5,
                cm6 = configService.customData?.customField6,
                cm7 = configService.customData?.customField7,
                cm8 = configService.customData?.customField8,
                cm9 = configService.customData?.customField9,
                cm10 = configService.customData?.customField10
            )
        }
    }
}
