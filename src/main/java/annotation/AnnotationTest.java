package annotation;

import annotation.annotations.*;
import api.RequestData;
import base.BaseCase;
import base.BaseData;
import base.CommandLogic;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import config.asserts.AssertMethod;
import lombok.SneakyThrows;
import utils.ClassFinderUtil;
import utils.ReportUtil;
import utils.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class AnnotationTest extends CommandLogic {

    public String rootPath = "";
    public BaseCase baseCase;

    public void annotationTest(String scannedPackage) {
        annotationTest(scannedPackage, null);
    }

    public void annotationTest(String scannedPackage, Class<? extends BaseCase> except) {
        if (!BaseData.isOpenAnnotation) {
            ReportUtil.log("------------------------------------------------注解测试已被关闭------------------------------------------------");
            return;
        }
        ClassFinderUtil classFinderUtil = new ClassFinderUtil();
        List<Class<? extends BaseCase>> scanned = classFinderUtil.scanned(scannedPackage);
        if (except != null) {
            scanned.remove(except);
        }
        for (Class<? extends BaseCase> aClass : scanned) {
            annotationTest(aClass);
        }

    }

    @SneakyThrows
    public void annotationTest(Class<? extends BaseCase> baseCaseClass) {
        if (!BaseData.isOpenAnnotation) {
            ReportUtil.log("------------------------------------------------注解测试已被关闭------------------------------------------------");
            return;
        }
        baseCase = baseCaseClass.newInstance();
        Method[] methods = baseCaseClass.getDeclaredMethods();
        List<Method> beforeMethod = new ArrayList<>();
        List<Method> autoTestMethod = new ArrayList<>();
        for (Method method : methods) {
            //BeforeClass调用前置接口
            if (method.isAnnotationPresent(BeforeClassRun.class)) {
                method.invoke(baseCase);
            }
            if (method.isAnnotationPresent(BeforeMethodRun.class)) {
                beforeMethod.add(method);
            }
            if (method.isAnnotationPresent(AutoTest.class)) {
                autoTestMethod.add(method);
            }
        }
        for (Method method : beforeMethod) {
            baseCaseField(method);
        }
        if (beforeMethod.size() == 0) {
            baseCaseField(null);
        }
        for (Method method : autoTestMethod) {
            AutoTest annotation = method.getAnnotation(AutoTest.class);
            BaseCase baseCaseAutoTest = (BaseCase) method.invoke(baseCase);
            String des = annotation.des();
            if (StringUtil.isNotEmpty(annotation.des())) {
                String currantCase = "执行@AutoTest,类名:" + baseCase.getClass().getSimpleName() + ",方法名:" + method.getName() + "，%s";
                des = String.format(currantCase, des);
            }
            apiTest(new RequestData(baseCaseAutoTest)
                    .setStepDes(des)
                    .setOpenAssert(annotation.isOpenAssert())
                    .setSleep(annotation.sleep()));
        }
    }

    private void baseCaseField(Method method) {
        //准备一份基础数据baseCaseBackup
        getBaseCaseObject(method);
        Field[] fields = baseCase.getClass().getFields();
        fieldAnnotation(fields, method);
    }

    @SneakyThrows
    private void fieldAnnotation(Field[] fields, Method method) {
        BeforeMethodRun beforeMethodRun;
        String group = "0";
        if (method != null) {
            beforeMethodRun = method.getDeclaredAnnotation(BeforeMethodRun.class);
            group = beforeMethodRun.group();
        }

        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(NotNull.class)) {
                NotNull annotation = field.getAnnotation(NotNull.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);
                }
            }
            if (field.isAnnotationPresent(NotEmpty.class)) {
                NotEmpty annotation = field.getAnnotation(NotEmpty.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);
                }
            }

            if (field.isAnnotationPresent(Unique.class)) {
                Unique annotation = field.getAnnotation(Unique.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);
                }
            }
            if (field.isAnnotationPresent(Length.class)) {
                Length annotation = field.getAnnotation(Length.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);

                }
            }
            if (field.isAnnotationPresent(Range.class)) {
                Range annotation = field.getAnnotation(Range.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    //自定义注解中的测试流程，示例
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);

                }
            }
            if (field.isAnnotationPresent(StringToInt.class)) {
                StringToInt annotation = field.getAnnotation(StringToInt.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);

                }
            }
            if (field.isAnnotationPresent(IntToString.class)) {
                IntToString annotation = field.getAnnotation(IntToString.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);

                }
            }
            if (field.isAnnotationPresent(Search.class)) {
                Search annotation = field.getAnnotation(Search.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);

                }
            }
            if (field.isAnnotationPresent(Chinese.class)) {
                Chinese annotation = field.getAnnotation(Chinese.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);

                }
            }
            if (field.isAnnotationPresent(Blank.class)) {
                Blank annotation = field.getAnnotation(Blank.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);

                }
            }

            if (field.getType().toString().contains("$")) {
                rootPath = rootPath + field.getName() + ".";
                inertClass(method, baseCase, field.getType().getSimpleName());
            }
            rootPath = "";
        }
    }

    @SneakyThrows
    public void fieldTest(Method method, Field field, Object value, String des, AssertMethod assertMethod, String retAssert) {
        BaseCase baseCaseMethod = getBaseCaseObject(method);
        RequestData requestData = new RequestData(baseCaseMethod);
        baseCase = baseCase.getClass().newInstance();//因为走了RequestData，serverMap会被置空，所以再new一遍
        String targetPath = rootPath + field.getName();
        requestData.setParam(replaceValue(requestData.getParam(), targetPath, value));
        requestData.setStepDes(des);
        if (StringUtil.isNotEmpty(retAssert)) {
            AssertMethod retAssertMethod = (AssertMethod) baseCaseMethod.getClass().getMethod(retAssert).invoke(baseCaseMethod);
            requestData.setAssertMethod(retAssertMethod);
        } else {
            requestData.setAssertMethod(assertMethod);
        }

        apiTest(requestData);

    }

    private void inertClass(Method method, BaseCase baseCase, String className) {
        Class<?>[] innerClazz = baseCase.getClass().getDeclaredClasses();
        for (Class claszInner : innerClazz) {
            if (className.equals(claszInner.getSimpleName())) {
                Field[] fields = claszInner.getDeclaredFields();
                fieldAnnotation(fields, method);
            }
        }
    }

    @SneakyThrows
    public BaseCase getBaseCaseObject(Method method) {
        BaseCase baseCaseObjcet;
        if (method != null) {
            baseCaseObjcet = (BaseCase) method.invoke(baseCase);
        } else {
            baseCaseObjcet = baseCase.getClass().getConstructor().newInstance();
        }
        return baseCaseObjcet;
    }

    private String replaceValue(String param, String targetPath, Object value) {
        JSONObject jsonObj = JSON.parseObject(param);
        JSONPath.set(jsonObj, targetPath, value);
        return JSON.toJSONString(jsonObj);
    }

    public boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}