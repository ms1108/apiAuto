package businessWf.user;

import api.RequestData;
import base.BaseCase;
import base.LoginBase;
import businessWf.user.testcase.AddUserCase;
import businessWf.user.testcase.DownloadUserCase;
import businessWf.user.service.UserService;
import businessWf.user.testcase.UploadUserCase;
import config.asserts.SuccessAssertDownloadFile;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static base.BaseData.getResponse;

public class UserTest extends LoginBase {
    @BeforeClass
    public void before() {
        loginWFAdmin();
    }

    @Test
    public void DownloadUserCaseAnnotationTest() {
        annotationTest(DownloadUserCase.class);
    }

    @Test
    public void downTest() {
        apiTest(new DownloadUserCase().downCase());
        apiTest(new RequestData(UserService.DOWNLOAD,
                new BaseCase().setPathParam(getResponse(UserService.DOWNLOAD_USER, "data.id")))
                .setAssertMethod(new SuccessAssertDownloadFile()));
    }

    @Test
    public void upTest() {
        apiTest(new UploadUserCase().setFilePath("src/main/resources/download/FIebXbhmOE.csv"));
    }

    @Test
    public void addUserCaseTest() {
        //apiTest(new AddUserCase().addUserCase());
        annotationTest(AddUserCase.class);
    }

    @Test
    public void annotationByPackage(){
        annotationTest("businessWf/user/testcase");
    }
}
