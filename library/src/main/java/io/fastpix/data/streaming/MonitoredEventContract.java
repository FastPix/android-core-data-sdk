package io.fastpix.data.streaming;

import io.fastpix.data.entity.NetworkBandwidthEntity;
import io.fastpix.data.entity.QueryDataEntity;
import io.fastpix.data.entity.CustomDataEntity;
import io.fastpix.data.entity.CustomerPlayerDataEntity;
import io.fastpix.data.entity.CustomerVideoDataEntity;
import io.fastpix.data.entity.CustomerViewDataEntity;
import io.fastpix.data.entity.CustomerViewerDataEntity;
import io.fastpix.data.entity.WorkSpaceEntity;
import io.fastpix.data.entity.PlayerDataEntity;
import io.fastpix.data.entity.VideoDataEntity;
import io.fastpix.data.entity.ViewDataEntity;
import io.fastpix.data.entity.ViewerDataEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MonitoredEventContract extends AbstractEventContract {
    public static final String TYPE = "trackableEvent";
    private final String localEventType;
    private final QueryDataEntity queryDataEntity;
    private ViewDataEntity viewData;
    private VideoDataEntity videoData;
    private CustomerVideoDataEntity customerVideoData;
    private PlayerDataEntity playerData;
    private CustomerPlayerDataEntity customerPlayerData;
    private WorkSpaceEntity environmentData;
    private ViewerDataEntity viewerData;
    private NetworkBandwidthEntity networkBandwidthEntity;
    private CustomerViewDataEntity customerViewData;
    private CustomerViewerDataEntity customerViewerData;
    private CustomDataEntity customData;

    public String getType() {
        return TYPE;
    }

    public MonitoredEventContract(String eventType) {
        this.localEventType = eventType;
        this.queryDataEntity = new QueryDataEntity() {
            public final void sync() throws JSONException {
                Map<List<String>, JSONObject> jsonObjectMap = new HashMap<>();

                // Initialize JSON objects for each data type
                jsonObjectMap.put(ViewDataEntity.keysViewData, new JSONObject());
                jsonObjectMap.put(VideoDataEntity.keysVData, new JSONObject());
                jsonObjectMap.put(CustomerVideoDataEntity.keysCVD, new JSONObject());
                jsonObjectMap.put(PlayerDataEntity.PDD, new JSONObject());
                jsonObjectMap.put(CustomerPlayerDataEntity.keysCPD, new JSONObject());
                jsonObjectMap.put(WorkSpaceEntity.keysEV, new JSONObject());
                jsonObjectMap.put(ViewerDataEntity.KEYS_VIEWER_DATA, new JSONObject());
                jsonObjectMap.put(NetworkBandwidthEntity.KEYS_BWMD, new JSONObject());
                jsonObjectMap.put(CustomerViewDataEntity.keysCCVD, new JSONObject());
                jsonObjectMap.put(CustomDataEntity.keysCD, new JSONObject());
                jsonObjectMap.put(CustomerViewerDataEntity.keysCVVD, new JSONObject());

                JSONArray jsonArray = this.keys();

                for (int i = 0; i < jsonArray.length(); ++i) {
                    String jsonArrayString = jsonArray.getString(i);

                    for (Map.Entry<List<String>, JSONObject> entry : jsonObjectMap.entrySet()) {
                        if (entry.getKey().contains(jsonArrayString)) {
                            entry.getValue().put(jsonArrayString, this.jsonObject.get(jsonArrayString));
                        }
                    }
                }

                // Replace data only if it's not null
                updateDataIfNotNull(jsonObjectMap);
            }
        };
    }

    private void updateDataIfNotNull(Map<List<String>, JSONObject> jsonObjectMap) throws JSONException {
        if (MonitoredEventContract.this.viewData != null)
            MonitoredEventContract.this.viewData.replace(jsonObjectMap.get(ViewDataEntity.keysViewData));

        if (MonitoredEventContract.this.videoData != null)
            MonitoredEventContract.this.videoData.replace(jsonObjectMap.get(VideoDataEntity.keysVData));

        if (MonitoredEventContract.this.customerVideoData != null)
            MonitoredEventContract.this.customerVideoData.replace(jsonObjectMap.get(CustomerVideoDataEntity.keysCVD));

        if (MonitoredEventContract.this.playerData != null)
            MonitoredEventContract.this.playerData.replace(jsonObjectMap.get(PlayerDataEntity.PDD));

        if (MonitoredEventContract.this.customerPlayerData != null)
            MonitoredEventContract.this.customerPlayerData.replace(jsonObjectMap.get(CustomerPlayerDataEntity.keysCPD));

        if (MonitoredEventContract.this.environmentData != null)
            MonitoredEventContract.this.environmentData.replace(jsonObjectMap.get(WorkSpaceEntity.keysEV));

        if (MonitoredEventContract.this.viewerData != null)
            MonitoredEventContract.this.viewerData.replace(jsonObjectMap.get(ViewerDataEntity.KEYS_VIEWER_DATA));

        if (MonitoredEventContract.this.networkBandwidthEntity != null)
            MonitoredEventContract.this.networkBandwidthEntity.replace(jsonObjectMap.get(NetworkBandwidthEntity.KEYS_BWMD));

        if (MonitoredEventContract.this.customerViewData != null)
            MonitoredEventContract.this.customerViewData.replace(jsonObjectMap.get(CustomerViewDataEntity.keysCCVD));

        if (MonitoredEventContract.this.customerViewerData != null)
            MonitoredEventContract.this.customerViewerData.replace(jsonObjectMap.get(CustomerViewerDataEntity.keysCVVD));

        if (MonitoredEventContract.this.customData != null)
            MonitoredEventContract.this.customData.replace(jsonObjectMap.get(CustomDataEntity.keysCD));
    }

    public boolean isTrackable() {
        return true;
    }

    public String getEventType() {
        return this.localEventType;
    }

    public QueryDataEntity getQuery() {
        return this.queryDataEntity;
    }

    public Integer getViewSequenceNumber() {
        String viewSequenceNumber= this.queryDataEntity.get("vesqnu");
        return viewSequenceNumber == null ? null : Integer.parseInt(viewSequenceNumber);
    }

    private void aClass(QueryDataEntity queryDataEntity) throws JSONException {
        this.queryDataEntity.update(queryDataEntity);
    }

    public ViewDataEntity getViewData() throws JSONException {
        ViewDataEntity viewData1= new ViewDataEntity();
        viewData1.update(this.viewData);
        return viewData1;
    }

    public void setViewData(ViewDataEntity viewData) throws JSONException {
        ViewDataEntity viewData1= new ViewDataEntity();
        viewData1.update(viewData);
        this.aClass(viewData1);
        this.viewData = viewData1;
    }

    public VideoDataEntity getVideoData() throws JSONException {
        VideoDataEntity videoData1= new VideoDataEntity();
        videoData1.update(this.videoData);
        return videoData1;
    }

    public void setVideoData(VideoDataEntity videoData) throws JSONException {
        VideoDataEntity videoData1= new VideoDataEntity();
        videoData1.update(videoData);
        this.aClass(videoData1);
        this.videoData = videoData1;
    }

    public CustomerVideoDataEntity getCustomerVideoData() throws JSONException {
        CustomerVideoDataEntity customerVideoData1= new CustomerVideoDataEntity();
        customerVideoData1.update(this.customerVideoData);
        return customerVideoData1;
    }

    public void setCustomerVideoData(CustomerVideoDataEntity customerVideoData) throws JSONException {
        CustomerVideoDataEntity customerVideoData1= new CustomerVideoDataEntity();
        customerVideoData1.update(customerVideoData);
        this.aClass(customerVideoData1);
        this.customerVideoData = customerVideoData1;
    }

    public PlayerDataEntity getPlayerData() throws JSONException {
        PlayerDataEntity playerData1= new PlayerDataEntity();
        playerData1.update(this.playerData);
        return playerData1;
    }

    public void setPlayerData(PlayerDataEntity playerData) throws JSONException {
        PlayerDataEntity playerData1= new PlayerDataEntity();
        playerData1.update(playerData);
        this.aClass(playerData1);
        this.playerData = playerData1;
    }

    public CustomerPlayerDataEntity getCustomerPlayerData() throws JSONException {
        CustomerPlayerDataEntity customerPlayerData1= new CustomerPlayerDataEntity();
        customerPlayerData1.update(this.customerPlayerData);
        return customerPlayerData1;
    }

    public void setCustomerPlayerData(CustomerPlayerDataEntity customerPlayerData) throws JSONException {
        CustomerPlayerDataEntity customerPlayerData1= new CustomerPlayerDataEntity();
        customerPlayerData1.update(customerPlayerData);
        this.aClass(customerPlayerData1);
        this.customerPlayerData = customerPlayerData1;
    }

    public WorkSpaceEntity getEnvironmentData() throws JSONException {
        WorkSpaceEntity environmentData1= new WorkSpaceEntity();
        environmentData1.update(this.environmentData);
        return environmentData1;
    }

    public void setEnvironmentData(WorkSpaceEntity environmentData) throws JSONException {
        WorkSpaceEntity environmentData1= new WorkSpaceEntity();
        environmentData1.update(environmentData);
        this.aClass(environmentData);
        this.environmentData = environmentData1;
    }

    public ViewerDataEntity getViewerData() throws JSONException {
        ViewerDataEntity viewerData1= new ViewerDataEntity();
        viewerData1.update(this.viewerData);
        return viewerData1;
    }

    public void setViewerData(ViewerDataEntity viewerData) throws JSONException {
        ViewerDataEntity viewerData1= new ViewerDataEntity();
        viewerData1.update(viewerData);
        this.aClass(viewerData1);
        this.viewerData = viewerData1;
    }

    public NetworkBandwidthEntity getBandwidthMetricData() throws JSONException {
        NetworkBandwidthEntity networkBandwidthEntity1 = new NetworkBandwidthEntity();
        networkBandwidthEntity1.update(this.networkBandwidthEntity);
        return networkBandwidthEntity1;
    }

    public void setBandwidthMetricData(NetworkBandwidthEntity networkBandwidthEntity) throws JSONException {
        NetworkBandwidthEntity networkBandwidthEntity1 = new NetworkBandwidthEntity();
        networkBandwidthEntity1.update(networkBandwidthEntity);
        this.aClass(networkBandwidthEntity1);
        this.networkBandwidthEntity = networkBandwidthEntity1;
    }

    public CustomerViewDataEntity getCustomerViewData() throws JSONException {
        CustomerViewDataEntity customerViewData1= new CustomerViewDataEntity();
        customerViewData1.update(this.customerViewData);
        return customerViewData1;
    }

    public void setCustomerViewData(CustomerViewDataEntity customerViewData)  throws JSONException {
        CustomerViewDataEntity customerViewData1= new CustomerViewDataEntity();
        customerViewData1.update(customerViewData);
        this.aClass(customerViewData1);
        this.customerViewData = customerViewData1;
    }

    public CustomerViewerDataEntity getCustomerViewerData() throws JSONException {
        CustomerViewerDataEntity customerViewerData1= new CustomerViewerDataEntity();
        customerViewerData1.update(this.customerViewerData);
        return customerViewerData1;
    }

    public void setCustomerViewerData(CustomerViewerDataEntity customerViewerData)  throws JSONException {
        CustomerViewerDataEntity customerViewerData1= new CustomerViewerDataEntity();
        customerViewerData1.update(customerViewerData);
        this.aClass(customerViewerData1);
        this.customerViewerData = customerViewerData1;
    }

    public CustomDataEntity getCustomData() throws JSONException {
        CustomDataEntity customData1= new CustomDataEntity();
        customData1.update(this.customData);
        return customData1;
    }

    public void setCustomData(CustomDataEntity customData)  throws JSONException {
        CustomDataEntity customData1= new CustomDataEntity();
        customData1.update(customData);
        this.aClass(customData1);
        this.customData = customData1;
    }

    public String toString() {
        return "TrackableEvent<" + this.localEventType + ", " + this.queryDataEntity.toString() + ">";
    }

}
