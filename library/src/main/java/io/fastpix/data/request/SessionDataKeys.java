package io.fastpix.data.request;

import android.util.Log;
import io.fastpix.data.entity.QueryDataEntity;
import io.fastpix.data.entity.CustomDataEntity;
import io.fastpix.data.entity.CustomerPlayerDataEntity;
import io.fastpix.data.entity.CustomerVideoDataEntity;
import io.fastpix.data.entity.CustomerViewDataEntity;
import com.google.common.collect.ImmutableMap;
import java.util.Objects;

public class SessionDataKeys {
    private static final ImmutableMap<String, MyClassA> IMMUTABLE_MAP = (new ImmutableMap.Builder()).put("internal_view_session_id", new MyClassA("ilvesnid", CustomerViewDataEntity.class)).put("internal_video_experiments", new MyClassA("ilvdes", CustomerViewDataEntity.class)).put("video_experiments", new MyClassA("vdes", CustomerVideoDataEntity.class)).put("video_id", new MyClassA("vdid", CustomerVideoDataEntity.class)).put("video_title", new MyClassA("vdtt", CustomerVideoDataEntity.class)).put("video_cdn", new MyClassA("cn", CustomerVideoDataEntity.class)).put("video_content_type", new MyClassA("vdctty", CustomerVideoDataEntity.class)).put("video_duration", new MyClassA("vddu", CustomerVideoDataEntity.class)).put("video_encoding_variant", new MyClassA("vdeova", CustomerVideoDataEntity.class)).put("video_is_live", new MyClassA("vdisli", CustomerVideoDataEntity.class)).put("video_language_code", new MyClassA("vdlncd", CustomerVideoDataEntity.class)).put("video_producer", new MyClassA("vdpd", CustomerVideoDataEntity.class)).put("video_series", new MyClassA("vdsr", CustomerVideoDataEntity.class)).put("video_stream_type", new MyClassA("vdsmty", CustomerVideoDataEntity.class)).put("video_variant_id", new MyClassA("vdvaid", CustomerVideoDataEntity.class)).put("video_variant_name", new MyClassA("vdvana", CustomerVideoDataEntity.class)).put("video_source_url", new MyClassA("vdsour", CustomerVideoDataEntity.class)).put("viewer_user_id", new MyClassA("viusid", CustomerPlayerDataEntity.class)).put("experiment_name", new MyClassA("exna", CustomerPlayerDataEntity.class)).put("view_session_id", new MyClassA("vesnid", CustomerViewDataEntity.class)).put("custom_1", new MyClassA("cm1", CustomDataEntity.class)).put("custom_2", new MyClassA("cm2", CustomDataEntity.class)).put("custom_3", new MyClassA("cm3", CustomDataEntity.class)).put("custom_4", new MyClassA("cm4", CustomDataEntity.class)).put("custom_5", new MyClassA("cm5", CustomDataEntity.class)).build();

    private SessionDataKeys() {
        Log.e("SessionDataKeys", "no instances");
    }

    public static String shortCode(String stCode) {
        return Objects.requireNonNull(IMMUTABLE_MAP.get(stCode)).stMessage;
    }

    public static Class<? extends QueryDataEntity> type(String typeQueryData) {
        return Objects.requireNonNull(IMMUTABLE_MAP.get(typeQueryData)).aClass;
    }

    private static final class MyClassA {
        public final String stMessage;
        public final Class<? extends QueryDataEntity> aClass;

        public MyClassA(String message, Class<? extends QueryDataEntity> localAClass) {
            this.stMessage = message;
            this.aClass = localAClass;
        }
    }
}
