package api;

import base.ApiMethod;
import base.BaseCase;
import base.IServiceMap;
import com.alibaba.fastjson.JSON;
import config.asserts.AssertMethod;
import config.asserts.FailAssetDefault;
import config.header.BaseHeaders;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.testng.Assert;

import java.util.Map;

import static utils.PropertiesUtil.get;


@Data
@Accessors(chain = true)
public class RequestData {

    private String host = get("g_host");
    private String uri;
    private ApiMethod methodAndRequestType;
    private String jsonSchemaPath;
    private String des;
    private String stepDes;

    private Map<String, String> cookies;
    private Class<? extends BaseHeaders> headers;
    private String param;

    private boolean isOpenDefaultAssert = true;
    private Integer sleep;
    private AssertMethod assertMethod;

    public RequestData() {
    }

    public RequestData(BaseCase param) {
        requestData(param);
    }

    public RequestData(IServiceMap iServiceMap, BaseCase param) {
        param.serverMap = iServiceMap;
        requestData(param);
    }

    public void requestData(BaseCase param) {
        this.uri = param.serverMap.getUri();
        this.methodAndRequestType = param.serverMap.getMethodAndRequestType();
        this.jsonSchemaPath = param.serverMap.getJsonSchemaPath();
        this.des = param.serverMap.getDes();
        this.headers = param.serverMap.getHeaders();
        this.assertMethod = param.assertMethod;
        param.serverMap = null;
        param.assertMethod = null;
        String jsonParam = JSON.toJSONString(param);
        if (jsonParam.contains("{\"$ref\":\"@\"}")) {
            Assert.fail("参数类中不能出现以get开头的方法，或者在该方法加上注解：@JSONField(serialize = false)");
        }
        this.param = jsonParam;
    }

    @SneakyThrows
    public Map<String, Object> getHeaders() {
        return headers.newInstance().getBaseHeaders();
    }

    public RequestData fail() {
        assertMethod = new FailAssetDefault();
        return this;
    }

    public RequestData offDefaultAssert() {
        isOpenDefaultAssert = false;
        return this;
    }

    public RequestData setAssertMethod(AssertMethod assertMethod) {
        this.assertMethod = getAssertMethod().setAssert(assertMethod);
        return this;
    }
}
