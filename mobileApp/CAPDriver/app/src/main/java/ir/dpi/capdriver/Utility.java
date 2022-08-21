package ir.dpi.capdriver;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.view.View;

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

    public static String getPreferences(Context context, String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences((Context) context);
        return preferences.getString(key, null);
    }
}
