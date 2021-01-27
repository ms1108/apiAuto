package config.paramMethod;

import api.RequestData;
import io.restassured.specification.RequestSpecification;

public class PathIParamMethod implements IParamMethod {

    @Override
    public RequestSpecification paramMethodBuild(RequestSpecification specification, RequestData requestData) {
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