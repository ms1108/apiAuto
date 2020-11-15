package businessWf.loginWF;

import api.ApiTest;
import api.RequestData;
import businessWf.loginWF.logincase.LoginCase;
import org.testng.annotations.Test;

public class LoginWF extends ApiTest {
    @Test(priority = 1)
    public void login() {
        //apiTest(new RequestData(LoginWFService.Config,new BaseCase()));
        //apiTest(LoginWFService.Config,new BaseCase());
        apiTest(new RequestData(new LoginCase().loginCase()));
    }
}
