package annotation;

import config.asserts.AssertMethod;
import config.asserts.FailAssetDefault;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@Inherited
public @interface IntToString {

    Class<? extends AssertMethod> asserts() default FailAssetDefault.class;

    String resetAssert() default "";

    String[] group() default "0";//当输入0时则不进行分组考虑

    String fieldPath() default "";

}
