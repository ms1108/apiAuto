package utils;

import java.io.*;

public class FileUtil {
    public static boolean writeFile(InputStream is, String filePath) {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream fileout;
        try {
            fileout = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.print("==========" + e.toString());
            return false;
        }
        /**
         * 根据实际运行效果 设置缓冲区大小
         */
        byte[] buffer = new byte[10 * 1024];
        int ch = 0;
        try {
            while ((ch = is.read(buffer)) != -1) {
                fileout.write(buffer, 0, ch);
            }
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } finally {
            try {
                is.close();
                fileout.flush();
                fileout.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static String getNewestFilePathInDownloadDir() {
        File file = new File("/download");
        if (file != null && file.isDirectory()) {
            File[] list = file.listFiles();
            int size = list.length;
            if (size > 0) {
                String currentFilePath = list[0].getAbsolutePath();
                long lastModify = list[0].lastModified();

                for(int i = 1; i < size; ++i) {
                    File currentFile = list[i];
                    if (currentFile.lastModified() > lastModify) {
                        currentFilePath = currentFile.getAbsolutePath();
                        lastModify = currentFile.lastModified();
                    }
                }
                return currentFilePath;
            }
        }
        return "";
    }
}
