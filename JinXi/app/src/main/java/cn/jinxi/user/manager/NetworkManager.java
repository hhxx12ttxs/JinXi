package cn.jinxi.user.manager;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import cn.jinxi.network.RequestListener;
import cn.jinxi.user.response.LoginResponse;

/**
 * Created by jiewang on 2015/10/18.
 */
public class NetworkManager {
    private AsyncHttpClient asyncHttpClient;
    private Context context;

    public NetworkManager(Context context) {
        this.context = context;
        asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.setConnectTimeout(60000);
        asyncHttpClient.setResponseTimeout(30000);
    }

    private RequestParams getDefaultParams() {
        RequestParams params = new RequestParams();
        String token = UserManager.GetInstance().getToken();
        if (token != null) {
            params.put("token", token);
        }
        return params;
    }

    public void login(String mobile, String password, RequestListener<LoginResponse> listener) {
        RequestParams params = getDefaultParams();
        params.put("mobile", mobile);
        params.put("password", password);
        //new AsyncHttpClientWarper<LoginResponse>(asyncHttpClient, context).startPostRequest(fullUrl(NetworkConsts.LOGIN), params, LoginResponse.class, listener);
    }
}
