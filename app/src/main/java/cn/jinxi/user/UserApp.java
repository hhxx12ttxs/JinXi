package cn.jinxi.user;

import android.app.Application;

/**
 * Created by jiewang on 2015/10/18.
 */
public class UserApp extends Application {
    private static UserApp instance;

    public static UserApp GetInstance(){
        return instance;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        this.instance = this;
    }
}
