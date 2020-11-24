package config.header;

import api.RequestData;

import java.util.Map;

public interface BaseHeaders {
    Map<String,Object> getHeaders(RequestData requestData);
}
