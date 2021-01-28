package annotation.impl;

import annotation.AnnotationServer;
import annotation.IAnnotationTestMethod;
import annotation.annotations.Search;
import config.asserts.ListSearchAssert;
import lombok.SneakyThrows;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static base.BaseData.getRequestValue;

public class SearchDefaultImpl implements IAnnotationTestMethod {
    @SneakyThrows
    @Override
    public void testMethod(Method method, Field field, Annotation a, AnnotationServer at) {
        Search annotation = (Search) a;
        String des =
                "类名:" + at.baseCase.getClass().getSimpleName() +
                        ",字段名:" + field.getName() +
                        ",列表搜索测试,";
        Object value = getRequestValue(annotation.addDataBaseCase().newInstance().serverMap, annotation.searchValuePath());
        String dimSearch = value.toString().substring(0, value.toString().length() - 1);
        at.fieldTest(method, field, value,
                des + value, new ListSearchAssert(annotation.listRootPath(), value.toString(), annotation.expectListLen()), annotation.resetAssert());
        at.fieldTest(method, field, dimSearch,
                des + "模糊搜索:" + dimSearch, new ListSearchAssert(annotation.listRootPath(), value.toString(), annotation.expectListLen()), annotation.resetAssert());
        at.fieldTest(method, field, "",
                des + "空值搜索", new ListSearchAssert(annotation.listRootPath(), annotation.expectListLen()), annotation.resetAssert());
        at.fieldTest(method, field, null,
                des + "搜索字段不传", new ListSearchAssert(annotation.listRootPath(), annotation.expectListLen()), annotation.resetAssert());

    }
}
