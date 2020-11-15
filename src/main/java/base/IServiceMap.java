package base;

import config.header.BaseHeaders;

import java.util.Map;

public interface IServiceMap {
    String getUri();

    ApiMethod getMethodAndRequestType();

    Class<? extends BaseHeaders>  getHeaders();

    String getJsonSchemaPath();

    String getDes();

}
