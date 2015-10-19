package cn.jinxi.user.manager;

import android.content.Context;

/**
 * Created by jiewang on 2015/10/18.
 */
public class UserManager {
    private static UserManager instance;
    private String token;
    private Context context;

    private UserManager(Context context){
        this.context = context;
    }

    public static UserManager GetInstance(Context context){
        if(instance == null){
            synchronized (UserManager.class){
                if(instance == null){
                    instance = new UserManager(context);
                }
            }
        }
        instance.context = context;
        return instance;
    }

    public static UserManager GetInstance(){
        return instance;
    }

    public String getToken(){
        if(token == null){
            //to do
        }
        return token;
    }
}
