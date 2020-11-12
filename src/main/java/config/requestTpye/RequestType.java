package config.requestTpye;

import api.RequestData;
import io.restassured.specification.RequestSpecification;

public interface RequestType {
    RequestSpecification requestBuild(RequestSpecification specification,RequestData requestData);
}
