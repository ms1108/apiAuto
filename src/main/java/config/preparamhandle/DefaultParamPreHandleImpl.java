package config.preparamhandle;

public class DefaultParamPreHandleImpl implements IParamPreHandle {
    @Override
    public String paramPreHandle(String param) {
        return param;
    }
}
