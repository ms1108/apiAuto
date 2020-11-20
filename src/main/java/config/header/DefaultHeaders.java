package config.header;

import base.BaseData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static utils.PropertiesUtil.get;

public class DefaultHeaders implements BaseHeaders {
    @Override
    public Map<String, Object> getHeaders() {
        Map<String, Object> map = new HashMap<>();
        map.put("content-type", "application/json");
        map.put("referer", get("g_host"));
        map.put("nonce", UUID.randomUUID().toString());
        map.put("timestamp", System.currentTimeMillis());
        map.put("cookie", "YAB_AUTH_TOKEN="+BaseData.token);
        return map;
    }
}
