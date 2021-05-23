package base;

import annotation.AnnotationServer;
import annotation.annotations.DataDepend;
import annotation.annotations.BaseCaseData;
import lombok.SneakyThrows;
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
        return getDataProvider();
    }

    @Test(dataProvider = "executeAnnotationAble")
    public void annotationTest(String executeAnnotationName, Class<? extends BaseCase> baseCase) {
        //预置日志前先清空，万一该用例空转，也就是没有发送接口，则导致下边的预置日志带到下一个用例中，所以先清空。
        ReportUtil.clearLogs();
        ReportUtil.setPreLog("PackagePath       : " + packagePath);
        ReportUtil.setPreLog("BaseCase          : " + baseCase.getSimpleName());
        ReportUtil.setPreLog("MethodOrFieldName : " + executeAnnotationName.split(",")[0]);
        ReportUtil.setPreLog("AnnotationName    : " + executeAnnotationName.split(",")[1]);
        //换行
        ReportUtil.setPreLog("");
        annotationServer(baseCase, executeAnnotationName);
    }

    //构造成这种格式:object[][] objects = {{"baseCaseData的方法名,annotation名称在该字段上的",Class<? extends BaseCase>}};
    @SneakyThrows
    private Object[][] getDataProvider() {
        List<List<Object>> allCase = new ArrayList<>();
        List<Class<? extends BaseCase>> baseCaseName = getBaseCaseName(packagePath);
        for (int i = 0; i < baseCaseName.size(); i++) {
            Class<? extends BaseCase> baseCaseClass = baseCaseName.get(i);
            Map<String, List<String>> methodNameAndAnnotationName = getMethodNameAndAnnotationName(baseCaseClass);
            Map<String, List<String>> fieldNameAndAnnotationName = getFieldNameAndAnnotationName(baseCaseClass);

            //DataDepend单独处理，每个class的DataDepend率先执行，构造成{方法名，DataDepend，BaseCase}
            for (Map.Entry<String, List<String>> entry : methodNameAndAnnotationName.entrySet()) {
                //该方法上有DataDepend注解的话，则先加入列表
                if (entry.getValue().contains(DataDepend.class.getSimpleName())) {
                    //如果该注解信息为true则不用在这里将DataDepend信息加入到allCase，也就不用单独调用一次DataDepend方法
                    if (!baseCaseClass.getMethod(entry.getKey()).getAnnotation(DataDepend.class).value()) {
                        List<Object> baseCaseAndFieldAnnotationName = new ArrayList<>();
                        baseCaseAndFieldAnnotationName.add("执行数据依赖方法名称：" + entry.getKey() + "," + "注解名称：" + DataDepend.class.getSimpleName());
                        baseCaseAndFieldAnnotationName.add(baseCaseClass);
                        allCase.add(baseCaseAndFieldAnnotationName);
                    }
                }
            }

            //当存在字段类的注解时必然存在baseCaseData,构造成{字段名，注解名，baseCaseData，BaseCase}
            fieldNameAndAnnotationName.forEach((k, v) -> {
                for (int j = 0; j < v.size(); j++) {
                    List<Object> baseCaseAndFieldAnnotationName = new ArrayList<>();
                    baseCaseAndFieldAnnotationName.add("测试的字段名称：" + k + "," + "该字段上的注解名称：" + v.get(j) + "," + "基础数据注解名称：" + BaseCaseData.class.getSimpleName());
                    baseCaseAndFieldAnnotationName.add(baseCaseClass);
                    allCase.add(baseCaseAndFieldAnnotationName);
                }
            });
            //处理其他的注解，如@AutoTest，构造成{方法名，注解名，BaseCase}
            methodNameAndAnnotationName.forEach((k, v) -> {
                for (int j = 0; j < v.size(); j++) {
                    List<Object> baseCaseAndFieldAnnotationName = new ArrayList<>();
                    String annotationName = v.get(j);
                    //DataDepend率先处理了，baseCaseData配合字段使用了
                    if (annotationName.equals(BaseCaseData.class.getSimpleName())
                            || annotationName.equals(DataDepend.class.getSimpleName())) {
                        continue;
                    }
                    baseCaseAndFieldAnnotationName.add("测试的方法名称："+k + "," + "注解名称："+annotationName);
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