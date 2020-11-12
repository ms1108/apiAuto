package utils;

import org.apache.commons.lang3.StringUtils;

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

    public static String getProperty(String key) {
        String value = props.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return value.trim();
    }

}
