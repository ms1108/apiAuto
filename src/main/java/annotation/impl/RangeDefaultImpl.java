package annotation.impl;

import annotation.AnnotationTest;
import annotation.IAnnotationTestMethod;
import annotation.annotation.Range;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * 数值范围[min,max]
 */
public class RangeDefaultImpl implements IAnnotationTestMethod {

    @SneakyThrows
    @Override
    public void testMethod(Method method, Field field, Annotation a, AnnotationTest at) {
        Range annotation = (Range) a;
        BigDecimal minNum = new BigDecimal(annotation.minNum());
        BigDecimal maxNum = new BigDecimal(annotation.maxNum());
        BigDecimal floatValue = new BigDecimal(annotation.floatValue());
        String des =
                "类名:" + at.baseCase.getClass().getSimpleName() +
                        ",字段名:" + field.getName() +
                        ",期望大小范围:" + minNum + "-" + maxNum +
                        ",传入值:";
        at.fieldTest(method, field, minNum, des + minNum, annotation.assertSuccess().newInstance(), annotation.resetAssert());
        at.fieldTest(method, field, maxNum, des + maxNum, annotation.assertSuccess().newInstance(), annotation.resetAssert());
        at.fieldTest(method, field, minNum.subtract(floatValue), des + minNum.subtract(floatValue), annotation.assertFail().newInstance(), annotation.resetAssert());
        at.fieldTest(method, field, maxNum.add(floatValue), des + maxNum.add(floatValue), annotation.assertFail().newInstance(), annotation.resetAssert());

    }
}
