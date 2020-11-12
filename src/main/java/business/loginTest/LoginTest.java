package business.loginTest;

import api.RequestData;
import base.BaseData;
import base.LoginBase;
import business.loginTest.testcase.ConfigCase;
import business.loginTest.testcase.LoginCase;
import business.loginTest.testcase.TestUploadCase;
import business.loginTest.service_constant.LoginService;
import org.testng.annotations.Test;

import static base.BaseData.getReqData;
import static base.BaseData.getResData;
import static org.hamcrest.CoreMatchers.*;

public class LoginTest extends LoginBase {
    //更多断言方法http://testingpai.com/article/1599472747188
    @Test
    public void test1() {
        apiTest(new RequestData(new LoginCase().rightLoginCase())
                .setStepDes("这是我的测试步骤")
        ).then().body("res", equalTo("test success"));

        //从响应中取值,常用于case类中
        System.out.println("resValue " + BaseData.res.get(LoginService.Login.getUri()).path("code"));
        Integer s = getResData(LoginService.Login, "code");
        System.out.println(s);
        //从请求中取值,常用于case类中
        System.out.println("reqValue " + BaseData.req.get(LoginService.Login.getUri()).get("loginName"));
        String s1 = getReqData(LoginService.Login,"loginName");
        System.out.println(s1);
    }

    @Test
    public void test2() {
        apiTest(new RequestData(new LoginCase().errorLoginCase()).fail());
    }

    @Test
    public void test3() {
        apiTest(new RequestData(new ConfigCase().dependCase()));
        apiTest(new RequestData(new LoginCase().dependCase()));
    }

    @Test
    public void test4() {
        apiTest(new RequestData(new ConfigCase().dependCase()));
        apiTest(new RequestData(new LoginCase().dependCase1()));
    }

    //类型测试
    @Test
    public void test5() {
        apiTest(new RequestData(new LoginCase().string2intTypeTestCase()).fail());
    }

    @Test
    public void test6() {
        apiTest(new RequestData(new LoginCase().int2stringTypeTestCase()).fail());
    }

    @Test
    public void test7() {
        apiTest(new RequestData(new TestUploadCase().uploadCase()));
    }

}
