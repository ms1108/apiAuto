package config.header;

import base.BaseData;

import java.util.HashMap;
import java.util.Map;


public class DefaultHeaders implements BaseHeaders {
    @Override
    public Map<String, Object> getBaseHeaders() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", BaseData.token);
        //TODO 默认请求头
        return map;
    }
}
