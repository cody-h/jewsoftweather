package app.jewsoftweather.com.jewsoftweather.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import app.jewsoftweather.com.jewsoftweather.R;
import app.jewsoftweather.com.jewsoftweather.db.CoolWeatherDB;
import app.jewsoftweather.com.jewsoftweather.model.City;
import app.jewsoftweather.com.jewsoftweather.model.County;
import app.jewsoftweather.com.jewsoftweather.model.Province;
import app.jewsoftweather.com.jewsoftweather.util.Utility;

public class ChooseAreaActivity extends AppCompatActivity {

    private ListView listView;
    private TextView titleText;

    private List<String> dataList = new ArrayList<String>();

    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;
    /**
     * 省列表
     */
    private List<Province> provinceList;

    /**
     * 市列表
     */
    private List<City> cityList;
    /**
     * 县列表
     */
    private List<County> countyList;

    /**
     * 选中的省份
     */
    private Province selectedProvince;
    /**
     * 选中的城市
     */
    private City selectedCity;
    /**
     * 选中的县城
     */
    private City selectedCounty;

    /**
     * 当前选中的级别
     */
    private int currentLevel;
    private CoolWeatherDB coolWeatherDB;
    private ArrayAdapter<String> adapter;
    private InputStream is;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_area);

        listView = (ListView) findViewById(R.id.list_view);
        titleText = (TextView) findViewById(R.id.title_text);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        coolWeatherDB = CoolWeatherDB.getInstance(this);
        //初始化数据 将数据存到数据库
        is = this.getClass().getClassLoader().getResourceAsStream("assets/kk.xml");
        initData();
//        if(loadCityFromXml){
//            try {
//                Utility.handleCityResponse(coolWeatherDB,is);
//                loadCityFromXml = false;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE) {
                    selectedProvince = provinceList.get(position);
                    queryCities();
                } else if (currentLevel == LEVEL_CITY) {
                    selectedCity = cityList.get(position);
                    queryCounties();
                }
            }
        });
        queryProvinces(); //加载省级数据
    }

    //将XML文件的数据解析保存到数据库中
    private void initData() {
    }

    /**
     * 查询全国所有的省份，从数据库中获取，如果没有直接解析XML文件
     */
    private void queryProvinces() {

//
//        if (loadCountyFromXml){
//            try {
//                Utility.handleCountyResponce(coolWeatherDB,is);
//                loadCountyFromXml = false;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        provinceList = coolWeatherDB.loadProvince();
        if (provinceList.size() > 0) {
            dataList.clear();
            for (Province province : provinceList) {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText("中国");
            currentLevel = LEVEL_PROVINCE;
        } else {
            System.out.println("列表里没有数据**************");
            try {
                Utility.handleProvinceResponse(coolWeatherDB, is);
                queryProvinces();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void queryCities() {

        cityList = coolWeatherDB.loadCities(Integer.parseInt(selectedProvince.getProvinceCode()));
        if (cityList.size() > 0) {
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedProvince.getProvinceName());
            currentLevel = LEVEL_CITY;
        } else {
            try {
                Utility.handleCityResponse(coolWeatherDB, is);
                queryCities();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void queryCounties() {

        countyList = coolWeatherDB.loadCounties(Integer.parseInt(selectedCity.getCityCode()));
        if (countyList.size() > 0) {
            dataList.clear();
            for (County county : countyList) {
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedCity.getCityName());
            currentLevel = LEVEL_COUNTY;

        } else {
            try {
                Utility.handleCountyResponce(coolWeatherDB, is);
                queryCounties();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (currentLevel == LEVEL_COUNTY) {
            queryCities();
        } else if (currentLevel == LEVEL_CITY) {
            queryProvinces();
        } else {
            finish();
        }
    }
}
