package base;

import config.header.DefaultHeaders;
import config.header.IHeaders;
import config.host.DefaultHost;
import config.host.IHost;
import config.preparamhandle.IParamPreHandle;
import config.preparamhandle.ParamPreHandleImpl;
import lombok.Getter;
import org.testng.Assert;
import utils.ReportUtil;

@Getter
public enum ModuleDefaultImpl {
    DEMO("demo", DefaultHost.class, DefaultHeaders.class, ParamPreHandleImpl.class);
    private String ModuleName;
    private Class<? extends IHost> iHost;
    private Class<? extends IHeaders> iHeaders;
    private Class<? extends IParamPreHandle> iParamPreHandle;

    ModuleDefaultImpl(String ModuleName, Class<? extends IHost> iHost, Class<? extends IHeaders> iHeaders, Class<? extends IParamPreHandle> iParamPreHandle) {
        this.ModuleName = ModuleName;
        this.iHost = iHost;
        this.iHeaders = iHeaders;
        this.iParamPreHandle = iParamPreHandle;
    }

    public static ModuleDefaultImpl getModuleEnum(String ModuleName) {
        for (ModuleDefaultImpl value : ModuleDefaultImpl.values()) {
            if (value.getModuleName().equals(ModuleName)) {
                return value;
            }
        }
        ReportUtil.log("没有找到对应的模块枚举:" + ModuleName);
        Assert.fail("没有找到对应的模块枚举");
        return DEMO;
    }
}
