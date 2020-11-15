package businessWf.downloaduser;

import api.RequestData;
import base.BaseCase;
import base.LoginBase;
import businessWf.downloaduser.logincase.DownloadUserCase;
import businessWf.downloaduser.service_constant.DownloadUserServer;
import config.asserts.SuccessAssertDownloadUser;
import org.testng.annotations.Test;

import static base.BaseData.getResponse;

public class DownloadTest extends LoginBase {
    @Test
    public void downTest(){
        loginAdmin();
        apiTest(new DownloadUserCase().downCase());
        apiTest(new RequestData(DownloadUserServer.DOWNLOAD,
                new BaseCase().setPathParam(getResponse(DownloadUserServer.DOWNLOAD_USER,"data.id")))
                .setAssertMethod(new SuccessAssertDownloadUser()));
    }

}
