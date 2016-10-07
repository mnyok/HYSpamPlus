package mskim.hyspamplus;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;


public class SharedPreferenceManager {
    static boolean togglePush(Activity activity){
        SharedPreferences setting = activity.getSharedPreferences("setting", Activity.MODE_PRIVATE);
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
