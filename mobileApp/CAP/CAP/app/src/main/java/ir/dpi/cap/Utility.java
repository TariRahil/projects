package ir.dpi.cap;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;

import java.util.Locale;

/**
 * Created by Noroozi on 4/30/2017.
 */

public class Utility {

    public static void languageHelper(String lang,String country,Configuration config, Resources resources) {
        Locale locale = new Locale(lang,country);
        config.locale = locale;
        Locale.setDefault(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    public static void setPreferences(Context context,String key,String value){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(key,value).commit();
    }


    public static String getPreferences( Context context,String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
    public static enum tripStatus {
        AddByPassenger("0", 0),  //  ثبت سفر توسط مسافر
        AcceptByDriver("1", 1),   //پذیرفتن سفر توسط راننده
        RejectByDriver("2", 2),   //لغو سفر توسط راننده
        RejectByPassenger("3", 3),    //لغو سفر توسط مسافر
        End("4", 4),    //خاتمه سفر
        NoAcceptByDriver("5", 5), //نپذیرفتن سفر توسط راننده
        ArriveCap("6",6), //کپ شما رسید
        DontAcceptByAnyDriver("7",7); //هیچ راننده ای درخواست شما رو قبول نکردد

        private String stringValue;
        private int intValue;
        private tripStatus(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }
    public static enum isStayStatus {
        Stay( "true",0),  // راننده در اختیار است
        NotStay("false", 1);  //راننده در اختیار نیست

        private String stringValue;
        private int intValue;
        private isStayStatus(String toString, int value) {
            stringValue = toString;
            intValue = value;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }
//    public static enum processFinishStatus {
//        MAPS_ACTIVECARSNUMBER( "MAPS_ACTIVECARSNUMBER",0),
//        MAPS_ADDTRIP("Maps_AddTrip", 1);
//
//        private String stringValue;
//        private int intValue;
//        private processFinishStatus(String toString, int value) {
//            stringValue = toString;
//            intValue = value;
//        }
//
//        @Override
//        public String toString() {
//            return stringValue;
//        }
//    }

}
