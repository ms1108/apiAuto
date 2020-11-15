package config.asserts;

import api.RequestData;
import io.restassured.response.Response;

/**
 * 断言继承，调用了父类断言
 */
public class SuccessAssertDownloadUser extends SuccessAssertDownloadFile{
    public AssertMethod assets(RequestData requestData, Response response) {
        super.assets(requestData, response);
        System.out.println("断言继承");
        return this;
    }
}
