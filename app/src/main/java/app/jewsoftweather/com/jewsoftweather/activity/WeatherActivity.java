package app.jewsoftweather.com.jewsoftweather.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import app.jewsoftweather.com.jewsoftweather.R;

/**
 * Created by Administrator on 2017-04-18.
 */

public class WeatherActivity extends Activity {

    private LinearLayout weatherInfoLayout;
    private TextView cityNameText;
    private TextView publishText;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_layout);
        //初始化控件
        weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
        cityNameText = (TextView) findViewById(R.id.city_name);
        publishText = (TextView) findViewById(R.id.publish_text);
    }
}
