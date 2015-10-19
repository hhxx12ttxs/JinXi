package cn.jinxi.utils;

import java.io.File;

/**
 * Created by jiewang on 2015/10/18.
 */
public class DataUtils {

    public static long GetFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + GetFolderSize(fileList[i]);

                } else {
                    size = size + fileList[i].length();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        return size;
    }
}
