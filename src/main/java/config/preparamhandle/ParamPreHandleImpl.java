package config.preparamhandle;

import com.alibaba.fastjson.JSONObject;

public class ParamPreHandleImpl implements IParamPreHandle {
    @Override
    public String paramPreHandle(String param) {
        return JSONObject.toJSONString(param);
    }

    @Override
    public String paramPathPreHandle(String param) {
        return param;
    }
}
