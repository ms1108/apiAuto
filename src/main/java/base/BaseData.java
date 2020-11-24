package base;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class BaseData {
    public static Map<String, Response> res = new HashMap<>();
    public static Map<String, JsonPath> req = new HashMap<>();
    public static String currentLoginName;
    public static String currentLoginPwd;
    public static String token;
    public static String defaultAssertPath = "code";
    public static Object defaultAssertValue = 0;
    public static boolean isOpenAnnotation = true;
    public static String tmpDir = "src/main/resources/download";

    static {
        MvnArgs mvnArgs = new MvnArgs();
        mvnArgs.mvnArgs();
    }

    public static <T> T getResponse(IServiceMap iServiceMap, String path) {
        Response response = res.get(iServiceMap.getUri());
        if (response == null) {
            Assert.fail(iServiceMap.getUri() + ",接口无响应数据");
        }

        T t = (T) response.path(path);
        if (t == null) {
            Assert.fail("未获取到请求数据，接口：" + iServiceMap.getUri() + ",路径:" + path);
        }
        return t;
    }

    public static <T> T getRequest(IServiceMap iServiceMap, String path) {
        JsonPath jsonPath = req.get(iServiceMap.getUri());
        if (jsonPath == null) {
            Assert.fail(iServiceMap.getUri() + ",接口无请求数据");
        }
        T t = (T) jsonPath.get(path);
        if (t == null) {
            Assert.fail("未获取到请求数据，接口：" + iServiceMap.getUri() + ",路径:" + path);
        }
        return t;
    }
}
