package config.asserts;

import api.RequestData;
import base.BaseCase;
import base.IServiceMap;
import io.restassured.response.Response;

/**
 * 调用其他接口进行断言
 */
public class BaseCaseAssert extends AssertMethod {
    private IServiceMap iServiceMap;
    private BaseCase baseCase;
    private AssertMethod assertMethod;

    public BaseCaseAssert(BaseCase baseCase, AssertMethod assertMethod) {
        this.baseCase = baseCase;
        this.assertMethod = assertMethod;
    }

    public BaseCaseAssert(IServiceMap iServiceMap, BaseCase baseCase, AssertMethod assertMethod) {
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
        data.setAssertMethod(assertMethod);
        apiTest(data);

        backCallAssert(requestData, response);
        return this;
    }
}
