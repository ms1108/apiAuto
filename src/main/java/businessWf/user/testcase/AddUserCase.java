package businessWf.user.testcase;

import AES.AesCryptUtil;
import annotation.BeforeMethod;
import annotation.Length;
import annotation.NotEmpty;
import annotation.NotNull;
import base.BaseCase;
import businessWf.user.service.UserService;
import utils.RandomUtil;

public class AddUserCase extends BaseCase {
    @NotNull
    @NotEmpty
    public String loginName;
    public String pwd;
    @Length(maxLen = 12)
    @NotNull
    public String name;
    public String phone;
    public String email;
    public int verifyType;
    public String des;
    public String idNumber;

    public AddUserCase() {
        serverMap = UserService.AddUser;
    }

    @BeforeMethod
    public AddUserCase addUserCase() {
        loginName = "User" + RandomUtil.getString();
        pwd = AesCryptUtil.encryptTmp("Admin_123");
        name = "User" + RandomUtil.getString(5);
        phone = RandomUtil.getPhone();
        email = "1@qq.com";
        verifyType = 1;
        des = RandomUtil.getString();
        return this;
    }
}
