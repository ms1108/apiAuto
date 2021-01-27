package annotation.annotations;

import config.request.IRequest;
import config.request.MultiThreadRequest;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Inherited

public @interface MultiRequest {
    int multiThreadNum() default 2;

    Class<? extends IRequest> iRequest() default MultiThreadRequest.class;

    String des() default "";

    boolean isOpenAssert() default true;

    int sleep() default 0;
}
