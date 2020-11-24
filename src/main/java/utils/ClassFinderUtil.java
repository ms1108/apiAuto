package utils;

import base.BaseCase;
import lombok.SneakyThrows;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ClassFinderUtil {

    @SneakyThrows
    public List<Class<? extends BaseCase>> scanned(String scannedPackage) {
        String scannedPath = scannedPackage.replace('.', '/');
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
        if (scannedUrl == null) {
            throw new IllegalArgumentException(String.format("Unable to get resources from path '%s'. Are you sure the package '%s' exists?", scannedPath, scannedPackage));
        }
        File scannedDir = new File(scannedUrl.getFile());
        List<Class<? extends BaseCase>> classes = new ArrayList<>();
        for (File file : scannedDir.listFiles()) {
            String resource = scannedPackage + '.' + file.getName();
            if (resource.endsWith(".class") && !resource.contains("$")) {
                String className = resource.substring(0, resource.lastIndexOf("."));
                Class<?> aClass = Class.forName(className);
                if (aClass.newInstance() instanceof BaseCase) {
                    classes.add((Class<? extends BaseCase>) aClass);
                }
            }
        }
        return classes;
    }

}
