package cn.jinxi.user.manager;

import android.os.Environment;

import cn.jinxi.user.UserApp;
import cn.jinxi.user.response.vo.User;

/**
 * Created by jiewang on 2015/10/18.
 */
public class GlobalSettingManager {
    public static String GetFilePath() {
        return UserApp.GetInstance().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/";
    }

    public static String GetCachePath() {
        return UserApp.GetInstance().getExternalCacheDir().getAbsolutePath() + "/";
    }
}
