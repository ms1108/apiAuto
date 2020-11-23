package base;

import api.ApiTest;
import config.asserts.AssertMethod;
import config.asserts.SuccessAssertDefault;
import config.header.BaseHeaders;
import config.header.DefaultHeaders;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BaseCase extends LoginBase {
    public String pathParam;
    public IServiceMap serverMap;
    public AssertMethod assertMethod = new SuccessAssertDefault();
    public BaseHeaders headers = new DefaultHeaders();
}
