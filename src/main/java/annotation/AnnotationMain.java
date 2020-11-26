package annotation;

import business.loginTest.testcase.LoginCase;

public class AnnotationMain {
    public static void main(String[] args) {
        AnnotationTest annotationTest = new AnnotationTest();
        annotationTest.annotationTest(LoginCase.class);
        //annotationTest.annotationTest("business.loginTest.testcase");
    }
}
