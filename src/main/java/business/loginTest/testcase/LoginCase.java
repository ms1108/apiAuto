package business.loginTest.testcase;

import annotation.annotation.*;
import api.RequestData;
import base.BaseCase;
import business.loginTest.service_constant.LoginConstant;
import business.loginTest.service_constant.LoginService;
import config.asserts.*;
import config.header.DefaultHeaders;
import lombok.Data;
import lombok.experimental.Accessors;
import utils.RandomUtil;

import static base.BaseData.*;
import static utils.PropertiesUtil.get;

@Data
@Accessors(fluent = true)
public class LoginCase extends BaseCase {
    @Unique(assertFail = SuccessAssertDefault.class)
    @NotNull(asserts = SuccessAssertDefault.class)
    @NotEmpty(asserts = SuccessAssertDefault.class)
    @Chinese
    @Blank(asserts = SuccessAssertDefault.class)
    public String loginName;

    @Chinese
    @Length(minLen = 1, maxLen = 8, assertFail = SuccessAssertDefault.class)
    public String pwd;

    @NotNull(asserts = SuccessAssertDefault.class)
    public Type type;

    @NotNull(asserts = SuccessAssertDefault.class)
    @Length(minLen = 1, maxLen = 8, assertFail = SuccessAssertDefault.class)
    @StringToInt(asserts = SuccessAssertDefault.class)
    @IntToString(resetAssert = "assertRightLogin")
    public String depend;//依赖config接口返回的结果

    @BlankWith(group = "1")
    public String userName;

    @Data
    @Accessors(fluent = true)
    public static class Type {
        @NotNull(asserts = SuccessAssertDefault.class)
        @Range(maxNum = "10", minInfinite = true, assertFail = SuccessAssertDefault.class)
        public TypeIn role;
    }

    @Data
    @Accessors(fluent = true)
    public static class TypeIn {
        @NotNull(asserts = SuccessAssertDefault.class)
        @Range(minNum = "0.1", maxNum = "1", floatValue = "0.1", assertFail = SuccessAssertDefault.class)//测试范围(0,1]
        public Integer TypeIn;
    }

    public LoginCase() {
        serverMap = LoginService.Login;
    }

    @BeforeClassRun
    public void dependBeforeClass() {
        apiTest(new RequestData(new ConfigCase().dependCase()));
    }

    @BeforeMethodRun
    public LoginCase rightLoginCase() {
        loginName = get("g_loginName");
        pwd = get("g_loginPwd");
        type = new Type().role(new TypeIn().TypeIn(LoginConstant.IS_MENAGE));
        depend = "123";
        assertMethod = new SuccessAssertGather(new EqualAssert("res", "test success"));
        return this;
    }

    @BeforeMethodRun(group = "1")
    public LoginCase rightLoginCase1() {
        loginName = get("g_loginName");
        pwd = get("g_loginPwd");
        type = new Type().role(new TypeIn().TypeIn(LoginConstant.No_MENAGE));
        depend = "123456";
        userName = RandomUtil.getString();
        assertMethod = new SuccessAssertGather(new EqualAssert("res", "test success"));
        headers = new DefaultHeaders();
        return this;
    }

    public LoginCase errorLoginCase() {
        LoginCase loginCase = rightLoginCase();
        loginCase.pwd = "";
        return this;
    }

    @AutoTest(des = "依赖测试")
    public LoginCase dependCase() {
        LoginCase loginCase = rightLoginCase();
        //从其他的请求参数中获取值
        loginCase.depend = getRequest(LoginService.Config, "depend");
        return this;
    }

    public LoginCase dependCase1() {
        LoginCase loginCase = rightLoginCase();
        loginCase.depend = null;
        //从其他响应中获取值，需要事先调用相应接口
        loginCase.depend = getResponse(LoginService.Config, "res.depend");
        return this;
    }

    public AssertMethod assertRightLogin() {
        return new SuccessAssertGather(new EqualAssert("res", "test success"),
                new ByOtherApiAssert(new ConfigCase().dependCase(), new EqualAssert("res.depend", "123")));
        //return new SuccessAssertDefault()
        //        .setAssert(new EqualAssert("res", "test success"))
        //        .setAssert(new ByOtherApiAssert(new ConfigCase().dependCase(), new EqualAssert("res.depend", "123")));
    }
}
