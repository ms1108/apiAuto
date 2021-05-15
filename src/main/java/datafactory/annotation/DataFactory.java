package datafactory.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Inherited
public @interface DataFactory {
    String name() default "";
    String des() default "";
}
