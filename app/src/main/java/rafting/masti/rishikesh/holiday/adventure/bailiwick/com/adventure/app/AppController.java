package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.app;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.IntDef;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AppController extends MultiDexApplication {

    public static final String TAG = AppController.class.getSimpleName();
    public static final int GRANTED = 0;
    public static final int DENIED = 1;
    public static final int BLOCKED = 2;
    public static int invoiceId = 0;
    private static AppController mInstance;
    private RequestQueue mRequestQueue;


    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @PermissionStatus
    public static int getPermissionStatus(Activity activity, String androidPermissionName) {
        if (ContextCompat.checkSelfPermission(activity, androidPermissionName) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, androidPermissionName)) {
                return BLOCKED;
            }
            return DENIED;
        }
        return GRANTED;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(this);
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    @Override
    public void onCreate() {
        //MultiDex.install(this);
        super.onCreate();
        mInstance = this;
        //        new AddSaledatahelper(this);
//        new AddQuatationdatahelper(this);

    }


    public void handleUncaughtException(Thread thread, Throwable e) {
        e.printStackTrace();
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(
            {GRANTED, DENIED, BLOCKED})
    public @interface PermissionStatus {
    }

}

