package cn.jinxi.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import cn.jinxi.R;
import cn.jinxi.network.BaseException;
import cn.jinxi.network.RequestListener;
import cn.jinxi.user.response.LoginResponse;

/**
 * Created by jiewang on 2015/10/18.
 */
public class LoginActivity extends  BaseActivity{
    private EditText emailEditText;
    private EditText pwdEditText;

    public static void Start(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("登录");
        emailEditText = (EditText) findViewById(R.id.login_email);
        pwdEditText = (EditText) findViewById(R.id.login_password);

        findViewById(R.id.login_loginbtn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                login();
            }
        });

        findViewById(R.id.login_registerbtn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //to do RegisterActivity
            }
        });

        findViewById(R.id.login_forgetpwdbtn).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //to do ForgetPwdActivity
            }
        });

        // 点击屏幕外边收起键盘
        findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });
        findViewById(R.id.root_inner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });

    }

    private void login(){
        hideSoftKeyboard();
        String email = emailEditText.getText().toString();
        String password = pwdEditText.getText().toString();
        if (email.isEmpty()) {
            showText("请输入正确的手机号码");
            return;
        }
        if (password.isEmpty()) {
            showText("请输入密码");
            return;
        }

        networkManager.login(email, password, new RequestListener<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse response) {
                showText("登录成功");
                finish();
            }

            @Override
            public void onFailure(BaseException exception, Throwable throwable) {
                //to do
            }
        });

    }
}
