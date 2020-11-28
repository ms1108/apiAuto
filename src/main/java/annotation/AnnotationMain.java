package annotation;

import business.loginTest.testcase.LoginCase;
import org.testng.annotations.Test;

public class AnnotationMain {
    @Test
    public void annotation(){
        AnnotationTest annotationTest = new AnnotationTest();
        annotationTest.annotationTest(LoginCase.class);
        //annotationTest.annotationTest("business.loginTest.testcase");
    }
}
