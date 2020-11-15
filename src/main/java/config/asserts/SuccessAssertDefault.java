package config.asserts;

import api.RequestData;
import base.BaseData;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import utils.StringUtil;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.equalTo;

public class SuccessAssertDefault extends AssertMethod {
    private String assertPath = BaseData.defaultAssertPath;
    private Object assertValue = BaseData.defaultAssertValue;

    public SuccessAssertDefault() {
    }

    public SuccessAssertDefault(String assertPath, Object assertValue) {
        this.assertPath = assertPath;
        this.assertValue = assertValue;
    }

    @Override
    public AssertMethod assets(RequestData requestData, Response response) {
        ValidatableResponse validatableResponse = response.then().statusCode(200).body(assertPath, equalTo(assertValue));
        if (StringUtil.isNotEmpty(requestData.getJsonSchemaPath())){
            validatableResponse.body(matchesJsonSchemaInClasspath(requestData.getJsonSchemaPath()));
        }

        //调用其他断言
        AssertMethod assertMethod = getAssertMethod();
        if (assertMethod != null) {
            assertMethod.assets(requestData, response);
        }
        return this;
    }
}
