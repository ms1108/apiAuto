package base;

import api.ApiTest;
import config.asserts.AssertMethod;
import config.asserts.SuccessAssertDefault;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BaseCase extends ApiTest{
    public String pathParam;
    public IServiceMap serverMap;
    public AssertMethod assertMethod = new SuccessAssertDefault();
}
