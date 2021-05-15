package api;

import base.ApiMethod;
import base.BaseCase;
import base.IServiceMap;
import com.alibaba.fastjson.JSON;
import config.asserts.AssertMethod;
import config.asserts.FailAssetDefault;
import config.header.IHeaders;
import config.preparamhandle.IParamPreHandle;
import config.preparamhandle.ParamPreHandleBlankImpl;
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

    private String host;
    private String uri;
    //请求的方式
    private ApiMethod methodAndRequestType;
    //jsonSchema的位置
    private String jsonSchemaPath;
    //当步骤描述为空时，使用地址枚举中的描述
    private String des;
    //请求步骤描述
    private String stepDes;
    //cookie
    private Map<String, String> cookies;
    //请求头
    private IHeaders headers;
    //经过参数前置处理器处理的请求参数
    private String param;
    //未经过参数前置处理器处理的请求参数
    private String paramData;
    //拼接在请求路径后的数据
    private String pathParam;
    //case对象
    private BaseCase baseParam;
    //uri枚举对象
    private IServiceMap serverMap;
    //是否开启断言
    private boolean isOpenAssert = true;
    //请求休眠
    private Integer sleep;
    //断言方式
    private AssertMethod assertMethod;
    //幂等请求线程个数
    private int multiThreadNum;
    //用于区分普通请求和幂等请求
    private IRequestMethod iRequestMethod = new DefaultRequestMethod();
    //用于拼接默认的请求字段，比如 {a:{k:v}},a对象每个请求都有
    private IParamPreHandle iParamPreHandle;

    public RequestData(BaseCase param) {
        requestData(param);
    }

    public void requestData(BaseCase param) {
        this.host = get("g_host");
        this.baseParam = param;
        this.serverMap = param.serverMap;
        this.methodAndRequestType = param.serverMap.getMethodAndRequestType();
        this.jsonSchemaPath = param.serverMap.getJsonSchemaPath();
        this.des = param.serverMap.getDes();
        this.uri = param.serverMap.getUri();
        this.assertMethod = param.assertMethod;
        this.headers = param.headers;//header最好由BaseCase对象传入，因为如果通过RequestData类set进来，其他方法调用该接口时又要set一遍，而且编写代码的人离职后，其他人通过BaseCase更直观的看出
        this.pathParam = param.pathParam;
        this.iParamPreHandle = param.iParamPreHandle != null ? param.iParamPreHandle : new ParamPreHandleBlankImpl();
        //param转json串时，以下的字段不需要
        param.serverMap = null;
        param.assertMethod = null;
        param.headers = null;
        param.pathParam = null;
        param.iParamPreHandle = null;
        paramData = this.iParamPreHandle.paramPreHandle(param);
        if (paramData.contains("{\"$ref\":\"@\"}")) {
            Assert.fail("Case类中不能出现以get开头的方法，或者在该方法加上注解：@JSONField(serialize = false)");
        }
        this.param = paramData;
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
