package business.loginTest.testcase;

import annotation.*;
import base.BaseCase;
import business.loginTest.service_constant.LoginConstant;
import business.loginTest.service_constant.LoginService;
import config.asserts.AssertMethod;
import config.asserts.BaseCaseAssert;
import config.asserts.BodyAssert;
import config.asserts.SuccessAssertDefault;
import lombok.Data;
import lombok.experimental.Accessors;

import static base.BaseData.getRequest;
import static base.BaseData.getResponse;
import static utils.PropertiesUtil.get;

@Data
@Accessors(fluent = true)
public class LoginCase extends BaseCase {
    @Unique
    @NotNull
    @NotEmpty
    @Chinese
    @Blank
    public String loginName;
    @Chinese
    @Length(minLen = 1, maxLen = 8, assertFail = SuccessAssertDefault.class)
    public String pwd;
    @NotNull
    @Size(minNum = 0, maxNum = 10, assertFail = SuccessAssertDefault.class)
    public Type type;
    @NotNull
    @Length(minLen = 1, maxLen = 8, assertFail = SuccessAssertDefault.class)
    @StringToInt
    @IntToString(resetAssert = "assertRightLogin")
    public String depend;//依赖config接口返回的结果

    @Data
    @Accessors(fluent = true)
    public static class Type {
        @NotNull()
        public TypeIn role;
        //public Integer role;
    }

    @Data
    @Accessors(fluent = true)
    public static class TypeIn {
        @NotNull
        @Size(minNum = 0, maxNum = 10, assertFail = SuccessAssertDefault.class)
        public Integer TypeIn;
    }

    public LoginCase() {
        serverMap = LoginService.Login;
    }

    @BeforeMethod
    public LoginCase rightLoginCase() {
        loginName = get("g_loginName");
        pwd = get("g_loginPwd");
        //type = new Type().role(LoginConstant.IS_MENAGE);
        type = new Type().role(new TypeIn().TypeIn(LoginConstant.IS_MENAGE));
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
        loginCase.depend = getRequest(LoginService.Config, "depend");
        return this;
    }

    public LoginCase dependCase1() {
        LoginCase loginCase = rightLoginCase();
        loginCase.depend = null;
        //从其他响应中获取值，需要事先调用相应接口
        //loginCase.depend = BaseData.res.get(LoginService.Config.getUri()).path("res.depend");
        loginCase.depend = getResponse(LoginService.Config, "res.depend");
        return this;
    }

    public AssertMethod assertRightLogin() {
        return new SuccessAssertDefault()
                .setAssert(new BodyAssert("res", "test success"))
                .setAssert(new BaseCaseAssert(new ConfigCase().dependCase(), new BodyAssert("res.depend", "123")));
    }
}
