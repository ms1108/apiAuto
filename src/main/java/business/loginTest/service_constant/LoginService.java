package business.loginTest.service_constant;

import base.*;
import config.header.BaseHeaders;
import config.header.DefaultHeaders;

import java.util.Map;

public enum LoginService implements IServiceMap {
    Login("/login", "login"),
    Config("/depend", "depend"),
    Upload("/upload", ApiMethod.UPLOAD, "upload");

    LoginService(String uri, String des) {
        this(uri, ApiMethod.POST, des);
    }

    LoginService(String uri, String des, Class<? extends BaseHeaders> headers) {
        this(uri, ApiMethod.POST, des, headers);
    }

    LoginService(String uri, ApiMethod methodAndRequestType, String des) {
        this(uri, methodAndRequestType, des, DefaultHeaders.class);
    }

    LoginService(String uri, ApiMethod methodAndRequestType, String des, Class<? extends BaseHeaders> headers) {
        this.uri = uri;
        this.methodAndRequestType = methodAndRequestType;
        this.des = des;
        this.headers = headers;
    }

    private String uri;
    private ApiMethod methodAndRequestType;
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
    public String getDes() {
        return des;
    }

}
