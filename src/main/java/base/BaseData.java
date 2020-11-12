package base;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseData {
    public static Map<String, Response> res = new HashMap<>();
    public static Map<String, JsonPath> req = new HashMap<>();
    public static String currentLoginName;
    public static String currentLoginPwd;
    public static String token;
    public static List<String> stepList = new ArrayList<>();
    public static String defaultAssertPath = "code";
    public static Object defaultAssertValue = 0;

    public static <T> T getResData(IServiceMap iServiceMap, String path) {
        Response response = res.get(iServiceMap.getUri());
        if (response == null) {
            Assert.fail(iServiceMap.getUri()+"接口无响应数据");
        }
        return (T) response.path(path);
    }

    public static <T> T getReqData(IServiceMap iServiceMap, String path) {
        JsonPath jsonPath = req.get(iServiceMap.getUri());
        if (jsonPath == null) {
            Assert.fail(iServiceMap.getUri()+"接口无请求数据");
        }
        return (T)jsonPath.get(path);
    }
}
