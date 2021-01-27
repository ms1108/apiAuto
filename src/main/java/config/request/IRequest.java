package config.request;

import api.RequestData;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public interface IRequest {
    Response requestMethod(RequestSpecification specification, RequestData requestData);
}
