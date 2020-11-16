package business.loginTest.service_constant;

import base.ApiMethod;
import base.IServiceMap;
import config.header.BaseHeaders;
import config.header.DefaultHeaders;

public enum LoginService implements IServiceMap {
    Login("/test", "jsonschema/login/login.json", "business/login"),
    Config("/depend", "", "depend"),
    Upload("/upload", ApiMethod.UPLOAD, "", "upload"),
    List("/list", ApiMethod.GET, "", "list")
    ;


    LoginService(String uri, String jsonSchemaPath, String des) {
        this(uri, ApiMethod.POST, jsonSchemaPath, des);
    }

    LoginService(String uri, String jsonSchemaPath, String des, Class<? extends BaseHeaders> headers) {
        this(uri, ApiMethod.POST, jsonSchemaPath, des, headers);
    }

    LoginService(String uri, ApiMethod methodAndRequestType, String jsonSchemaPath, String des) {
        this(uri, methodAndRequestType, jsonSchemaPath, des, DefaultHeaders.class);
    }

    LoginService(String uri, ApiMethod methodAndRequestType, String jsonSchemaPath, String des, Class<? extends BaseHeaders> headers) {
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
