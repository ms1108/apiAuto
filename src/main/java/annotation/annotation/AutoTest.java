package annotation.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Inherited
/**
 * 依赖较多接口时不宜使用，会过于复杂，建议用于列表的筛选，排序等测试
 */
public @interface AutoTest {
    String des() default "";

    boolean isOpenAssert() default true;

    int sleep() default 0;
}
