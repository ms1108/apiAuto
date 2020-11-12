package config.asserts;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static base.BaseData.defaultAssertPath;
import static base.BaseData.defaultAssertValue;
import static org.hamcrest.CoreMatchers.equalTo;

public class DefaultSuccessAssert implements BaseAsserts{
    @Override
    public ValidatableResponse assets(Response response) {
        return response.then().statusCode(200).body(defaultAssertPath, equalTo(defaultAssertValue));
    }
}
