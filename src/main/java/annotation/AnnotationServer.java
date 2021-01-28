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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;

public class AnnotationServer extends CommandLogic {

    private String rootPath = "";
    public BaseCase baseCase;


    @SneakyThrows
    public void annotationServer(Class<? extends BaseCase> baseCaseClass, String executeAnnotationAble) {
        if (!BaseData.isOpenAnnotation) {
            ReportUtil.log("------------------------------------------------注解测试已被关闭------------------------------------------------");
            return;
        }
        baseCase = baseCaseClass.newInstance();
        Method[] methods = baseCaseClass.getDeclaredMethods();
        List<Method> beforeMethod = new ArrayList<>();
        List<Method> autoTestMethod = new ArrayList<>();
        List<Method> multiRequestMethod = new ArrayList<>();
        for (Method method : methods) {
            //BeforeClass调用前置接口
            if (executeAnnotationAble.contains(BeforeClassRun.class.getSimpleName()) && method.isAnnotationPresent(BeforeClassRun.class)) {
                method.invoke(baseCase);
            }
            if (executeAnnotationAble.contains(BeforeMethodRun.class.getSimpleName()) && method.isAnnotationPresent(BeforeMethodRun.class)) {
                beforeMethod.add(method);
            }
            if (executeAnnotationAble.contains(AutoTest.class.getSimpleName()) && method.isAnnotationPresent(AutoTest.class)) {
                autoTestMethod.add(method);
            }
            if (executeAnnotationAble.contains(MultiRequest.class.getSimpleName()) && method.isAnnotationPresent(MultiRequest.class)) {
                multiRequestMethod.add(method);
            }
        }
        for (Method method : beforeMethod) {
            baseCaseField(method, executeAnnotationAble);
        }
        if (executeAnnotationAble.contains(BeforeMethodRun.class.getSimpleName()) && beforeMethod.size() == 0) {
            baseCaseField(null, executeAnnotationAble);
        }
        for (Method method : autoTestMethod) {
            AutoTest annotation = method.getAnnotation(AutoTest.class);
            BaseCase baseCaseTest = (BaseCase) method.invoke(baseCase);
            String des = annotation.des();
            if (StringUtil.isNotEmpty(annotation.des())) {
                des = "执行@AutoTest,类名:" + baseCase.getClass().getSimpleName() + ",方法名:" + method.getName() + "，" + des;
            }
            apiTest(new RequestData(baseCaseTest)
                    .setStepDes(des)
                    .setOpenAssert(annotation.isOpenAssert())
                    .setSleep(annotation.sleep()));
        }
        for (Method method : multiRequestMethod) {
            MultiRequest annotation = method.getAnnotation(MultiRequest.class);
            BaseCase baseCaseTest = (BaseCase) method.invoke(baseCase);
            String des = annotation.des();
            apiTest(new RequestData(baseCaseTest)
                    .setMultiThreadNum(annotation.multiThreadNum())
                    .setIRequestMethod(annotation.iRequest().newInstance())
                    .setStepDes(des)
                    .setOpenAssert(annotation.isOpenAssert())
                    .setSleep(annotation.sleep()));

        }
    }

    private void baseCaseField(Method method, String executeAnnotationAble) {
        //准备一份基础数据baseCaseBackup
        getBaseCaseObject(method);
        Field[] fields = baseCase.getClass().getFields();
        fieldAnnotation(fields, method, executeAnnotationAble);
    }

    @SneakyThrows
    private void fieldAnnotation(Field[] fields, Method method, String executeAnnotationAble) {
        BeforeMethodRun beforeMethodRun;
        String group = "0";
        if (method != null) {
            beforeMethodRun = method.getDeclaredAnnotation(BeforeMethodRun.class);
            group = beforeMethodRun.group();
        }

        for (Field field : fields) {
            field.setAccessible(true);
            if (executeAnnotationAble.contains(NotNull.class.getSimpleName()) && field.isAnnotationPresent(NotNull.class)) {
                NotNull annotation = field.getAnnotation(NotNull.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);
                }
            }
            if (executeAnnotationAble.contains(NotEmpty.class.getSimpleName()) && field.isAnnotationPresent(NotEmpty.class)) {
                NotEmpty annotation = field.getAnnotation(NotEmpty.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);
                }
            }

