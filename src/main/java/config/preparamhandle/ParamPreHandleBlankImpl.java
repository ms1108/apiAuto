package config.preparamhandle;

public class ParamPreHandleBlankImpl implements IParamPreHandle {
    @Override
    public String paramPreHandle(String param) {
        return param;
    }
}
