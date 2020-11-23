package annotation;

import api.ApiTest;
import api.RequestData;
import base.BaseCase;
import base.BaseData;
import base.LoginBase;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import config.asserts.AssertMethod;
import config.asserts.ListSearchAssert;
import lombok.SneakyThrows;
import utils.ClassFinderUtil;
import utils.RandomUtil;
import utils.ReportUtil;
import utils.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;

import static base.BaseData.getRequest;

public class AnnotationTest extends LoginBase {

    private String rootPath = "";
    private BaseCase baseCase;
    private BaseCase baseCaseOld;

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
        baseCase.assertMethod = null;
        Method[] methods = baseCaseClass.getDeclaredMethods();
        List<Method> beforeMethod = new ArrayList<>();
        for (Method method : methods) {
            //BeforeClass调用前置接口
            if (method.isAnnotationPresent(BeforeClass.class)) {
                method.invoke(baseCase);
            }
            if (method.isAnnotationPresent(BeforeMethod.class)) {
                beforeMethod.add(method);
            }
        }
        for (Method method : beforeMethod) {
            baseCaseField(method);
        }
        if (beforeMethod.size() == 0) {
            baseCaseField(null);
        }
    }

    private void baseCaseField(Method method) {
        getRequestData(method);
        Field[] fields = baseCase.getClass().getFields();
        fieldAnnotation(fields, method);
    }

    @SneakyThrows
    private void fieldAnnotation(Field[] fields, Method method) {
        BeforeMethod beforeMethod;
        String group = "0";
        if (method != null) {
            //只获取当前类中的方法
            beforeMethod = method.getDeclaredAnnotation(BeforeMethod.class);
            group = beforeMethod.group();
        }
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(NotNull.class)) {
                NotNull annotation = field.getAnnotation(NotNull.class);
                if (Arrays.asList(annotation.group()).contains("0") || Arrays.asList(annotation.group()).contains(group)) {
                    String des = "类名:" + baseCase.getClass().getSimpleName() + ",字段名:" + field.getName() + ",输入null值校验";
                    fieldTest(method, field, null, des, annotation.asserts().newInstance(), annotation.resetAssert());

                }
            }
            if (field.isAnnotationPresent(NotEmpty.class)) {
                NotEmpty annotation = field.getAnnotation(NotEmpty.class);
                if (Arrays.asList(annotation.group()).contains("0") || Arrays.asList(annotation.group()).contains(group)) {
                    String des = "类名:" + baseCase.getClass().getSimpleName() + ",字段名:" + field.getName() + ",空字符串校验";
                    fieldTest(method, field, "", des, annotation.asserts().newInstance(), annotation.resetAssert());
                }
            }
            if (field.isAnnotationPresent(Unique.class)) {
                Unique annotation = field.getAnnotation(Unique.class);
                if (Arrays.asList(annotation.group()).contains("0") || Arrays.asList(annotation.group()).contains(group)) {
                    String des = "类名:" + baseCase.getClass().getSimpleName() + ",字段名:" + field.getName() + ",唯一性校验";
                    String uniqueRandom = "Unique" + RandomUtil.getString(8);
                    fieldTest(method, field, uniqueRandom, des + ",数据准备", annotation.assertSuccess().newInstance(), annotation.resetAssert());
                    fieldTest(method, field, uniqueRandom, des + ",已存在创建失败", annotation.assertFail().newInstance(), annotation.resetAssert());

                }
            }
            if (field.isAnnotationPresent(Length.class)) {
                Length annotation = field.getAnnotation(Length.class);
                if (Arrays.asList(annotation.group()).contains("0") || Arrays.asList(annotation.group()).contains(group)) {
                    int minLen = annotation.minLen();
                    int maxLen = annotation.maxLen();
                    String des =
                            "类名:" + baseCase.getClass().getSimpleName() +
                                    ",字段名:" + field.getName() +
                                    ",期望长度范围:" + minLen + "-" + maxLen +
                                    ",传入值长度:";
                    fieldTest(method, field, RandomUtil.getString(minLen), des + minLen, annotation.assertSuccess().newInstance(), annotation.resetAssert());
                    fieldTest(method, field, RandomUtil.getString(maxLen), des + maxLen, annotation.assertSuccess().newInstance(), annotation.resetAssert());
                    if (minLen != 1) {
                        fieldTest(method, field, RandomUtil.getString(minLen - 1), des + (minLen - 1), annotation.assertFail().newInstance(), annotation.resetAssert());
                    }
                    fieldTest(method, field, RandomUtil.getString(maxLen + 1), des + (maxLen + 1), annotation.assertFail().newInstance(), annotation.resetAssert());
                }
            }
            if (field.isAnnotationPresent(Size.class)) {
                Size annotation = field.getAnnotation(Size.class);
                if (Arrays.asList(annotation.group()).contains("0") || Arrays.asList(annotation.group()).contains(group)) {
                    int minNum = annotation.minNum();
                    int maxNum = annotation.maxNum();
                    String des =
                            "类名:" + baseCase.getClass().getSimpleName() +
                                    ",字段名:" + field.getName() +
                                    ",期望大小范围:" + minNum + "-" + maxNum +
                                    ",传入值:";
                    fieldTest(method, field, minNum, des + minNum, annotation.assertSuccess().newInstance(), annotation.resetAssert());
                    fieldTest(method, field, maxNum, des + maxNum, annotation.assertSuccess().newInstance(), annotation.resetAssert());
                    fieldTest(method, field, minNum - 1, des + (minNum - 1), annotation.assertFail().newInstance(), annotation.resetAssert());
                    fieldTest(method, field, maxNum + 1, des + (maxNum + 1), annotation.assertFail().newInstance(), annotation.resetAssert());

                }
            }
            if (field.isAnnotationPresent(StringToInt.class)) {
                StringToInt annotation = field.getAnnotation(StringToInt.class);
                if (Arrays.asList(annotation.group()).contains("0") || Arrays.asList(annotation.group()).contains(group)) {
                    String des =
                            "类名:" + baseCase.getClass().getSimpleName() +
                                    ",字段名:" + field.getName() +
                                    ",类型测试,传入整形:";
                    Integer value = isInteger(field.get(baseCaseOld) + "") ? Integer.parseInt((String) field.get(baseCaseOld)) : 1;
                    fieldTest(method, field, value, des + value, annotation.asserts().newInstance(), annotation.resetAssert());

                }
            }
            if (field.isAnnotationPresent(IntToString.class)) {
                IntToString annotation = field.getAnnotation(IntToString.class);
                if (Arrays.asList(annotation.group()).contains("0") || Arrays.asList(annotation.group()).contains(group)) {
                    String des =
                            "类名:" + baseCase.getClass().getSimpleName() +
                                    ",字段名:" + field.getName() +
                                    ",类型测试,传入字符:";
                    String value = field.get(baseCaseOld) + "";
                    fieldTest(method, field, value, des + value, annotation.asserts().newInstance(), annotation.resetAssert());
                }
            }
            if (field.isAnnotationPresent(Search.class)) {
                Search annotation = field.getAnnotation(Search.class);
                if (Arrays.asList(annotation.group()).contains("0") || Arrays.asList(annotation.group()).contains(group)) {
                    String des =
                            "类名:" + baseCase.getClass().getSimpleName() +
                                    ",字段名:" + field.getName() +
                                    ",列表搜索测试,";
                    Object value = getRequest(annotation.addDataBaseCase().newInstance().serverMap, annotation.searchValuePath());
                    String dimSearch = value.toString().substring(0, value.toString().length() - 1);
                    fieldTest(method, field, value,
                            des + value, new ListSearchAssert(annotation.listRootPath(), value.toString(), annotation.expectListLen()), annotation.resetAssert());
                    fieldTest(method, field, dimSearch,
                            des + "模糊搜索:" + dimSearch, new ListSearchAssert(annotation.listRootPath(), value.toString(), annotation.expectListLen()), annotation.resetAssert());
                    fieldTest(method, field, "",
                            des + "空值搜索", new ListSearchAssert(annotation.listRootPath(), annotation.expectListLen()), annotation.resetAssert());
                    fieldTest(method, field, null,
                            des + "搜索字段不传", new ListSearchAssert(annotation.listRootPath(), annotation.expectListLen()), annotation.resetAssert());
                }
            }
            if (field.isAnnotationPresent(Chinese.class)) {
                Chinese annotation = field.getAnnotation(Chinese.class);
                if (Arrays.asList(annotation.group()).contains("0") || Arrays.asList(annotation.group()).contains(group)) {
                    String des =
                            "类名:" + baseCase.getClass().getSimpleName() +
                                    ",字段名:" + field.getName() +
                                    ",中文字符测试,传入中文值:";
                    String value = RandomUtil.getChinese(annotation.chineseLen());
                    fieldTest(method, field, value, des + value, annotation.asserts().newInstance(), annotation.resetAssert());
                }
            }
            if (field.isAnnotationPresent(Blank.class)) {
                Blank annotation = field.getAnnotation(Blank.class);
                if (Arrays.asList(annotation.group()).contains("0") || Arrays.asList(annotation.group()).contains(group)) {
                    String des =
                            "类名:" + baseCase.getClass().getSimpleName() +
                                    ",字段名:" + field.getName() +
                                    ",末尾加空格测试,传入中文值:";
                    String value = RandomUtil.getString() + " ";
                    fieldTest(method, field, value, des + value, annotation.asserts().newInstance(), annotation.resetAssert());
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
    private void fieldTest(Method method, Field field, Object value, String des, AssertMethod assertMethod, String retAssert) {
        RequestData requestData = getRequestData(method);
        String targetPath = rootPath + field.getName();
        requestData.setParam(replaceValue(requestData.getParam(), targetPath, value));
        requestData.setStepDes(des);
        if (StringUtil.isNotEmpty(retAssert)) {
            AssertMethod retAssertMethod = (AssertMethod) baseCase.getClass().getMethod(retAssert).invoke(baseCase);
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
    private RequestData getRequestData(Method method) {
        RequestData requestData;
        if (method != null) {
            requestData = new RequestData((BaseCase) method.invoke(baseCase));
        } else {
            //当baseCase类中没有@BeforeMethod时new一个构造函数
            requestData = new RequestData(baseCase.getClass().getConstructor().newInstance());
        }
        baseCaseOld = baseCase;
        baseCase = baseCase.getClass().newInstance();
        return requestData;
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