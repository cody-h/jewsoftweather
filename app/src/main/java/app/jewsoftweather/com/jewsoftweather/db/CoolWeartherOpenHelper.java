package app.jewsoftweather.com.jewsoftweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.id;

/**
 * Created by Administrator on 2017-04-10.
 */

public class CoolWeartherOpenHelper extends SQLiteOpenHelper {

    /**
     * Province表建表语句
     */
    public static final String CREATE_PROVINCE = "create table Province ("
            + "id integer primary key autoincrement,"
            +"province_name text,"
            +"province_code text)";

    /**
     *City表建表语句
     */
    public  static  final String CREATE_CITY = "create table City ("
            +" id integer primary key autoincrement,"
            +"city_name text,"
            +"city_code text,"
            +"province_code integer)";

    /**
     *County表建表语句
     */
    public static  final  String CREATE_COUNTY = "create table County("
            +"id integer primary key autoincrement,"
            +"county_name text,"
            +"county_code text,"
            +"weather_code text,"
            +"city_id integer)";


    public CoolWeartherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROVINCE);  //创建Province表
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
