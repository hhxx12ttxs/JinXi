package cn.jinxi.user.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.jinxi.R;
import cn.jinxi.user.consts.EventConsts;
import cn.jinxi.user.manager.ImageManager;
import cn.jinxi.user.manager.vo.InfoDelivery;
import cn.jinxi.user.response.vo.User;
import de.greenrobot.event.EventBus;

/**
 * Created by jiewang on 2015/10/18.
 */
public class FirstLoginActivity extends BaseActivity {
    public static final int INTENT_TYPE_TAKEPHOTO = 99001;
    public static final int INTENT_TYPE_PHOTOALBUM = 99002;

    private Byte sex;
    private InfoDelivery infoDelivery;

    private ImageView portrait;
    private Button sexMaleBtn;
    private Button sexFemaleBtn;

    public static void Start(Context context) {
        Intent intent = new Intent(context, FirstLoginActivity.class);
        context.startActivity(intent);
    }

    public void onEventMainThread(EventConsts.InfoDeliveryEvent event) {
        InfoDelivery infoDelivery = event.getData();
        if (infoDelivery.name != null) {
            this.infoDelivery.name = infoDelivery.name;
        }

        updateInfoView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstlogin);

        infoDelivery = new InfoDelivery();

        setTitle("编辑");
        TextView rightTextView = getRightTextView();
        rightTextView.setText("跳过");
        rightTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //to do
            }
        });

        portrait = (ImageView) findViewById((R.id.firstlogin_portrait));
        sexMaleBtn = (Button) findViewById(R.id.firstlogin_sex_male);
        sexMaleBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                changeSex(User.SEX_MALE);
            }
        });

        sexFemaleBtn = (Button) findViewById(R.id.firstlogin_sex_female);
        sexFemaleBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                changeSex(User.SEX_FEMALE);
            }
        });

        findViewById(R.id.firstlogin_portrait_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                portrait.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(FirstLoginActivity.this);
                        final String[] items = {"拍照", "从相册中选择"};
                        builder.setItems(items,
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        File file = new File(ImageManager.GetInstance().getPortraitPath());
                                        if (file.exists()) {
                                            file.delete();
                                        }
                                        if (which == 0) {
                                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                            intent.putExtra("output", Uri.fromFile(file));
                                            startActivityForResult(intent, INTENT_TYPE_TAKEPHOTO);
                                        } else if (which == 1) {
                                            Intent intent = new Intent();
                                            intent.setType("image/*");
                                            intent.setAction(Intent.ACTION_GET_CONTENT);
                                            startActivityForResult(intent, INTENT_TYPE_PHOTOALBUM);
                                        }
                                    }
                                });
                        builder.create().show();
                    }
                });
            }
        });

        setInfoListener(R.id.firstlogin_name, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateInfoActivity.Start(FirstLoginActivity.this, UpdateInfoActivity.INTENT_TYPE_NAME, infoDelivery);
            }
        });

        findViewById(R.id.firstlogin_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfo();
            }
        });

        updateInfoView();
    }

    private void changeSex(Byte sexSelected) {
        this.sex = sexSelected;
        if(sexSelected == User.SEX_MALE){
            sexMaleBtn.setSelected(true);
        }
        else if (sexSelected == User.SEX_FEMALE){
            sexFemaleBtn.setSelected(true);
        }
    }

    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_TYPE_TAKEPHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                ImageManager.GetInstance().compressImage(ImageManager.GetInstance().getPortraitPath());
                showPortrait(ImageManager.GetInstance().getPortraitPath());
                uploadPortrait();
            }
        } else if (requestCode == INTENT_TYPE_PHOTOALBUM) {
            if (resultCode == Activity.RESULT_OK) {
                Uri originalUri = data.getData();        //获得图片的uri
                try {
                    Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), originalUri);
                    FileOutputStream outputStream = new FileOutputStream(new File(ImageManager.GetInstance().getPortraitPath()));
                    bmp.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    bmp.recycle();
                } catch (IOException e) {
                    e.printStackTrace();
                    showText("获取图片失败");
                    return;
                }
                ImageManager.GetInstance().compressImage(ImageManager.GetInstance().getPortraitPath());
                showPortrait(ImageManager.GetInstance().getPortraitPath());
                uploadPortrait();
            }
        }
    }

    private void showPortrait(String path){
        File file = new File(path);
        if(file.exists()){
            Bitmap bm = BitmapFactory.decodeFile(path);
            portrait.setImageBitmap(bm);
        }
    }

    private void uploadPortrait() {
       //to do
    }

    private void updateInfo(){
        //to do
    }

    private void updateInfoView() {
        updateInfoItem(R.id.firstlogin_name, "姓名", infoDelivery.name);
        updateInfoItem(R.id.firstlogin_signature, "个性签名", infoDelivery.signature);

        updateInfoItem(R.id.firstlogin_school, "学校", infoDelivery.school);
        updateInfoItem(R.id.firstlogin_degree, "身份", infoDelivery.degree);
        updateInfoItem(R.id.firstlogin_grade, "年份", infoDelivery.grade);

        updateInfoItem(R.id.firstlogin_email, "邮箱", infoDelivery.email);
        updateInfoItem(R.id.firstlogin_qq, "QQ", infoDelivery.qq);
        updateInfoItem(R.id.firstlogin_wechat, "微信", infoDelivery.wechat);
        updateInfoItem(R.id.firstlogin_phone, "手机", infoDelivery.phone);
    }

    private void updateInfoItem(int itemId, String title, String data) {
        View item = findViewById(itemId);
        TextView titleView = (TextView) item.findViewById(R.id.item_myinfo_title);
        titleView.setText(title);

        TextView dataView = (TextView) item.findViewById(R.id.item_myinfo_data);
        if (data == null) {
            dataView.setText("未设置");
        } else {
            dataView.setText(data);
        }

        View mark = item.findViewById(R.id.item_myinfo_mark);
        mark.setVisibility(View.GONE);
    }

    private void setInfoListener(int itemId, View.OnClickListener listener){
        findViewById(itemId).setOnClickListener(listener);
    }
}
