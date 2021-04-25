package api;

import base.ApiMethod;
import base.BaseCase;
import base.IServiceMap;
import com.alibaba.fastjson.JSON;
import config.asserts.AssertMethod;
import config.asserts.FailAssetDefault;
import config.header.IHeaders;
import config.requestMethod.DefaultRequestMethod;
import config.requestMethod.IRequestMethod;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.testng.Assert;

import java.util.Map;

import static utils.PropertiesUtil.get;


@Data
@NoArgsConstructor
@Accessors(chain = true)
public class RequestData {

    private String host = get("g_host");
    private String uri;
    private ApiMethod methodAndRequestType;
    private String jsonSchemaPath;
    private String des;
    private String stepDes;
    private Map<String, String> cookies;
    private IHeaders headers;
    private String param;
    private String pathParam;
    private BaseCase baseParam;
    private IServiceMap serverMap;

    private boolean isOpenAssert = true;
    private Integer sleep;
    private AssertMethod assertMethod;

    private int multiThreadNum;
    private IRequestMethod iRequestMethod = new DefaultRequestMethod();

    public RequestData(BaseCase param) {
        requestData(param);
    }

    public void requestData(BaseCase param) {
        this.baseParam = param;
        this.serverMap = param.serverMap;
        this.methodAndRequestType = param.serverMap.getMethodAndRequestType();
        this.jsonSchemaPath = param.serverMap.getJsonSchemaPath();
        this.des = param.serverMap.getDes();
        this.uri = param.serverMap.getUri();
        this.assertMethod = param.assertMethod;
        this.headers = param.headers;//header最好由BaseCase对象传入，因为如果通过RequestData类set进来，其他方法调用该接口时又要set一遍，而且编写代码的人离职后，其他人通过BaseCase更直观的看出
        this.pathParam = param.pathParam;
        //param转json串时，以下的字段不需要
        param.serverMap = null;
        param.assertMethod = null;
        param.headers = null;
        param.pathParam = null;
        String jsonParam = JSON.toJSONString(param);
        if (jsonParam.contains("{\"$ref\":\"@\"}")) {
            Assert.fail("Case类中不能出现以get开头的方法，或者在该方法加上注解：@JSONField(serialize = false)");
        }
        this.param = jsonParam;
    }

    public RequestData fail() {
        assertMethod = new FailAssetDefault();
        return this;
    }

    public RequestData offDefaultAssert() {
        isOpenAssert = false;
        return this;
    }

}
