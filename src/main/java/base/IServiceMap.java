package base;

import config.header.BaseHeaders;

import java.util.Map;

public interface IServiceMap {
    String getUri();

    ApiMethod getMethodAndRequestType();

    String getJsonSchemaPath();

    String getDes();

}
