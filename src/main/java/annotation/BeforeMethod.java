package annotation;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Inherited
public @interface BeforeMethod {
    String group() default "0";
}
