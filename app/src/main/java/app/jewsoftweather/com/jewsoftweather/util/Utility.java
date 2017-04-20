package app.jewsoftweather.com.jewsoftweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import app.jewsoftweather.com.jewsoftweather.db.CoolWeatherDB;
import app.jewsoftweather.com.jewsoftweather.model.City;
import app.jewsoftweather.com.jewsoftweather.model.County;
import app.jewsoftweather.com.jewsoftweather.model.Province;


/**
 * Created by Administrator on 2017-04-15.
 */

public class Utility {
    /**
     * 将XML解析出来的省级数据保存到服务器
     */
    public   static void handleProvinceResponse(CoolWeatherDB coolWeatherDB,InputStream is) throws Exception {
        Xml2Province xml2Province = new Xml2Province();
        List<Province> provinceList = xml2Province.parse(is);
        for (Province province :
             provinceList) {
            //将解析出来的数据储存到Provicne表中
            coolWeatherDB.saveProvince(province);
        }
    }

    /**
     * 将XML解析出来的市级数据保存到服务器
     */
    public   static void handleCityResponse(CoolWeatherDB coolWeatherDB,InputStream is) throws Exception {
        Xml2City xml2City = new Xml2City();
        List<City> cityList = xml2City.parse(is);
        for (City city :
             cityList) {
            coolWeatherDB.saveCity(city);
        }
    }

    /**
     * 将XML解析出来的县级数据保存到服务器
     */

    public   static void handleCountyResponce(CoolWeatherDB coolWeatherDB,InputStream is) throws Exception {
        Xml2County xml2County = new Xml2County();
        List<County> countyList = xml2County.parser2county(is);
        for (County county:
             countyList) {
            coolWeatherDB.saveCounty(county);
        }
    }

    /**
     * 解析服务器返回的JSON数据，并将解析出来的数据储存到本地
     */
    public static void handleWeatherResponse(Context context,String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherinfo = jsonObject.getJSONObject("weatherinfo");
            String cityName = weatherinfo.getString("city");
            String weatherCode = weatherinfo.getString("cityid");
            String temp1 = weatherinfo.getString("temp1");
            String temp2 = weatherinfo.getString("temp2");
            String weatherDesp = weatherinfo.getString("weather");
            String publishTime = weatherinfo.getString("ptime");
            saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesp,publishTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveWeatherInfo(Context context, String cityName, String weatherCode, String temp1, String temp2, String weatherDesp, String publishTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CANADA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected",true);
        editor.putString("city_name",cityName);
        editor.putString("weather_code",weatherCode);
        editor.putString("temp1",temp1);
        editor.putString("temp2",temp2);
        editor.putString("weather_desp",weatherDesp);
        editor.putString("weather_time",publishTime);
        editor.putString("current_date",sdf.format(new Date()));
        editor.commit();
    }
}
