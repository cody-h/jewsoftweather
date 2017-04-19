package app.jewsoftweather.com.jewsoftweather.util;

import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import app.jewsoftweather.com.jewsoftweather.model.Province;

/**
 * Created by Administrator on 2017-04-12.
 */

public class Xml2Province {
    public List<Province> parse(InputStream is) throws Exception {

        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is,"UTF-8");

       List<Province> list =  new ArrayList<Province>();
        Province province = null;

        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT){
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    if(parser.getName().equalsIgnoreCase("province")){
                        province = new Province();

                        String provinceCode = parser.getAttributeValue(0);
                        province.setProvinceCode(provinceCode);

                        String provinceName = parser.getAttributeValue(1);
                        province.setProvinceName(provinceName);

//                        System.out.println("**************"+provinceCode+"|"+provinceName);

                        list.add(province);
                    }
                    break;
            }
            eventType = parser.next();
        }
        is.close();
        return  list;
    }
}
