package cn.jinxi.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import cn.jinxi.R;

/**
 * Created by jiewang on 2015/10/18.
 */
public class RegisterActivity extends BaseActivity{
    private EditText schoolEditText;
    private EditText emailText;
    private EditText pwdText;
    private EditText confirmPwdText;

    public static void Start(Context context){
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        addBackBtn();
        setTitle("注册");

        confirmPwdText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    register();
                }
                return false;
            }
        });

        // 点击屏幕外边收起键盘
        findViewById(R.id.root_inner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });
    }

    private void register(){
        hideSoftKeyboard();
    }
}
