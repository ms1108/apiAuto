package business.user;

import base.CommandLogic;
import business.user.userCase.UserCase;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserTest extends CommandLogic {
    @Test
    public void test(){
        //更多断言方法http://testingpai.com/article/1599472747188
        new UserCase().user().then().body("code",equalTo(0));
    }
}
