package config.asserts;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static base.BaseData.defaultAssertPath;
import static base.BaseData.defaultAssertValue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

public class DefaultFailAsset implements BaseAsserts {
    @Override
    public ValidatableResponse assets(Response response) {
        ValidatableResponse body = null;
        if (response.statusCode() == 200) {
            body = response.then().body(defaultAssertPath, not(equalTo(defaultAssertValue)));
        }
        return body;
    }
}
