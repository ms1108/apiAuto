package base;

import annotation.annotations.Module;
import config.asserts.AssertMethod;
import config.asserts.SuccessAssertDefault;
import config.header.IHeaders;
import config.header.DefaultHeaders;
import config.host.DefaultHost;
import config.host.IHost;
import config.preparamhandle.ParamPreHandleImpl;
import config.preparamhandle.IParamPreHandle;
import lombok.Data;
import lombok.SneakyThrows;

@Data
public class BaseCase extends CommandLogic {
    public String pathParam;//开头需要携带斜杠'/'
    public IHost iHost;
    public IServiceMap serverMap;
    public IHeaders headers;
    public IParamPreHandle iParamPreHandle;
    public AssertMethod assertMethod = new SuccessAssertDefault();

    //根据模块赋默认的实现对象
    @SneakyThrows
    public BaseCase(){
        Module annotation = this.getClass().getAnnotation(Module.class);
        ModuleDefaultImplEnum defaultImplEnum = ModuleDefaultImplEnum.getModuleEnum(annotation.value());
        iHost = defaultImplEnum.getIHost().newInstance();
        headers = defaultImplEnum.getIHeaders().newInstance();
        iParamPreHandle = defaultImplEnum.getIParamPreHandle().newInstance();
    }
}
