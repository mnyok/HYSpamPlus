package mskim.hyspamplus.util;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;


public class SharedPreferenceManager {
    public static boolean togglePush(SharedPreferences setting){
        SharedPreferences.Editor editor = setting.edit();

        //toggle push preference
        if (setting.getBoolean("push", true)){
            Log.i("Push toggle", "OFF");
            editor.putBoolean("push", false);
            editor.apply();
            return false;
        } else {
            Log.i("Push toggle", "ON");
            editor.putBoolean("push", true);
            editor.apply();
            return true;
        }
    }
}
