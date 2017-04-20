package app.jewsoftweather.com.jewsoftweather.util;

/**
 * Created by Administrator on 2017-04-19.
 */

public interface HttpCallbackListener {
    void onFinish(String response);

    void  onError(Exception e);
}
