package config.preparamhandle;

import base.BaseCase;
import com.alibaba.fastjson.JSONObject;

public class DefaultParamPreHandleImpl implements IParamPreHandle {
    @Override
    public String paramPreHandle(BaseCase param) {
        return JSONObject.toJSONString(param);
    }
}
