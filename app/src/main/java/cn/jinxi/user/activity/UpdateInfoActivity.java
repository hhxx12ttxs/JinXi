package cn.jinxi.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import cn.jinxi.R;
import cn.jinxi.user.consts.EventConsts;
import cn.jinxi.user.manager.EventManager;
import cn.jinxi.user.manager.vo.InfoDelivery;
import cn.jinxi.utils.DataUtils;

/**
 * Created by jiewang on 2015/10/19.
 */
/*
    public String name;
    public String signature;

    public String school;
    public String degree;
    public String grade;

    public String email;
    public String qq;
    public String wechat;
    public String phone;
 */
public class UpdateInfoActivity extends  BaseActivity{
    public static final int INTENT_TYPE_SEX = 0;
    public static final int INTENT_TYPE_NAME = 1;
    public static final int INTENT_TYPE_SIGNATURE = 2;
    public static final int INTENT_TYPE_SCHOOL = 3;
    public static final int INTENT_TYPE_DEGREE = 4;
    public static final int INTENT_TYPE_GRADE = 5;
    public static final int INTENT_TYPE_EMAIL = 6;
    public static final int INTENT_TYPE_QQ = 7;
    public static final int INTENT_TYPE_WECHAT = 8;
    public static final int INTENT_TYPE_PHONE = 9;

    private static final String INTENT_TYPE = "INTENT_TYPE";
    private static final String INTENT_FIRST_EDIT = "INTENT_FIRST_EDIT";
    private static final String INTENT_INFO = "INTENT_INFO";

    private int infoType;
    private boolean firstEDIT;
    private InfoDelivery infoDelivery;

    private EditText editText;
    private TextView rightBtn;

    public static void Start(Context context, int type){
        Intent intent = new Intent(context, UpdateInfoActivity.class);
        intent.putExtra(INTENT_TYPE, type);
        context.startActivity(intent);
    }

    public static void Start(Context context, int type, InfoDelivery infoDelivery) {
        Intent intent = new Intent(context, UpdateInfoActivity.class);
        intent.putExtra(INTENT_TYPE, type);
        intent.putExtra(INTENT_FIRST_EDIT, true);
        intent.putExtra(INTENT_INFO, DataUtils.Obj2JsonStr(infoDelivery));
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateinfo);

        infoType = getIntent().getIntExtra(INTENT_TYPE, INTENT_TYPE_SEX);
        firstEDIT = getIntent().getBooleanExtra(INTENT_FIRST_EDIT, false);
        if (firstEDIT) {
            String infoStr = getIntent().getStringExtra(INTENT_INFO);
            infoDelivery = (InfoDelivery) DataUtils.JsonStr2Obj(infoStr, InfoDelivery.class);
        }
        // view
        initView();
    }

    public void initView() {
        addBackBtn();
        rightBtn = getRightTextView();
        rightBtn.setText("保存");

        editText = (EditText) findViewById(R.id.updateinfo_info);

        if (infoType == INTENT_TYPE_NAME) {
            UpdateUserName();
        }

        // 点击屏幕外边收起键盘
        findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });
    }

    public void UpdateUserName(){
        setTitle("姓名");
        editText.setVisibility(View.VISIBLE);
        if(firstEDIT){
            if(infoDelivery.name != null){
                editText.setText(infoDelivery.name);
                editText.setSelection(infoDelivery.name.length());
            }
        }

        rightBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String content = editText.getText().toString();
                if(firstEDIT){
                    infoDelivery.name = content;
                    EventManager.GetInstance().post(new EventConsts.InfoDeliveryEvent().setData(infoDelivery));
                    finish();
                    return;
                }
            }
        });


    }
}
