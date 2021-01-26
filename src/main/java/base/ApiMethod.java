package base;

import config.requestTpye.*;
import lombok.SneakyThrows;

public enum ApiMethod {
    POST("post", BodyIRequestType.class),
    POST_FORM("post", FormIRequestType.class),
    GET("get", QueryIRequestType.class),
    GET_PATH("get", PathIRequestType.class),
    POST_PATH_BODY("post", PathAndBodyRequestType.class),
    UPLOAD("post", UploadIRequestType.class);

    private String apiMethod;
    private Class<? extends IRequestType> requestType;

    ApiMethod(String method, Class<? extends IRequestType> requestType) {
        this.apiMethod = method;
        this.requestType = requestType;
    }

    public String getApiMethod() {
        return apiMethod;
    }

    @SneakyThrows
    public IRequestType getRequestType() {
        return requestType.newInstance();
    }
}
