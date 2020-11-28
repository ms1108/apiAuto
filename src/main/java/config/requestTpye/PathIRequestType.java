package config.requestTpye;

import api.RequestData;
import com.alibaba.fastjson.JSONObject;
import io.restassured.specification.RequestSpecification;

public class PathIRequestType implements IRequestType {

    @Override
    public RequestSpecification requestBuild(RequestSpecification specification, RequestData requestData) {
        return specification.pathParams(JSONObject.parseObject(requestData.getParam()));
    }
}
