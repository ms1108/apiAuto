package annotation;

import business.loginTest.testcase.LoginCase;
import org.testng.annotations.Test;

public class AnnotationMain {
    @Test
    public void annotation(){
        AnnotationServer annotationServer = new AnnotationServer();
        annotationServer.annotationServer(LoginCase.class,"isManage,EnumInt,BeforeMethodRun");
        //annotationTest.annotationTest("business.loginTest.testcase");
    }
}
