package businessWf.user.service;

import base.ApiMethod;
import base.IServiceMap;
import config.header.BaseHeaders;
import config.header.DefaultHeaders;

public enum UserService implements IServiceMap {
    DOWNLOAD_USER("/1.0/userService/exportUsers", "", "下载"),
    DOWNLOAD("/1.0/fileService/download/{pathParam}", ApiMethod.GET_PATH, "", "下载文件"),
    Upload_USER("/1.0/userService/uploadForImport", ApiMethod.UPLOAD, "", "上传"),
    AddUser("/1.0/userService/addUser", "", "新增用户"),
    ;

    UserService(String uri, String jsonSchemaPath, String des) {
        this(uri, ApiMethod.POST, jsonSchemaPath, des);
    }

    UserService(String uri, String jsonSchemaPath, String des, Class<? extends BaseHeaders> headers) {
        this(uri, ApiMethod.POST, jsonSchemaPath, des, headers);
    }

    UserService(String uri, ApiMethod methodAndRequestType, String jsonSchemaPath, String des) {
        this(uri, methodAndRequestType, jsonSchemaPath, des, DefaultHeaders.class);
    }

    UserService(String uri, ApiMethod methodAndRequestType, String jsonSchemaPath, String des, Class<? extends BaseHeaders> headers) {
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
