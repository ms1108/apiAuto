package api;

import base.ApiMethod;
import base.BaseCase;
import base.IServiceMap;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import config.asserts.BaseAsserts;
import config.asserts.DefaultFailAsset;
import config.header.BaseHeaders;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.testng.Assert;

import java.util.Map;

import static utils.PropertiesUtil.getProperty;


@Data
@Accessors(chain = true)
public class RequestData {

    private String host = getProperty("g_host");
    private String uri;
    private Class<?> requestClass;
    private ApiMethod methodAndRequestType;
    private String des;

    private Map<String, String> cookies;
    private  Class<? extends BaseHeaders> headers;
    private String param;

    private String stepDes;
    private boolean defaultAssert = true;
    private Integer sleep;
    private BaseAsserts baseAsserts;

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
        this.des = param.serverMap.getDes();
        this.headers = param.serverMap.getHeaders();
        this.baseAsserts = param.baseAsserts;
        param.serverMap = null;
        param.baseAsserts = null;
        String jsonParam = JSON.toJSONString(param);
        if (jsonParam.contains("{\"$ref\":\"@\"}")) {
            Assert.fail("参数类中不能出现以get开头的方法，或者在该方法加上注解：@JSONField(serialize = false)");
        }

        if (param.string2int != null) {
            String string2int = param.string2int;
            param.string2int = null;
            jsonParam = switchType(param, string2int, "string2int");
        }

        if (param.int2string != null) {
            String int2string = param.int2string;
            param.int2string = null;
            jsonParam = switchType(param, int2string, "int2string");
        }

        this.param = jsonParam;
    }

    @SneakyThrows
    public Map<String, Object> getHeaders() {
        return headers.newInstance().getBaseHeaders();
    }

    public RequestData fail() {
        baseAsserts = new DefaultFailAsset();
        return this;
    }

    public RequestData offDefaultAssert() {
        defaultAssert = false;
        return this;
    }

    private String switchType(BaseCase param, String targetPath, String type) {
        String jsonString = JSON.toJSONString(param);
        JSONObject jsonObj = JSON.parseObject(jsonString);
        if ("string2int".equals(type)) {
            String value = (String) JSONPath.read(jsonString, targetPath);
            JSONPath.set(jsonObj, targetPath, Integer.parseInt(value));
        } else {
            Integer value = (Integer) JSONPath.read(jsonString, targetPath);
            JSONPath.set(jsonObj, targetPath, value + "");
        }
        return JSON.toJSONString(jsonObj);
    }
}
