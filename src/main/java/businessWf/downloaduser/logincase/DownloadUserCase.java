package businessWf.downloaduser.logincase;

import AES.AesCryptUtil;
import base.BaseCase;
import businessWf.downloaduser.service_constant.DownloadUserServer;

import static utils.PropertiesUtil.get;

public class DownloadUserCase extends BaseCase {
    public String userPwd;
    public String exportPwd = "";
    public String[] ids = new String[]{};

    public DownloadUserCase() {
        serverMap = DownloadUserServer.DOWNLOAD_USER;
    }

    public DownloadUserCase downCase() {
        userPwd = AesCryptUtil.encryptTmp(get("g_loginPwd"));
        return this;
    }
}
