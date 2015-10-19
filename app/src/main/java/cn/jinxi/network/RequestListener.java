package cn.jinxi.network;

/**
 * Created by jiewang on 2015/10/18.
 */
public interface RequestListener<JsonClass extends BaseResponse> {
    public void onSuccess(JsonClass response);

    public void onFailure(BaseException exception, Throwable throwable);
}
