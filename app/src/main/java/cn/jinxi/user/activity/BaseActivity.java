package cn.jinxi.user.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import cn.jinxi.R;
import cn.jinxi.user.manager.EventManager;
import cn.jinxi.user.manager.ImageManager;
import cn.jinxi.user.manager.NetworkManager;

/**
 * Created by jiewang on 2015/10/18.
 */
public class BaseActivity extends Activity {
    protected NetworkManager networkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkManager = new NetworkManager(this);

        ImageManager.GetInstance(getApplicationContext());
        EventManager.GetInstance().getEventBus().register(this);
    }

    public void onEvent(String event) {
        Log.d("Event", "default handler");
    }

    public NetworkManager getNetworkManager(){
        return networkManager;
    }

    public void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        imm.showSoftInput(findViewById(R.id.root), InputMethodManager.SHOW_FORCED);
        imm.hideSoftInputFromWindow(findViewById(R.id.root).getWindowToken(), 0); //强制隐藏键盘
    }

    public void showText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void setTitle(String title) {

        TextView textView = (TextView) findViewById(R.id.header_title);
        if (title != null) {
            textView.setText(title);
        } else {
            textView.setText("");
        }
    }

    public ImageButton addBackBtn() {
        ImageButton back = (ImageButton) findViewById(R.id.header_leftbtn);
        back.setImageResource(R.drawable.bar_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        back.setVisibility(View.VISIBLE);
        return back;
    }

    public TextView getRightTextView() {
        TextView right = (TextView) findViewById(R.id.header_righttextbtn);
        right.setVisibility(View.VISIBLE);
        return right;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventManager.GetInstance().getEventBus().unregister(this);
    }
}
