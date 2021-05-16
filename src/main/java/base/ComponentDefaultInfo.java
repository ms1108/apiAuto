package base;

import config.asserts.AssertMethod;
import config.asserts.SuccessAssertDefault;
import config.header.DefaultHeaders;
import config.header.IHeaders;
import config.host.DefaultHost;
import config.host.IHost;
import config.preparamhandle.IParamPreHandle;
import config.preparamhandle.ParamPreHandleImpl;
import lombok.Getter;
import org.testng.Assert;

@Getter
public enum ComponentDefaultInfo {
    COMPONENT("loginTest", DefaultHost.class, DefaultHeaders.class, ParamPreHandleImpl.class, SuccessAssertDefault.class);
    private String componentName;
    private Class<? extends IHost> iHost;
    private Class<? extends IHeaders> iHeaders;
    private Class<? extends IParamPreHandle> iParamPreHandle;
    private Class<? extends AssertMethod> assertMethod;

    ComponentDefaultInfo(String componentName, Class<? extends IHost> iHost, Class<? extends IHeaders> iHeaders, Class<? extends IParamPreHandle> iParamPreHandle, Class<? extends AssertMethod> assertMethod) {
        this.componentName = componentName;
        this.iHost = iHost;
        this.iHeaders = iHeaders;
        this.iParamPreHandle = iParamPreHandle;
        this.assertMethod = assertMethod;
    }

    public static ComponentDefaultInfo getModuleEnum(String moduleName) {
        for (ComponentDefaultInfo value : ComponentDefaultInfo.values()) {
            if (value.getComponentName().equals(moduleName)) {
                return value;
            }
        }
        Assert.fail("没有找到对应的枚举模块," + moduleName);
        return COMPONENT;
    }
}
