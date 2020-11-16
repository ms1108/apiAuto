package business.user.service_constant;

import base.ApiMethod;
import base.IServiceMap;
import config.header.BaseHeaders;
import config.header.DefaultHeaders;

public enum UserServiceTestCode implements IServiceMap {
    ADD_USER("/user/add", "", "user"),
    USER_LIST("/user/list", "", "list");

    UserServiceTestCode(String uri, String jsonSchemaPath, String des) {
        this(uri, ApiMethod.POST, jsonSchemaPath, des);
    }

    UserServiceTestCode(String uri, String jsonSchemaPath, String des, Class<? extends BaseHeaders> headers) {
        this(uri, ApiMethod.POST, jsonSchemaPath, des, headers);
    }

    UserServiceTestCode(String uri, ApiMethod methodAndRequestType, String jsonSchemaPath, String des) {
        this(uri, methodAndRequestType, jsonSchemaPath, des, DefaultHeaders.class);
    }

    UserServiceTestCode(String uri, ApiMethod methodAndRequestType, String jsonSchemaPath, String des, Class<? extends BaseHeaders> headers) {
        this.uri = uri;
        this.methodAndRequestType = methodAndRequestType;
        this.jsonSchemaPath = jsonSchemaPath;
        this.des = des;
        this.headers = headers;
    }

    private String uri;
    private ApiMethod methodAndRequestType;
    private String jsonSchemaPath;
    private String des;
    private Class<? extends BaseHeaders> headers;


    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public ApiMethod getMethodAndRequestType() {
        return methodAndRequestType;
    }

    @Override
    public Class<? extends BaseHeaders> getHeaders() {
        return headers;
    }

    @Override
    public String getJsonSchemaPath() {
        return jsonSchemaPath;
    }

    @Override
    public String getDes() {
        return des;
    }

}
