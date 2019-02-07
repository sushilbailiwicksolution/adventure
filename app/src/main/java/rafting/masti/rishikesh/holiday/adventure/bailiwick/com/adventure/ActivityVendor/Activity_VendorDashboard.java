package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.ActivityVendor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Activties.HomePage;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.App.AppController1;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.AppUtils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Support.DeviceOperation;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Support.MyBounceInterpolator;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Support.RootActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Utils.Itags;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.loginRegister.LoginRegisterActivity;

/**
 * Created by Prince on 03-03-2018.
 */

public class Activity_VendorDashboard extends RootActivity {

    private Context mContext;
    private String TAG = this.getClass().getName();


    ImageView img_raft, img_camp;
    Animation raftAnim, campAnim;
    private TextView txt_username, txt_logout;
    private Button btn_booking_history;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_dashboard);
        mContext = Activity_VendorDashboard.this;

        createIds();
        clickEvent();


    }


    private void clickEvent() {
        btn_booking_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_VendorDashboard.this, ActivityVendor_History.class);
                startActivity(i);
            }
        });


        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });
    }

    private void Logout() {
        new MaterialDialog.Builder(this)
                .iconRes(R.drawable.ic_launcher_background)
                .limitIconToDefaultSize()
                .title("Logout!")
                .contentColor(getResources().getColor(R.color.gray))

                .titleColor(getResources().getColor(R.color.black))
                .backgroundColor(getResources().getColor(R.color.white))
                .content("Do you want to logout?")
                .positiveText("Cancel")
                .negativeText("Yes")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        SharedPref.ClearAll();
                        Intent i = new Intent(Activity_VendorDashboard.this, LoginRegisterActivity.class);
                        startActivity(i);
                        finish();

                    }
                })
                .show();
    }

    private void createIds() {

        img_raft = (ImageView) findViewById(R.id.img_raft);
        img_camp = (ImageView) findViewById(R.id.img_camping);
        txt_username = (TextView) findViewById(R.id.txt_username);
        txt_logout = (TextView) findViewById(R.id.txt_logout);

        btn_booking_history = (Button) findViewById(R.id.btn_booking_history);


        raftAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        campAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        raftAnim.setInterpolator(interpolator);
        campAnim.setInterpolator(interpolator);
    }

    public void CampTapButton(View view) {


        // Use bounce interpolator with amplitude 0.2 and frequency 20


        img_camp.startAnimation(campAnim);
        campAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
                Log.e("i m heree", "in animation");
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                Intent i = new Intent(Activity_VendorDashboard.this, ActivityCampInventory.class);
                startActivity(i);
            }
        });

    }

    public void RaftTapButton(View view) {


        // Use bounce interpolator with amplitude 0.2 and frequency 20


        img_raft.startAnimation(raftAnim);
        raftAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                Log.e("it finish", "it finish");
                Intent i = new Intent(Activity_VendorDashboard.this, ActivityRAftingInventory.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {
        GetUserDetail(DeviceOperation.getDeviceDetail(this), DeviceOperation.getFcmkey());

        super.onResume();
    }

    private void GetUserDetail(final String deviceid, final String fcm) {

        Log.e("Device Detail", deviceid + "  :  " + fcm);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        if (new CheckConnectivity().isConnected(mContext)) {
                            try {
                                Log.e("Response : prince ", response);
                                if (response != null) {
                                    JSONObject jsData = new JSONObject(response);
                                    String status = jsData.getString("status");
                                    if (status.equalsIgnoreCase("1")) {
                                        SaveDetal(jsData);

                                    } else {
                                        String msg = jsData.getString("msg");

                                    }
                                } else {
                                    Toast.makeText(mContext, "Invalid Response !!!", Toast.LENGTH_LONG).show();

                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();

                            }
                        } else {
                            Toast.makeText(mContext, "Check Your connetion", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG).show();

                Log.e("Error :", error.toString());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put(Itags.Header, "ABC98XYZ53IJ61L");
                // params.put("Accept-Language", "fr");


                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("action", UtilsUrl.Action_userDetail);
                params.put("user_id", SharedPref.getUserID());
                params.put("fcm", fcm);
                params.put("device_id", deviceid);
                Log.e("Param Response ", "" + params);
                return params;
            }
        };
        AppController1.getInstance().addToRequestQueue(stringRequest);

    }

    private void SaveDetal(JSONObject jsData) {
        try {
            JSONObject data = jsData.getJSONObject("data");

            SharedPref.saveUserTYPE(data.getString("user_type"));
            SharedPref.saveFirstName(data.getString("fname"));
            SharedPref.saveLastName(data.getString("lname"));
            SharedPref.saveMobile1(data.getString("mobile_no"));
            SharedPref.saveEmail(data.getString("email"));
            if (!data.getString("profile_pic").equalsIgnoreCase("")) {
                SharedPref.saveprofileURL(data.getString("profile_pic"));

            }
            SharedPref.savegender(data.getString("gender"));
            SharedPref.saveAddress(data.getString("address"));
            SharedPref.saveCity(data.getString("city"));

            txt_username.setText("Welcome " + SharedPref.getFirstName() + " " + SharedPref.getLastName());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
