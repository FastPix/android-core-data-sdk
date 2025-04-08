package io.fastpix.data.monitor;

import org.json.JSONException;

import io.fastpix.data.Interfaces.EventEmitter;
import io.fastpix.data.streaming.StreamingHub;
import io.fastpix.data.entity.NetworkBandwidthEntity;
import io.fastpix.data.entity.ViewDataEntity;
import io.fastpix.data.streaming.MediaStreaming;

/**
 * RequestMetricsTracker is responsible for tracking metrics related to requests (e.g., network requests, API calls).
 * It extends BaseTracker, inheriting core tracking functionality for request-based events.
 */
public class RequestMetricsMonitor extends AbstractMonitor {
    protected long requestCount = 0L;
    protected long requestCompletedCount = 0L;
    protected long requestCanceledCount = 0L;
    protected long requestFailedCount = 0L;
    protected long totalBytes = 0L;
    protected long totalLoadTime = 0L;
    protected double totalLatency = 0.0;

    /**
     * Constructor for RequestMetricsTracker.
     *
     * @param eventEmitter The event dispatcher responsible for handling and dispatching request-related events.
     *                         This allows RequestMetricsTracker to manage and dispatch request metrics.
     */
    public RequestMetricsMonitor(EventEmitter eventEmitter) {
        super(eventEmitter);
    }

    /**
     * Handles playback events related to request metrics and updates relevant counters and view data.
     *
     * @param streamingHub The playback event to process.
     * @throws JSONException If there is an error handling JSON data.
     */
    protected void handlePlaybackEvent(StreamingHub streamingHub) throws JSONException {
        switch (streamingHub.getType()) {
            case "requestCompleted":
                MediaStreaming requestCompleted = (MediaStreaming) streamingHub;
                ++this.requestCount;
                long timeLong = 0L;
                NetworkBandwidthEntity networkBandwidthEntity;
                if ((networkBandwidthEntity = requestCompleted.getBandwidthMetricData()) != null) {
                    if (networkBandwidthEntity.getRequestStart() != null) {
                        timeLong = networkBandwidthEntity.getRequestResponseStart() - networkBandwidthEntity.getRequestStart();
                    }
                    long timeOfLong;
                    if ((timeOfLong = networkBandwidthEntity.getRequestResponseEnd() - networkBandwidthEntity.getRequestResponseStart()) > 0L && networkBandwidthEntity.getRequestBytesLoaded() != null && networkBandwidthEntity.getRequestBytesLoaded() > 0L) {
                        long updateTime = networkBandwidthEntity.getRequestBytesLoaded() / timeOfLong * 8000L;
                        ++this.requestCompletedCount;
                        this.totalBytes += networkBandwidthEntity.getRequestBytesLoaded();
                        this.totalLoadTime += timeOfLong;
                        ViewDataEntity viewData1= requestCompleted.getViewData();
                        (viewData1 ).setViewMinRequestThroughput(Math.min(viewData1.getViewMinRequestThroughput() == null ? Long.MAX_VALUE : viewData1.getViewMinRequestThroughput(), updateTime));
                        if (this.totalLoadTime > 0) {
                            long throughput = (this.totalBytes * 8000L) / this.totalLoadTime;
                            viewData1.setViewAverageRequestThroughput(throughput);
                        } else {
                            viewData1.setViewAverageRequestThroughput(Long.valueOf(0)); // Handle edge case
                        }
                        viewData1.setViewRequestCount(this.requestCount);
                        if (timeLong > 0L) {
                            this.totalLatency += timeLong;
                            viewData1.setViewMaxRequestLatency(Math.max(viewData1.getViewMaxRequestLatency() == null ? 0.0 : viewData1.getViewMaxRequestLatency(), timeLong));
                            viewData1.setViewAverageRequestLatency(this.totalLatency / this.requestCompletedCount);
                        }
                        requestCompleted.setViewData(viewData1);
                    }
                }
                return;
            case "requestFailed":
                MediaStreaming requestFailed = (MediaStreaming) streamingHub;
                ++this.requestCount;
                ++this.requestFailedCount;
                ViewDataEntity viewData = requestFailed.getViewData();
                viewData.setViewRequestCount(this.requestCount);
                viewData.setViewRequestFailedCount(this.requestFailedCount);
                requestFailed.setViewData(viewData);
                return;
            case "requestCanceled":
                MediaStreaming requestCanceled = (MediaStreaming) streamingHub;
                ++this.requestCount;
                ++this.requestCanceledCount;
                ViewDataEntity viewData2 = requestCanceled.getViewData();
                viewData2.setViewRequestCount(this.requestCount);
                viewData2.setViewRequestCanceledCount(this.requestCanceledCount);
                requestCanceled.setViewData(viewData2);
                return;
            default:
                break;
        }
    }
}
