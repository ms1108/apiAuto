package business.user.service_constant;

import base.ApiMethod;
import base.IServiceMap;
import config.header.BaseHeaders;
import config.header.DefaultHeaders;

import java.util.Map;

public enum UserService implements IServiceMap {
    ADD_USER("/user/add", "user"),
    USER_LIST("/user/list", "list")
    ;

    UserService(String uri, String des) {
        this(uri, ApiMethod.POST, des);
    }

    UserService(String uri, String des, Class<? extends BaseHeaders> headers) {
        this(uri, ApiMethod.POST, des, headers);
    }

    UserService(String uri, ApiMethod methodAndRequestType, String des) {
        this(uri, methodAndRequestType, des,DefaultHeaders.class);
    }

    UserService(String uri, ApiMethod methodAndRequestType, String des, Class<? extends BaseHeaders> headers) {
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
    public  Class<? extends BaseHeaders>  getHeaders() {
        return headers;
    }

    @Override
    public String getDes() {
        return des;
    }

}
