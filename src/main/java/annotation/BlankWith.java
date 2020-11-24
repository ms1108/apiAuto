package annotation;

import config.asserts.AssertMethod;
import config.asserts.SuccessAssert;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Inherited
//首末尾加上空格测试
public @interface BlankWith {
    Class<? extends AssertMethod> asserts() default SuccessAssert.class;

    String resetAssert() default "";

    String[] group() default "0";//当输入0时则不进行分组考虑
}
