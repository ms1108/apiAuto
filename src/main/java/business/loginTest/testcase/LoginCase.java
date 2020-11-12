package business.loginTest.testcase;

import base.BaseCase;
import business.loginTest.service_constant.LoginConstant;
import business.loginTest.service_constant.LoginService;
import lombok.Data;
import lombok.experimental.Accessors;


import static base.BaseData.getReqData;
import static base.BaseData.getResData;
import static utils.PropertiesUtil.getProperty;

@Data
@Accessors(fluent = true)
public class LoginCase extends BaseCase {
    public String loginName;
    public String pwd;
    public type type;
    public String depend;//依赖config接口返回的结果


    @Data
    @Accessors(fluent = true)
    static class type {
        public Integer role;
    }

    public LoginCase() {
        serverMap = LoginService.Login;
    }

    public LoginCase rightLoginCase() {
        loginName = getProperty("g_loginName");
        pwd = getProperty("g_loginPwd");
        type = new type().role(LoginConstant.IS_MENAGE);
        depend = "123";
        return this;
    }

    public LoginCase errorLoginCase() {
        LoginCase loginCase = rightLoginCase();
        loginCase.pwd = "";
        return this;
    }

    public LoginCase dependCase() {
        LoginCase loginCase = rightLoginCase();
        loginCase.depend = null;
        //从其他的请求参数中获取值
        //loginCase.depend = BaseData.req.get(LoginService.Config.getUri()).get("depend");
        loginCase.depend = getReqData(LoginService.Config, "depend");
        return this;
    }

    public LoginCase dependCase1() {
        LoginCase loginCase = rightLoginCase();
        loginCase.depend = null;
        //从其他响应中获取值，需要事先调用相应接口
        //loginCase.depend = BaseData.res.get(LoginService.Config.getUri()).path("res.depend");
        loginCase.depend = getResData(LoginService.Config, "res.depend");
        return this;
    }

    public LoginCase string2intTypeTestCase() {
        LoginCase loginCase = rightLoginCase();
        loginCase.string2int = "pwd";
        return loginCase;
    }

    public LoginCase int2stringTypeTestCase() {
        LoginCase loginCase = rightLoginCase();
        loginCase.int2string = "type.role";
        return loginCase;
    }

}