            if (executeAnnotationAble.contains(Unique.class.getSimpleName()) && field.isAnnotationPresent(Unique.class)) {
                Unique annotation = field.getAnnotation(Unique.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);
                }
            }
            if (executeAnnotationAble.contains(Length.class.getSimpleName()) && field.isAnnotationPresent(Length.class)) {
                Length annotation = field.getAnnotation(Length.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);
                }
            }
            if (executeAnnotationAble.contains(Range.class.getSimpleName()) && field.isAnnotationPresent(Range.class)) {
                Range annotation = field.getAnnotation(Range.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    //自定义注解中的测试流程，示例
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);
                }
            }
            if (executeAnnotationAble.contains(StringToInt.class.getSimpleName()) && field.isAnnotationPresent(StringToInt.class)) {
                StringToInt annotation = field.getAnnotation(StringToInt.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);
                }
            }
            if (executeAnnotationAble.contains(IntToString.class.getSimpleName()) && field.isAnnotationPresent(IntToString.class)) {
                IntToString annotation = field.getAnnotation(IntToString.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);
                }
            }
            if (executeAnnotationAble.contains(Search.class.getSimpleName()) && field.isAnnotationPresent(Search.class)) {
                Search annotation = field.getAnnotation(Search.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);
                }
            }
            if (executeAnnotationAble.contains(Chinese.class.getSimpleName()) && field.isAnnotationPresent(Chinese.class)) {
                Chinese annotation = field.getAnnotation(Chinese.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);
                }
            }
            if (executeAnnotationAble.contains(Blank.class.getSimpleName()) && field.isAnnotationPresent(Blank.class)) {
                Blank annotation = field.getAnnotation(Blank.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);
                }
            }
            if (executeAnnotationAble.contains(SpecialCharacters.class.getSimpleName()) && field.isAnnotationPresent(SpecialCharacters.class)) {
                SpecialCharacters annotation = field.getAnnotation(SpecialCharacters.class);
                List<String> groupList = Arrays.asList(annotation.group());
                if (groupList.contains("0") || groupList.contains(group)) {
                    IAnnotationTestMethod instance = annotation.testMethod().newInstance();
                    instance.testMethod(method, field, annotation, this);
                }
            }

            if (field.getType().toString().contains("$")) {
                rootPath = rootPath + field.getName() + ".";
                inertClass(method, baseCase, field.getType().getSimpleName(),executeAnnotationAble);
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

    private void inertClass(Method method, BaseCase baseCase, String className, String executeAnnotationAble) {
        Class<?>[] innerClazz = baseCase.getClass().getDeclaredClasses();
        for (Class claszInner : innerClazz) {
            if (className.equals(claszInner.getSimpleName())) {
                Field[] fields = claszInner.getDeclaredFields();
                fieldAnnotation(fields, method, executeAnnotationAble);
            }
        }
    }

    @SneakyThrows
    public BaseCase getBaseCaseObject(Method method) {
        BaseCase baseCaseObject;
        if (method != null) {
            baseCaseObject = (BaseCase) method.invoke(baseCase);
        } else {
            baseCaseObject = baseCase.getClass().getConstructor().newInstance();
        }
        return baseCaseObject;
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

    public List<Class<? extends BaseCase>> getBaseCaseName(String scannedPackage) {
        ClassFinderUtil classFinderUtil = new ClassFinderUtil();
        return classFinderUtil.scanned(scannedPackage);
    }

    public List<String> getAnnotationNameOnMethod(Class<? extends BaseCase> baseCase) {
        List<String> annotations = new ArrayList<>();
        for (Method method : baseCase.getDeclaredMethods()) {
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                annotations.add(annotation.annotationType().getSimpleName());
            }
        }
        return annotations;
    }

    public List<String> getAnnotationNameOnField(Class<? extends BaseCase> baseCase) {
        List<String> annotations = new ArrayList<>();
        for (Field field : baseCase.getFields()) {
            for (Annotation annotation : field.getAnnotations()) {
                annotations.add(annotation.annotationType().getSimpleName());
            }
        }
        return annotations;
    }
}