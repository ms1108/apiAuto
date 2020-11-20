package businessWf.loginWF.serviceconstant;

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


    LoginWFService(String uri, ApiMethod methodAndRequestType, String jsonSchemaPath, String des) {
        this.uri = uri;
        this.methodAndRequestType = methodAndRequestType;
        this.jsonSchemaPath = jsonSchemaPath;
        this.des = des;
    }

    private String uri;
    private ApiMethod methodAndRequestType;
    private String jsonSchemaPath;
    private String des;


    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public ApiMethod getMethodAndRequestType() {
        return methodAndRequestType;
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
