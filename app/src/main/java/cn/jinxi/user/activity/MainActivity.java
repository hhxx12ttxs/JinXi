package cn.jinxi.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import cn.jinxi.R;

/**
 * Created by jiewang on 2015/10/18.
 */
public class MainActivity extends BaseActivity {

    public static void Start(Context context){
        //to do judge login
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //to do judge login or first open
                //LoginActivity.Start(MainActivity.this);
                FirstLoginActivity.Start(MainActivity.this);
                finish();
            }
        }, 2000);

    }
}
