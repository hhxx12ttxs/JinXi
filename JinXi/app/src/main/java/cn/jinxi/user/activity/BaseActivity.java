package cn.jinxi.user.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import cn.jinxi.R;
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
}
