package base;

import api.ApiTest;
import config.asserts.AssertMethod;
import config.asserts.SuccessAssertDefault;
import config.header.IHeaders;
import config.header.DefaultHeaders;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
public class BaseCase extends CommandLogic {
    public String pathParam;
    public IServiceMap serverMap;
    public AssertMethod assertMethod = new SuccessAssertDefault();
    public IHeaders headers = new DefaultHeaders();
}
