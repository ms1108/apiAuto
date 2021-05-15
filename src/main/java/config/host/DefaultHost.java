package config.host;

import static utils.PropertiesUtil.get;

public class DefaultHost implements IHost{
    @Override
    public String getHost() {
        return get("g_host");
    }
}
