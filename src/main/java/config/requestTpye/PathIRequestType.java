package config.requestTpye;

import api.RequestData;
import com.alibaba.fastjson.JSONObject;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class PathIRequestType implements IRequestType {

    @Override
    public RequestSpecification requestBuild(RequestSpecification specification, RequestData requestData) {
        String uri = requestData.getUri();
        String pathParam = requestData.getBaseParam().pathParam;
        if (uri.endsWith("/")) {
            uri = uri.substring(0, uri.length() - 1);
        }
        if (pathParam.startsWith("/")) {
            pathParam = pathParam.substring(0, uri.length() - 1);
        }
        requestData.setUri(uri + "/" + pathParam);
        return specification;
    }
}
