package config.asserts;

import api.RequestData;
import io.restassured.response.Response;

public class SuccessAssert extends AssertMethod {

    private AssertMethod assertMethod;

    public SuccessAssert(AssertMethod... assertMethods) {
        assertGather(new SuccessAssertDefault(), assertMethods);
    }

    public SuccessAssert(SuccessAssertDefault successAssertDefault, AssertMethod... assertMethods) {
        assertGather(successAssertDefault, assertMethods);
    }

    private void assertGather(SuccessAssertDefault successAssertDefault, AssertMethod... assertMethods) {
        AssertMethod assertMethodsTmp = successAssertDefault;
        for (AssertMethod assertMethod : assertMethods) {
            assertMethodsTmp.setAssert(assertMethod);
            assertMethodsTmp = assertMethod;
        }
        this.assertMethod = successAssertDefault;
    }

    @Override
    public AssertMethod assets(RequestData requestData, Response response) {
        assertMethod.assets(requestData, response);
        return this;
    }
}
