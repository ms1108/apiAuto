package business.loginTest.testcase;

import base.UploadCase;
import business.loginTest.service_constant.LoginService;

public class TestUploadCase extends UploadCase {
    public TestUploadCase() {
        serverMap = LoginService.Upload;
    }

    public TestUploadCase uploadCase() {
        filePath = "src/main/resources/test1.json";
        return this;
    }
}
