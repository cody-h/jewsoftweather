package app.jewsoftweather.com.jewsoftweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.jewsoftweather.com.jewsoftweather.R;
import app.jewsoftweather.com.jewsoftweather.receiver.AutoUpdateReceiver;
import app.jewsoftweather.com.jewsoftweather.util.HttpCallbackListener;
import app.jewsoftweather.com.jewsoftweather.util.HttpUtil;
import app.jewsoftweather.com.jewsoftweather.util.Utility;

/**
 * Created by Administrator on 2017-04-18.
 */

public class WeatherActivity extends Activity implements View.OnClickListener {

    private LinearLayout weatherInfoLayout;
    private TextView cityNameText;
    private TextView publishText,weatherDespText,temp1Text,temp2Text,currentDateText;
    private Button switchCity;
    private Button refreshWeather;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_layout);
        //初始化控件
        weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
        cityNameText = (TextView) findViewById(R.id.city_name);
        publishText = (TextView) findViewById(R.id.publish_text);
        weatherDespText = (TextView) findViewById(R.id.weather_desp);
        temp1Text = (TextView) findViewById(R.id.temp1);
        temp2Text = (TextView) findViewById(R.id.temp2);
        currentDateText = (TextView) findViewById(R.id.current_date);
        switchCity = (Button) findViewById(R.id.switch_city);
        refreshWeather = (Button) findViewById(R.id.refresh_weather);

        switchCity.setOnClickListener(this);
        refreshWeather.setOnClickListener(this);


        String weatherCode = getIntent().getStringExtra("weather_code");
        System.out.println("天气预报代码weatherCode:"+weatherCode);
        if(!TextUtils.isEmpty(weatherCode)){
            //天气代号时就去查询天气
            publishText.setText("同步中...");
            weatherInfoLayout.setVisibility(View.VISIBLE);
            cityNameText.setVisibility(View.VISIBLE);
            queryWeatherInfo(weatherCode);
        }else{
            //如果保存有本地的天气 就直接显示
            showWeather();
        }
    }

    //查询天气信息
    private void queryWeatherInfo(String weatherCode) {
        //http://www.weather.com.cn/data/cityinfo/101010100.html
        String address = "http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
        System.out.println("address:----"+address);
        queryFromServer(address);
    }

    private void queryFromServer(String address) {
    HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
    @Override
    public void onFinish(String response) {
        Utility.handleWeatherResponse(WeatherActivity.this,response);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showWeather();
            }
        });
    }

    @Override
    public void onError(Exception e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                publishText.setText("同步失败");
            }
        });
        System.out.println("同步失败----------");

    }
});
    }

    private void showWeather() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        cityNameText.setText(prefs.getString("city_name",""));
        temp1Text.setText(prefs.getString("temp1",""));
        temp2Text.setText(prefs.getString("temp2",""));
        weatherDespText.setText(prefs.getString("weather_desp",""));
        publishText.setText("今天"+prefs.getString("weather_time","")+"发布");
        currentDateText.setText(prefs.getString("current_date",""));
        cityNameText.setVisibility(View.VISIBLE);

        String cityName = prefs.getString("city_name","");
        System.out.println("--------------"+cityName+"---"+prefs.getString("weather_code",""));

        Intent intent = new Intent(this, AutoUpdateReceiver.class);
        startService(intent);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.switch_city:
                Intent intent = new Intent(this, ChooseAreaActivity.class);
                intent.putExtra("from_weather_activity",true);
                startActivity(intent);
                finish();
                break;
            case R.id.refresh_weather:
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                String weatherCode = prefs.getString("weather_code", "");
                if(!TextUtils.isEmpty(weatherCode)){
                    queryWeatherInfo(weatherCode);
                }
                break;
        }
    }
}
