package cn.jinxi.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

/**
 * Created by jiewang on 2015/10/18.
 */
public class DataUtils {

    private static Gson gson = new GsonBuilder().create();

    public static Gson GetGson() {
        return gson;
    }

    public static String Obj2JsonStr(Object obj) {
        if (obj == null) return null;
        return GetGson().toJson(obj);
    }

    public static Object JsonStr2Obj(String jsonStr, Class objClass) {
        if (jsonStr == null) return null;
        return GetGson().fromJson(jsonStr, objClass);
    }

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
