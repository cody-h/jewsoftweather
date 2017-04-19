package app.jewsoftweather.com.jewsoftweather.util;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import app.jewsoftweather.com.jewsoftweather.model.City;

/**
 * Created by Administrator on 2017-04-12.
 */


public class Xml2City {

    int provinceid;
    public List<City> parse(InputStream is) throws Exception {

        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is,"UTF-8");

        List<City> cityList = new ArrayList<City>();
        City city = null;

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT){
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    if(parser.getName().equalsIgnoreCase("province")){
                        int provinceCode = Integer.parseInt(parser.getAttributeValue(0));
                        provinceid = provinceCode;
                    }
                    else if (parser.getName().equalsIgnoreCase("city")){
                        city = new City();

                        String cityCode = parser.getAttributeValue(0);
                        String cityName = parser.getAttributeValue(1);

                        city.setCityCode(cityCode);
                        city.setCityName(cityName);
                        city.setProvinceId(provinceid);

//                        System.out.println("*********"+cityName);

                    }
                    break;
                case XmlPullParser.END_TAG:
                    if(parser.getName().equalsIgnoreCase("city")){
                        cityList.add(city);
                    }
                    break;
            }

            eventType = parser.next();
        }
        is.close();
        return  cityList;
    }
}
