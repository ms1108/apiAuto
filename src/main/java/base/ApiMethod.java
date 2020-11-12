package base;

import config.requestTpye.*;
import lombok.SneakyThrows;

public enum ApiMethod {
    POST("post", BodyRequestType.class),
    POST_FORM("post", FormRequestType.class),
    GET("get", QueryRequestType.class),
    GET_PATH("get", PathRequestType.class),
    UPLOAD("post", UploadRequestType.class);

    private String apiMethod;
    private Class<? extends RequestType> requestType;

    ApiMethod(String method, Class<? extends RequestType> requestType) {
        this.apiMethod = method;
        this.requestType = requestType;
    }

    public String getApiMethod() {
        return apiMethod;
    }

    @SneakyThrows
    public RequestType getRequestType() {
        return requestType.newInstance();
    }
}
