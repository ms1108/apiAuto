package config.requestTpye;

import api.RequestData;
import io.restassured.specification.RequestSpecification;

public class QueryRequestType implements RequestType {
    @Override
    public RequestSpecification requestBuild(RequestSpecification specification, RequestData requestData) {
        return specification.queryParam(requestData.getParam());
    }
}
