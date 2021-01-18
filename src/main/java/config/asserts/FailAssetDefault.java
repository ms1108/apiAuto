package config.asserts;

import api.RequestData;
import base.BaseData;
import io.restassured.response.Response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

public class FailAssetDefault extends AssertMethod {
    private String assertPath = BaseData.defaultAssertPath;
    private Object assertValue = BaseData.defaultAssertValue;
    private int statusCode = 200;

    public FailAssetDefault() {
    }

    public FailAssetDefault(int statusCode) {
        this.statusCode = statusCode;
    }

    public FailAssetDefault(String assertPath, Object assertValue) {
        this.assertPath = assertPath;
        this.assertValue = assertValue;
    }

    @Override
    public AssertMethod assets(RequestData requestData, Response response) {
        if (statusCode == 200) {
            response.then().body(assertPath, not(equalTo(assertValue)));
        }else {
            response.then().statusCode(statusCode);
        }
        backCallAssert(requestData, response);
        return this;
    }
}
