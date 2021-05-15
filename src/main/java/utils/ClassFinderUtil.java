package utils;

import base.BaseCase;
import lombok.SneakyThrows;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClassFinderUtil {

    @SneakyThrows
    public List<String> scanPackage(String scannedPackage) {
        String scannedPath = scannedPackage.replace('.', '/');
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
        if (scannedUrl == null) {
            throw new IllegalArgumentException(String.format("Unable to get resources from path '%s'. Are you sure the package '%s' exists?", scannedPath, scannedPackage));
        }
        File scannedDir = new File(scannedUrl.getFile());
        List<String> classNames = new ArrayList<>();
        for (File file : Objects.requireNonNull(scannedDir.listFiles())) {
            String fileName = file.getName();
            //不包含$符，即排除内部类
            if (fileName.endsWith(".class") && !fileName.contains("$")) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                classNames.add(className);
            }
        }
        return classNames;
    }

    //扫描case包
    @SneakyThrows
    public List<Class<? extends BaseCase>> scanBaseCaseClass(String scannedPackage) {
        List<Class<? extends BaseCase>> classes = new ArrayList<>();
        List<String> classNames = scanPackage(scannedPackage);
        for (String className : classNames) {
            String resource = scannedPackage + '.' + className;
            Class<?> aClass = Class.forName(resource);
            if (aClass.newInstance() instanceof BaseCase) {
                classes.add((Class<? extends BaseCase>) aClass);
            }
        }
        return classes;
    }

    public static void main(String[] args) {
        ClassFinderUtil classFinderUtil = new ClassFinderUtil();
        System.out.println(classFinderUtil.scanPackage("annotation.annotations"));
        //System.out.println(classFinderUtil.scanned("business.loginTest.testcase"));
    }

}
