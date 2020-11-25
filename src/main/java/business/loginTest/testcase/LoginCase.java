package business.loginTest.testcase;

import annotation.*;
import base.BaseCase;
import business.loginTest.service_constant.LoginConstant;
import business.loginTest.service_constant.LoginService;
import config.asserts.*;
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
    @BlankWith
    public String depend;//依赖config接口返回的结果

    @Data
    @Accessors(fluent = true)
    public static class Type {
        @NotNull()
        public TypeIn role;
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
        type = new Type().role(new TypeIn().TypeIn(LoginConstant.IS_MENAGE));
        depend = "123";
        assertMethod = new SuccessAssertGather(new EqualAssert("res", "test success"));
        return this;
    }
    @BeforeMethod
    public LoginCase rightLoginCase1() {
        loginName = get("g_loginName");
        pwd = get("g_loginPwd");
        type = new Type().role(new TypeIn().TypeIn(LoginConstant.No_MENAGE));
        depend = "123456";
        assertMethod = new SuccessAssertGather(new EqualAssert("res", "test success"));
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
        return new SuccessAssertDefault()
                .setAssert(new EqualAssert("res", "test success"))
                .setAssert(new ByOtherApiAssert(new ConfigCase().dependCase(), new EqualAssert("res.depend", "123")));
    }
}
