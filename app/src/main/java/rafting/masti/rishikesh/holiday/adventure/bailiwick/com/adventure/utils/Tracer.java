package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils;

import android.util.Log;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.BuildConfig;

/**
 * Class to print log while in debug mode
 */
public class Tracer {

    public static void infoLog(String TAG,String message){
        if (checkIsDebug()){
            Log.i(TAG,message);
        }
    }

    public static void verboseLog(String TAG,String message){
        if (checkIsDebug()){
            Log.v(TAG,message);
        }
    }

    public static void debugLog(String TAG,String message){
        if (checkIsDebug()){
            Log.d(TAG,message);
        }
    }

    public static void errorLog(String TAG,String message){
        if (checkIsDebug()){
            Log.e(TAG,message);
        }
    }


    private static boolean checkIsDebug(){
        return BuildConfig.DEBUG;
    }

}
