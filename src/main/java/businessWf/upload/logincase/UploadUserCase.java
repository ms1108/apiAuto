package businessWf.upload.logincase;

import base.UploadCase;
import businessWf.upload.service_constant.UploadUserServer;

public class UploadUserCase extends UploadCase {

    public UploadUserCase() {
        serverMap = UploadUserServer.Upload_USER;
    }
}
