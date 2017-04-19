package app.jewsoftweather.com.jewsoftweather.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import android.util.Xml;

import app.jewsoftweather.com.jewsoftweather.model.County;


public class Xml2County {
	
	int cityId ;
	
	public List<County> parser2county(InputStream is) throws Exception{
		
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is,"utf-8");
		
		int eventType = parser.getEventType();
		List<County> countyList = new ArrayList<County>();
		County county = null;
		
		while(eventType != XmlPullParser.END_DOCUMENT){
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				
				break;
			case XmlPullParser.START_TAG:
				if(parser.getName().equalsIgnoreCase("city")){
					int cityCode = Integer.parseInt(parser.getAttributeValue(0));
					cityId = cityCode;
				}
				else if(parser.getName().equalsIgnoreCase("county")){
					county = new County();
					
					String countyCode = parser.getAttributeValue(0);
					String countyName = parser.getAttributeValue(1);
					String weatherCode = parser.getAttributeValue(2);
					
					county.setCountyCode(countyCode);
					county.setCountyName(countyName);
					county.setWeatherCode(weatherCode);
					county.setCityId(cityId);
					
					countyList.add(county);
				}
				break;
			case XmlPullParser.END_TAG:
				break;
			}
			
			eventType = parser.next();
		}
		is.close();
		return countyList;
	}

}
