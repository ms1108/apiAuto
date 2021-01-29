package base;

import annotation.AnnotationServer;
import annotation.annotations.BeforeClassRun;
import annotation.annotations.BeforeMethodRun;
import business.loginTest.testcase.LoginCase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.ReportUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnnotationTest extends AnnotationServer {
    //business.loginTest.testcase格式
    private String packagePath;

    public AnnotationTest(String packagePath) {
        this.packagePath = packagePath.trim();
    }

    @DataProvider
    public Object[][] executeAnnotationAble() {
        return getDataProvider();//bject[][] objects = {{Class<? extends BaseCase>,"beforeMethodName;annotationNameOnField"}};构造成这种格式
    }

    @Test(dataProvider = "executeAnnotationAble")
    public void annotationTest(String executeAnnotationName, Class<? extends BaseCase> baseCase) {
        ReportUtil.log("PackagePath       :  " + packagePath);
        ReportUtil.log("BaseCase          :  " + baseCase.getSimpleName());
        ReportUtil.log("MethodOrFieldName :  " + executeAnnotationName.split(",")[0]);
        ReportUtil.log("AnnotationName    :  " + executeAnnotationName.split(",")[1]);
        //换行
        ReportUtil.log("");
        annotationServer(baseCase, executeAnnotationName);
    }

    //@Test可用来调试
    public void annotation() {
        annotationServer(LoginCase.class, "MultiRequest");
    }

    private Object[][] getDataProvider() {
        List<List<Object>> allCase = new ArrayList<>();
        List<Class<? extends BaseCase>> baseCaseName = getBaseCaseName(packagePath);
        for (int i = 0; i < baseCaseName.size(); i++) {
            Class<? extends BaseCase> baseCaseClass = baseCaseName.get(i);
            Map<String, List<String>> methodNameAndAnnotationName = getMethodNameAndAnnotationName(baseCaseClass);
            Map<String, List<String>> fieldNameAndAnnotationName = getFieldNameAndAnnotationName(baseCaseClass);

            //BeforeClassRun单独处理，每个class的BeforeClassRun率先执行
            methodNameAndAnnotationName.forEach((k, v) -> {
                if (v.contains(BeforeClassRun.class.getSimpleName())) {
                    List<Object> baseCaseAndFieldAnnotationName = new ArrayList<>();
                    baseCaseAndFieldAnnotationName.add(k + "," + BeforeClassRun.class.getSimpleName());
                    baseCaseAndFieldAnnotationName.add(baseCaseClass);
                    allCase.add(baseCaseAndFieldAnnotationName);
                }
            });


            //当存在字段类的注解时必然存在BeforeMethodRun
            fieldNameAndAnnotationName.forEach((k, v) -> {
                for (int j = 0; j < v.size(); j++) {
                    List<Object> baseCaseAndFieldAnnotationName = new ArrayList<>();
                    baseCaseAndFieldAnnotationName.add(k + "," + v.get(j) + "," + BeforeMethodRun.class.getSimpleName());
                    baseCaseAndFieldAnnotationName.add(baseCaseClass);
                    allCase.add(baseCaseAndFieldAnnotationName);
                }
            });

            methodNameAndAnnotationName.forEach((k, v) -> {
                for (int j = 0; j < v.size(); j++) {
                    List<Object> baseCaseAndFieldAnnotationName = new ArrayList<>();
                    String annotationName = v.get(j);
                    //BeforeClassRun率先处理了，BeforeMethodRun配合字段使用了
                    if (annotationName.equals(BeforeMethodRun.class.getSimpleName())
                            || annotationName.equals(BeforeClassRun.class.getSimpleName())) {
                        continue;
                    }
                    baseCaseAndFieldAnnotationName.add(k + "," + annotationName);
                    baseCaseAndFieldAnnotationName.add(baseCaseClass);
                    allCase.add(baseCaseAndFieldAnnotationName);
                }
            });
        }
        //将List转成object[][],后边的括号定义内层元素
        Object[][] array = new Object[allCase.size()][2];
        for (int i = 0; i < allCase.size(); i++) {
            for (int j = 0; j < allCase.get(i).size(); j++) {
                if (j == 0) {
                    array[i][j] = (String) allCase.get(i).get(j);
                } else {
                    array[i][j] = (Class<? extends BaseCase>) allCase.get(i).get(j);
                }
            }
        }
        return array;
    }
}
// Class<? extends BaseCase> baseCaseClass = baseCaseName.get(i);
//            List<String> annotationNameOnMethod = getAnnotationNameOnMethod(baseCaseClass);
//            List<String> annotationNameOnField = getAnnotationNameOnField(baseCaseClass);
//
//            //BeforeClassRun单独处理
//            if (annotationNameOnMethod.contains(BeforeClassRun.class.getSimpleName())) {
//                annotationNameOnMethod.remove(annotationNameOnMethod.indexOf(BeforeClassRun.class.getSimpleName()));
//                List<Object> baseCaseAndFieldAnnotationName = new ArrayList<>();
//                baseCaseAndFieldAnnotationName.add(BeforeClassRun.class.getSimpleName());
//                baseCaseAndFieldAnnotationName.add(baseCaseClass);
//                allCase.add(baseCaseAndFieldAnnotationName);
//            }
//
//            //BeforeMethodRun名称和字段的注解名称
//            for (int j = 0; j < annotationNameOnField.size(); j++) {
//                List<Object> baseCaseAndFieldAnnotationName = new ArrayList<>();
//                baseCaseAndFieldAnnotationName.add(annotationNameOnField.get(j) + ";" + BeforeMethodRun.class.getSimpleName());
//                baseCaseAndFieldAnnotationName.add(baseCaseClass);
//                allCase.add(baseCaseAndFieldAnnotationName);
//            }
//
//            //BeforeMethodRun配合字段使用的，已被处理
//            if (annotationNameOnMethod.contains(BeforeMethodRun.class.getSimpleName())) {
//                annotationNameOnMethod.remove(annotationNameOnMethod.indexOf(BeforeMethodRun.class.getSimpleName()));
//            }
//
//            for (int j = 0; j < annotationNameOnMethod.size(); j++) {
//                List<Object> baseCaseAndFieldAnnotationName = new ArrayList<>();
//                baseCaseAndFieldAnnotationName.add(annotationNameOnMethod.get(j));
//                baseCaseAndFieldAnnotationName.add(baseCaseClass);
//                allCase.add(baseCaseAndFieldAnnotationName);
//            }