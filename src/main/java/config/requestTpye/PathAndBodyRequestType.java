package config.requestTpye;

import api.RequestData;
import com.alibaba.fastjson.JSONObject;
import io.restassured.specification.RequestSpecification;

public class PathAndBodyRequestType extends PathIRequestType {
    public RequestSpecification requestBuild(RequestSpecification specification, RequestData requestData) {
        RequestSpecification requestSpecification = super.requestBuild(specification, requestData);
        return requestSpecification.body(JSONObject.parseObject(requestData.getParam()));
    }
}