package annotation;

import base.BaseCase;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface IAnnotationTestMethod {
    void testMethod(Method method, Field field, Annotation a, AnnotationTest at);
}
