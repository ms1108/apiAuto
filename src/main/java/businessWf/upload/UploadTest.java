package businessWf.upload;

import base.LoginBase;
import businessWf.upload.logincase.UploadUserCase;
import org.testng.annotations.Test;

public class UploadTest extends LoginBase {
    @Test
    public void test(){
        loginAdmin();
        apiTest(new UploadUserCase().setFilePath("src/main/resources/download/HMJfZ2uAar.csv"));
    }
}
