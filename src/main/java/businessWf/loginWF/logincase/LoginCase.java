package businessWf.loginWF.logincase;

//import AES.AesCryptUtil;
import base.BaseCase;
import businessWf.loginWF.serviceconstant.LoginWFService;

import static utils.PropertiesUtil.get;

public class LoginCase extends BaseCase {
    public String username;
    public String pwd;
    public Integer isAdminPage;

    public LoginCase(){
        serverMap = LoginWFService.LoginWF;
    }

    public LoginCase loginCase(){
        username = "admin";
        //pwd = AesCryptUtil.encryptTmp(get("g_loginPwd"));
        isAdminPage =1;
        return this;
    }
}
