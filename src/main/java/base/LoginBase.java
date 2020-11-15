package base;

import annotation.AnnotationTest;
import api.RequestData;
import businessWf.loginWF.logincase.LoginCase;
import io.restassured.response.Response;

public class LoginBase extends AnnotationTest {
    public Response loginAdmin() {
        LoginCase loginCase = new LoginCase().loginCase();
        BaseData.currentLoginName = loginCase.username;
        BaseData.currentLoginPwd = loginCase.pwd;
        return apiTest(new RequestData(loginCase).offDefaultAssert());
    }
}
