package api;

import base.BaseCase;
import base.BaseData;
import base.IServiceMap;
import businessWf.loginWF.service_constant.LoginWFService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import utils.RandomUtil;
import utils.ReportUtil;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static base.BaseData.defaultAssertPath;
import static base.BaseData.defaultAssertValue;
import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import static utils.FileUtil.writeFile;

public class ApiTest {
    public Response apiTest(BaseCase baseCase) {
        return apiTest(new RequestData(baseCase));
    }

    public Response apiTest(IServiceMap iServiceMap, BaseCase baseCase) {
        return apiTest(new RequestData(iServiceMap, baseCase));
    }

    public Response apiTest(RequestData requestData) {
        //换行
        ReportUtil.log("");
        if (requestData.getStepDes() == null) {
            ReportUtil.log("Des     : " + requestData.getDes());
        } else {
            ReportUtil.log("StepDes : " + requestData.getStepDes());
        }
        ReportUtil.log("Host    : " + requestData.getHost());
        ReportUtil.log("Uri     : " + requestData.getUri());
        ReportUtil.log("Method  : " + requestData.getMethodAndRequestType().getApiMethod());

        RestAssured.baseURI = requestData.getHost();
        RestAssured.useRelaxedHTTPSValidation();
        RequestSpecification specification = given();

        Map<String, Object> headers = requestData.getHeaders();
        specification.headers(headers);
        ReportUtil.log("Header  : " + headers);

        specification = requestData.getMethodAndRequestType().getRequestType().requestBuild(specification, requestData);
        ReportUtil.log("Param   : " + requestData.getParam());

        if (requestData.getSleep() != null && requestData.getSleep() != 0) {
            try {
                ReportUtil.log("Sleep   : " + requestData.getSleep());
                TimeUnit.SECONDS.sleep(requestData.getSleep());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //发送请求
        Response response = specification.request(requestData.getMethodAndRequestType().getApiMethod(), requestData.getUri());

        //存储请求
        BaseData.req.put(requestData.getUri(), from(requestData.getParam()));
        //存储响应
        BaseData.res.put(requestData.getUri(), response);

        //下载文件
        String ContentTypeHeader = response.getHeader("Content-Type");
        String res = response.getBody().asString();
        if (ContentTypeHeader != null && (ContentTypeHeader.contains("download")
                || ContentTypeHeader.contains("octet-stream"))) {
            String fileType = "";
            String headerDisposition = response.getHeader("Content-disposition");
            if (headerDisposition != null) {
                fileType = headerDisposition.substring(headerDisposition.lastIndexOf("."), headerDisposition.length() - 1);
            }
            String contentPath = "src/main/resources/download/" + RandomUtil.getStringRandom() + fileType;
            Assert.assertTrue(writeFile(response.getBody().asInputStream(), contentPath), "下载文件失败");
            res = "{\"filePath\":\"" + contentPath + "\"}";
        }
        ReportUtil.log("res     : " + res);

        //断言
        if (requestData.isOpenDefaultAssert() && requestData.getAssertMethod() != null) {
            requestData.getAssertMethod().assets(requestData, response);
        }

        //保存token
        if (requestData.getUri().equals(LoginWFService.LoginWF.getUri())
                && response.statusCode() == 200
                && response.path(defaultAssertPath) == defaultAssertValue) {
            BaseData.token = response.path("data.token");
        }
        return response;
    }

}
