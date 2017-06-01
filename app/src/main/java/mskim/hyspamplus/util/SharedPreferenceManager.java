package mskim.hyspamplus.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


public class SharedPreferenceManager {
    private final Context mContext;

    public SharedPreferenceManager(Context context) {
        mContext = context;
    }

    public boolean togglePush(){
        SharedPreferences setting = mContext.getSharedPreferences("setting", Activity.MODE_PRIVATE);
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

    public boolean getPush() {
        return mContext.getSharedPreferences("setting", Activity.MODE_PRIVATE).getBoolean("push", true);

    }
}
