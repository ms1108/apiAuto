package config.header;

import api.RequestData;
import base.BaseData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static utils.PropertiesUtil.get;

public class DefaultHeaders implements IHeaders {
    @Override
    public Map<String, Object> getHeaders(RequestData requestData) {
        Map<String, Object> map = new HashMap<>();
        map.put("content-type", "application/json");
        map.put("referer", get("g_host"));
        map.put("nonce", UUID.randomUUID().toString());
        map.put("timestamp", System.currentTimeMillis());
        map.put("cookie", "YAB_AUTH_TOKEN="+BaseData.token);
        return map;
    }
}
