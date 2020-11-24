package config.asserts;

import api.RequestData;
import base.BaseCase;
import base.IServiceMap;
import io.restassured.response.Response;

/**
 * 调用其他接口进行断言
 */
public class ByOtherApiAssert extends AssertMethod {
    private IServiceMap iServiceMap;
    private BaseCase baseCase;
    private AssertMethod assertMethod;

    public ByOtherApiAssert(BaseCase baseCase, AssertMethod assertMethod) {
        this.baseCase = baseCase;
        this.assertMethod = assertMethod;
    }

    public ByOtherApiAssert(IServiceMap iServiceMap, BaseCase baseCase, AssertMethod assertMethod) {
        this.iServiceMap = iServiceMap;
        this.baseCase = baseCase;
        this.assertMethod = assertMethod;
    }

    @Override
    public AssertMethod assets(RequestData requestData, Response response) {
        RequestData data;
        if (iServiceMap == null) {
            data = new RequestData(baseCase);
        } else {
            data = new RequestData(iServiceMap, baseCase);
        }
        //调用其他接口进行断言
        data.setAssertMethod(assertMethod);
        Response otherResponse = apiTest(data);

        backCallAssert(data, otherResponse);
        return this;
    }
}
