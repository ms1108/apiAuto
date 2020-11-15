package base;

import utils.PropertiesUtil;
import utils.StringUtil;

public class MvnArgs {
    public static String g_host = "g_host";
    public static String g_loginName = "g_loginName";
    public static String g_loginPwd = "g_loginPwd";
    public static String is_open_annotation = "is_open_annotation";

    public void mvnArgs() {
        String host = System.getProperty(g_host);
        String loginName = System.getProperty(g_loginName);
        String loginPwd = System.getProperty(g_loginPwd);
        String isOpenAnnotation = System.getProperty(is_open_annotation);

        if (!StringUtil.isEmpty(host)) {
            PropertiesUtil.set(g_host, host);
        }
        if (!StringUtil.isEmpty(loginName)) {
            PropertiesUtil.set(g_loginName, loginName);
        }
        if (!StringUtil.isEmpty(loginPwd)) {
            PropertiesUtil.set(g_loginPwd, loginPwd);
        }
        if (!StringUtil.isEmpty(isOpenAnnotation)) {
            BaseData.isOpenAnnotation = "true".equals(isOpenAnnotation);
        }
    }
}
