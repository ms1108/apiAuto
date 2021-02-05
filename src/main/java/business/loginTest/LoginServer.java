package business.loginTest;

import api.RequestData;
import base.AnnotationTest;
import business.loginTest.testcase.ConfigCase;
import business.loginTest.testcase.LoginCase;
import business.loginTest.testcase.TestUploadCase;
import config.asserts.EqualAssert;
import config.asserts.SuccessAssertDefault;
import org.testng.annotations.Test;

/**
 * Test类，建议用于多流程，多场景测试
 */
public class LoginServer extends AnnotationTest {

    public LoginServer(String packagePath) {
        super("business.loginTest.testcase");
    }

    //更多断言方法http://testingpai.com/article/1599472747188
    @Test
    public void test() {
        apiTest(new RequestData(new LoginCase().rightLoginCase())
                .setStepDes("这是我的测试步骤")
                .setAssertMethod(new SuccessAssertDefault()
                        .setAssert(new EqualAssert("res", "test success"))));
        //.then().body("res", equalTo("test success"));

        ////从响应中取值,常用于case类中
        //System.out.println("resValue " + BaseData.res.get(LoginService.Login.getUri()).path("code"));
        //Integer s = getResponse(LoginService.Login, "code");
        //System.out.println(s);
        ////从请求中取值,常用于case类中
        //System.out.println("reqValue " + BaseData.req.get(LoginService.Login.getUri()).get("loginName"));
        //String s1 = getRequest(LoginService.Login,"loginName");
        //System.out.println(s1);
    }

    @Test
    public void test11() {
        apiTest(new RequestData(new ConfigCase().dependCase()));
        apiTest(new LoginCase().rightLoginCase());
        //.then().body("res", equalTo("test success"));

        ////从响应中取值,常用于case类中
        //System.out.println("resValue " + BaseData.res.get(LoginService.Login.getUri()).path("code"));
        //Integer s = getResponse(LoginService.Login, "code");
        //System.out.println(s);
        ////从请求中取值,常用于case类中
        //System.out.println("reqValue " + BaseData.req.get(LoginService.Login.getUri()).get("loginName"));
        //String s1 = getRequest(LoginService.Login,"loginName");
        //System.out.println(s1);
    }

    @Test
    public void test2() {
        apiTest(new RequestData(new LoginCase().errorLoginCase()));
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

    @Test
    public void test7() {
        apiTest(new RequestData(new TestUploadCase().uploadCase()));
    }

}