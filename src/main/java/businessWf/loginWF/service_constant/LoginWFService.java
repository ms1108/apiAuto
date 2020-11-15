package businessWf.loginWF.service_constant;

import base.ApiMethod;
import base.IServiceMap;
import config.header.BaseHeaders;
import config.header.DefaultHeaders;

public enum LoginWFService implements IServiceMap {
    LoginWF("/1.0/authService/login", "", "登陆防水堡"),
    Config("/1.0/authService/config", "", "登录页面配置信息"),
    ;

    LoginWFService(String uri, String jsonSchemaPath, String des) {
        this(uri, ApiMethod.POST, jsonSchemaPath, des);
    }

    LoginWFService(String uri, String jsonSchemaPath, String des, Class<? extends BaseHeaders> headers) {
        this(uri, ApiMethod.POST, jsonSchemaPath, des, headers);
    }

    LoginWFService(String uri, ApiMethod methodAndRequestType, String jsonSchemaPath, String des) {
        this(uri, methodAndRequestType, jsonSchemaPath, des, DefaultHeaders.class);
    }

    LoginWFService(String uri, ApiMethod methodAndRequestType, String jsonSchemaPath, String des, Class<? extends BaseHeaders> headers) {
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
