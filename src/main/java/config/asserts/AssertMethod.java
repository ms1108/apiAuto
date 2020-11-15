package config.asserts;

import api.ApiTest;
import api.RequestData;
import io.restassured.response.Response;

public abstract class AssertMethod extends ApiTest {
    public AssertMethod assertMethod;

    public AssertMethod setAssert(AssertMethod assertMethod) {
        this.assertMethod = assertMethod;
        return this;
    }

    public AssertMethod getAssertMethod() {
        return assertMethod;
    }

    public abstract AssertMethod assets(RequestData requestData, Response response);
}
