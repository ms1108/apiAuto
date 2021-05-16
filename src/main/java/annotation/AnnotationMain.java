package annotation;

import component.loginTest.testcase.ListCase;
import org.testng.annotations.Test;
import utils.ReportUtil;

public class AnnotationMain {
    //可用来调试
    @Test
    public void annotation(){
        ReportUtil.clearLogs();
        AnnotationServer annotationServer = new AnnotationServer();
        //annotationServer.annotationServer(LoginCase.class,"isManage,EnumInt,BeforeMethodRun");
        annotationServer.annotationServer(ListCase.class,"search,Search,beforeClass,BeforeMethodRun,BeforeClassRun");
        //annotationTest.annotationTest("business.loginTest.testcase");
    }
}
