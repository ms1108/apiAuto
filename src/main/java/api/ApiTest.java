package api;

import base.BaseCase;
import base.BaseData;
import business.loginTest.service_constant.LoginService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import utils.RandomUtil;
import utils.ReportUtil;
import utils.StringUtil;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static base.BaseData.*;
import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import static utils.FileUtil.writeFile;

public class ApiTest {
    //该类中的属性一定要写私有的如下
    //private Integer test = 1;

    public Response apiTest(BaseCase baseCase) {
        return apiTest(new RequestData(baseCase));
    }

    public Response apiTest(RequestData requestData) {
        //换行
        ReportUtil.log("");
        if (StringUtil.isEmpty(requestData.getStepDes())) {
            ReportUtil.log("Des       :" + requestData.getDes());
        } else {
            ReportUtil.log("StepDes   :" + requestData.getStepDes());
        }
        ReportUtil.log("Host      :" + requestData.getHost());
        ReportUtil.log("Uri       :" + requestData.getUri());
        ReportUtil.log("Method    :" + requestData.getMethodAndRequestType().getApiMethod());
        ReportUtil.log("ParamType :" + requestData.getMethodAndRequestType().getParamMethod().getClass().getSimpleName());

        RestAssured.baseURI = requestData.getHost();
        RestAssured.useRelaxedHTTPSValidation();
        RequestSpecification specification = given();

        Map<String, Object> headers = requestData.getHeaders().getHeaders(requestData);
        specification.headers(headers);
        ReportUtil.log("Header    :" + headers);

        specification = requestData.getMethodAndRequestType().getParamMethod().paramMethodBuild(specification, requestData);

        ReportUtil.log("Param     :" + requestData.getParam());

        if (requestData.getSleep() != null && requestData.getSleep() != 0) {
            try {
                ReportUtil.log("Sleep     :" + requestData.getSleep());
                TimeUnit.SECONDS.sleep(requestData.getSleep());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //发送请求
        Response response = requestData.getIRequest().requestMethod(specification, requestData);
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
            String contentPath = DownloadDir + RandomUtil.getString() + fileType;
            Assert.assertTrue(writeFile(response.getBody().asInputStream(), contentPath), "下载文件失败");
            res = "{\"filePath\":\"" + contentPath + "\"}";
        }
        ReportUtil.log("res       :" + res);

        //断言
        if (requestData.isOpenAssert() && requestData.getAssertMethod() != null) {
            requestData.getAssertMethod().assets(requestData, response);
        }

        //保存token
        if (requestData.getUri().equals(LoginService.Login.getUri())
                && response.statusCode() == 200
                && response.path(defaultAssertPath) == defaultAssertValue) {
            //BaseData.token = response.path("data.token");
        }
        return response;
    }
}
