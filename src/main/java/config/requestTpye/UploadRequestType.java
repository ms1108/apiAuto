package config.requestTpye;

import api.RequestData;
import com.alibaba.fastjson.JSONObject;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.Map;

public class UploadRequestType implements RequestType {
    @Override
    public RequestSpecification requestBuild(RequestSpecification specification, RequestData requestData) {
        Map<String, Object> jsonObject = JSONObject.parseObject(requestData.getParam());
        String filePath = (String) jsonObject.get("filePath");
        if (!filePath.startsWith("/")) {
            filePath = "/" + filePath;
        }
        String path = this.getClass().getResource(filePath).getPath();
        specification.contentType("multipart/form-data")
                .multiPart((String) jsonObject.get("fileKey"), new File(path));
        jsonObject.remove("filePath");
        jsonObject.remove("fileKey");
        return specification.queryParams(jsonObject);
    }
}
