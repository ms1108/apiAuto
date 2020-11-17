package annotation;

import base.BaseCase;
import base.IServiceMap;
import config.asserts.AssertMethod;
import config.asserts.SuccessAssertDefault;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Inherited
public @interface Search {

    int expectListLen() default 1;

    //列表根路径可以根据项目写默认的
    String listRootPath() default "data.rows";

    String searchValuePath() default "";

    Class<? extends BaseCase> addDataBaseCase() default BaseCase.class;

    String resetAssert() default "";

    String[] group() default "0";//当输入0时则不进行分组考虑

}
