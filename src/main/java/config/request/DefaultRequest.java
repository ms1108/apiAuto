package config.request;

import api.RequestData;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class DefaultRequest implements IRequest {
    @Override
    public Response requestMethod(RequestSpecification specification, RequestData requestData) {
        return specification.request(requestData.getMethodAndRequestType().getApiMethod(), requestData.getUri());
    }
}
