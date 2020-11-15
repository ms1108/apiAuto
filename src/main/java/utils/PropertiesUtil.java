package utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtil {
    private static Properties props;

    static {
        props = new Properties();
        try {
            //读取propertiesUtil类的配置
            //利用反射加载类信息，获取配置文件的文件流，并指点编码格式
            //当该类与properties不在同级时需要加/
            props.load(new InputStreamReader(PropertiesUtil.class.getResourceAsStream("/resource.properties"), "UTF-8"));
        } catch (IOException e) {
        }
    }
    public static void set(String key, String value) {
        props.setProperty(key, value);
    }
    public static String get(String key) {
        String value = props.getProperty(key.trim(),"");
        return value.trim();
    }

}
