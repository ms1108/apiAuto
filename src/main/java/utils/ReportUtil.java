package utils;

import org.testng.Reporter;

public class ReportUtil {
    public static void log(String msg) {
        Reporter.log(msg, true);
    }
    public static void log(String msg , boolean isPrint) {
        Reporter.log(msg, isPrint);
    }
}
