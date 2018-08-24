package appentus.ezyrideuser;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPrefData {
    private static final String PrefDB = "ezyRide";
    private static final String PerPrefDB = "perezyRide";

    public static void SetStringPref(Context context, String KEY, String Value) {
        try {
            SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(KEY, Value);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String GetStringPref(Context context, String KEY, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        return sp.getString(KEY, defValue);
    }

    public static void SetBooleanPref(Context context, String KEY, boolean Value) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(KEY, Value);
        editor.commit();
    }
    public static void setPermanentBoolean(Context context, String KEY, boolean Value) {
        SharedPreferences sp = context.getSharedPreferences(PerPrefDB, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(KEY, Value);
        editor.commit();
    }
    public static void clearSharedPreference(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}
