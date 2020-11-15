package config.requestTpye;

import api.RequestData;
import com.alibaba.fastjson.JSONObject;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;

import java.io.File;
import java.util.Map;

public class UploadRequestType implements RequestType {
    @SneakyThrows
    @Override
    public RequestSpecification requestBuild(RequestSpecification specification, RequestData requestData) {
        Map<String, Object> jsonObject = JSONObject.parseObject(requestData.getParam());
        String filePath = (String) jsonObject.get("filePath");
        specification.contentType("multipart/form-data")
                .multiPart((String) jsonObject.get("fileKey"), new File(filePath));
        jsonObject.remove("filePath");
        jsonObject.remove("fileKey");
        return specification.queryParams(jsonObject);
    }
}
