package io.fastpix.data.Interfaces;

import java.net.URL;
import java.util.List;
import java.util.Map;


public interface RequestHandler {
    void get(URL url);

    void post(URL url, String type, Map<String, String> hashtable);

    void postWithCompletion(String domain, String workSpaceId, String stringObject, Map<String, String> hashtable, IFPNetworkRequestsCompletion ifpNetworkRequestsCompletion);

    default void postWithCompletion(String domain, String workSpaceId, String stringObject, Map<String, String> hashtable, IFPNetworkRequestsCompletion2 ifpNetworkRequestsCompletion2) {
        this.postWithCompletion(domain, workSpaceId, stringObject, hashtable, dataOfTheResponse -> ifpNetworkRequestsCompletion2.onComplete(dataOfTheResponse, (Map)null));
    }

    public interface IFPNetworkRequestsCompletion2 {
        void onComplete(boolean isComplete, Map<String, List<String>> stringListMap);
    }

    public interface IFPNetworkRequestsCompletion {
        void onComplete(boolean isComplete);
    }
}
