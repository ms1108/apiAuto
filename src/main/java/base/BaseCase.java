package base;

import annotation.annotations.Chinese;
import config.asserts.AssertMethod;
import config.asserts.SuccessAssertDefault;
import config.header.IHeaders;
import config.header.DefaultHeaders;
import config.preparamhandle.DefaultParamPreHandleImpl;
import config.preparamhandle.IParamPreHandle;
import lombok.Data;

@Data
public class BaseCase extends CommandLogic {
    public String pathParam;//开头需要携带斜杠'/'
    public IServiceMap serverMap;
    public AssertMethod assertMethod = new SuccessAssertDefault();
    public IHeaders headers = new DefaultHeaders();
    public IParamPreHandle iParamPreHandle = new DefaultParamPreHandleImpl();
}
