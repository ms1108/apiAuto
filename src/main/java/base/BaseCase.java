package base;

import api.ApiTest;
import config.asserts.BaseAsserts;
import config.asserts.DefaultSuccessAssert;

public class BaseCase extends ApiTest{
    public String string2int;
    public String int2string;
    public IServiceMap serverMap;
    public BaseAsserts baseAsserts = new DefaultSuccessAssert();
}
