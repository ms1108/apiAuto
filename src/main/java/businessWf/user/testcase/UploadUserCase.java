package businessWf.user.testcase;

import base.UploadCase;
import businessWf.user.service.UserService;

public class UploadUserCase extends UploadCase {

    public UploadUserCase() {
        serverMap = UserService.Upload_USER;
    }
}
