package config.asserts;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public interface BaseAsserts {
    ValidatableResponse assets(Response response);
}
