package api;

import base.BaseCase;
import base.BaseData;
import base.IServiceMap;
import business.loginTest.service_constant.LoginService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
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
        ValidatableResponse validatableResponse = response.then();
        ReportUtil.log("res     : " + response.getBody().asString());

        //默认断言
        if (requestData.isDefaultAssert()) {
            requestData.getBaseAsserts().assets(response);
        }
        //存储请求
        BaseData.req.put(requestData.getUri(), from(requestData.getParam()));
        //存储响应
        BaseData.res.put(requestData.getUri(), response);

        String ContentTypeHeader = response.getHeader("Content-Typ");
        if (ContentTypeHeader != null && (ContentTypeHeader.contains("download")
                || ContentTypeHeader.contains("octet-stream"))) {
            String loadFilePath = System.getProperty("user.dir") + "/download";
            writeFile(response.asInputStream(), loadFilePath);
        }
        //保存token
        if (requestData.getUri().equals(LoginService.Login.getUri())
                && response.statusCode() == 200
                && response.path(defaultAssertPath) == defaultAssertValue) {
            BaseData.token = response.path("data.token");
        }
        return response;
    }

}
