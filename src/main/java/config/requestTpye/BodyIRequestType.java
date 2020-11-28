package config.requestTpye;

import api.RequestData;
import io.restassured.specification.RequestSpecification;

public class BodyIRequestType implements IRequestType {

    @Override
    public RequestSpecification requestBuild(RequestSpecification specification, RequestData requestData) {
        return specification.body(requestData.getParam());
    }
}
