package businessWf.user.testcase;

import AES.AesCryptUtil;
import annotation.BeforeMethod;
import annotation.NotEmpty;
import annotation.NotNull;
import base.BaseCase;
import businessWf.user.service.UserService;

import static utils.PropertiesUtil.get;

public class DownloadUserCase extends BaseCase {
    @NotEmpty
    @NotNull
    public String userPwd;
    public String exportPwd = "";
    public String[] ids = new String[]{};

    public DownloadUserCase() {
        serverMap = UserService.DOWNLOAD_USER;
    }

    @BeforeMethod
    public DownloadUserCase downCase() {
        userPwd = AesCryptUtil.encryptTmp(get("g_loginPwd"));
        return this;
    }
}
