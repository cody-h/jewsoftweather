package app.jewsoftweather.com.jewsoftweather;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import app.jewsoftweather.com.jewsoftweather.db.CoolWeatherDB;
import app.jewsoftweather.com.jewsoftweather.model.City;
import app.jewsoftweather.com.jewsoftweather.model.County;
import app.jewsoftweather.com.jewsoftweather.model.Province;
import app.jewsoftweather.com.jewsoftweather.util.Utility;
import app.jewsoftweather.com.jewsoftweather.util.Xml2City;
import app.jewsoftweather.com.jewsoftweather.util.Xml2County;
import app.jewsoftweather.com.jewsoftweather.util.Xml2Province;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("app.jewsoftweather.com.jewsoftweather", appContext.getPackageName());
    }

    @Test
    public  void pullProvince() throws Exception {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("assets/kk.xml");
        Xml2Province xml2Province = new Xml2Province();
        List<Province> provinces = xml2Province.parse(is);

        for (Province province : provinces) {
            System.out.println("provinceCode:"+province.getProvinceCode());
            System.out.println("provinceName:"+province.getProvinceName());
        }

    }

    @Test
    public void pullCity() throws Exception {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("assets/kk.xml");
        Xml2City xml2City = new Xml2City();
        List<City> cities = xml2City.parse(is);

        for (City city: cities) {
            System.out.println("provinceId；"+city.getProvinceId()+"-"+"cityCode:"+city.getCityCode()+"-"+"cityName:"+city.getCityName());
        }
    }

   @Test
    public void pullCounty() throws Exception {
       InputStream is = this.getClass().getClassLoader().getResourceAsStream("assets/kk.xml");
       Xml2County xml2County = new Xml2County();
       List<County> counties = xml2County.parser2county(is);

       for (County county: counties
            ) {
           System.out.println("countyCode:"+county.getCountyCode()+"--"+"countyName"+county.getCountyName());
       }
   }

   @Test
    public void testProvicne(Context context) throws Exception {
       InputStream is = this.getClass().getClassLoader().getResourceAsStream("assets/kk.xml");
       CoolWeatherDB coolWeatherDB = CoolWeatherDB.getInstance(context);
       Utility.handleProvinceResponse(coolWeatherDB,is);

       List<Province> provinceList = coolWeatherDB.loadProvince();
       for (Province province:
            provinceList) {
           System.out.println("省份:"+province.getProvinceName());
       }
   }

}
