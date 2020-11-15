package config.requestTpye;

import api.RequestData;
import io.restassured.specification.RequestSpecification;

public class BodyRequestType implements RequestType {

    @Override
    public RequestSpecification requestBuild(RequestSpecification specification, RequestData requestData) {
        requestData.setParam("{\"c\":{},\"b\":" + requestData.getParam() + "}");
        return specification.body(requestData.getParam());
    }
}
