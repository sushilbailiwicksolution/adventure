package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.activity_vendor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.app.AppController;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.apputils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.loginRegister.LoginRegisterActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.DeviceOperation;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.MyBounceInterpolator;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.RootActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Itags;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Tracer;


public class ActivityVendorDashboard extends RootActivity {

    private Context mContext;
    private String TAG = this.getClass().getName();


    ImageView img_raft, img_camp, img_cycling, img_biking, img_bungy;
    Animation raftAnim, campAnim, cycleAnim, bikeAnim, bungyAnim;
    private TextView txt_username, txt_logout;
    private Button btn_booking_history;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_dashboard);
        mContext = ActivityVendorDashboard.this;

        createIds();
        clickEvent();
    }


    private void clickEvent() {
        btn_booking_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ActivityVendorDashboard.this, ActivityVendorHistory.class);
                startActivity(i);
            }
        });
        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });
    }

    private void logOut() {
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
                        Intent i = new Intent(ActivityVendorDashboard.this, LoginRegisterActivity.class);
                        startActivity(i);
                        finish();
                    }
                })
                .show();
    }

    private void createIds() {

        img_raft = findViewById(R.id.img_raft);
        img_camp = findViewById(R.id.img_camping);
        img_cycling = findViewById(R.id.img_cycling);
        img_biking = findViewById(R.id.img_biking);
        img_bungy = findViewById(R.id.img_bungy);

        txt_username = findViewById(R.id.txt_username);
        txt_logout = findViewById(R.id.txt_logout);

        btn_booking_history = findViewById(R.id.btn_booking_history);

        raftAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        campAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        cycleAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        bikeAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        bungyAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        raftAnim.setInterpolator(interpolator);
        campAnim.setInterpolator(interpolator);
        cycleAnim.setInterpolator(interpolator);
        bikeAnim.setInterpolator(interpolator);
        bungyAnim.setInterpolator(interpolator);

    }

    // Camp
    public void campTapButton(View view) {

        img_camp.startAnimation(campAnim);
        campAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
                Log.e("i m heree", "in pp");
            }

            @Override
            public void onAnimationRepeat(Animation arg0) { }

            @Override
            public void onAnimationEnd(Animation arg0) {
                Log.e("i m heree", "in pp2");
                Intent i = new Intent(ActivityVendorDashboard.this, ActivityCampInventory.class);
                startActivity(i);
            }
        });

    }

    // Cycle
    public void cycleTapButton(View view) {
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        Log.e("i m heree", "in animation");

        img_cycling.startAnimation(cycleAnim);
        cycleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
                Log.e("i m heree", "in animation");
                Log.e("i m heree", "in pp2");
            }

            @Override
            public void onAnimationRepeat(Animation arg0) { }

            @Override
            public void onAnimationEnd(Animation arg0) {
                Log.e("i m heree", "in pp2");
                Intent i = new Intent(ActivityVendorDashboard.this, ActivityCyclingInventory.class);
                startActivity(i);
            }
        });
    }

    // biking
    public void bikeTapButton(View view) {
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        Log.e("i m heree", "in animation");

        img_biking.startAnimation(bikeAnim);
        bikeAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
                Log.e("i m heree", "in animation");
                Log.e("i m heree", "in pp2");
            }

            @Override
            public void onAnimationRepeat(Animation arg0) { }

            @Override
            public void onAnimationEnd(Animation arg0) {
                Log.e("i m heree", "in pp2");
                Intent i = new Intent(ActivityVendorDashboard.this, ActivityBikingInventory.class);
                startActivity(i);
            }
        });
    }

    // Bungy Jumping
    public void bungyTapButton(View view) {
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        Log.e("i m heree", "in animation");

        img_bungy.startAnimation(bungyAnim);
        bungyAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
                Log.e("i m heree", "in animation");
                Log.e("i m heree", "in pp2");
            }

            @Override
            public void onAnimationRepeat(Animation arg0) { }

            @Override
            public void onAnimationEnd(Animation arg0) {
                Log.e("i m heree", "in pp2");
                Intent i = new Intent(ActivityVendorDashboard.this, ActivityBungyInventory.class);
                i.putExtra("service_id", Itags.Bungy);
                startActivity(i);
            }
        });

    }


    // Rafting
    public void raftTapButton(View view) {

        img_raft.startAnimation(raftAnim);
        raftAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) { }

            @Override
            public void onAnimationRepeat(Animation arg0) { }

            @Override
            public void onAnimationEnd(Animation arg0) {
                Log.e("it finish", "it finish");
                Intent i = new Intent(ActivityVendorDashboard.this, ActivityRaftingInventory.class);
                i.putExtra("service_id", Itags.RAFTING);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {

        getUserDetail(DeviceOperation.getDeviceDetail(this), DeviceOperation.getFcmkey());
        super.onResume();
    }

    private void getUserDetail(final String deviceid, final String fcm) {

        Log.e("Device Detail", deviceid + "  :  " + fcm);
        if (new CheckConnectivity().isConnected(mContext)) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.e("Response : prince ", response);
                        if (response != null) {
                            JSONObject jsData = new JSONObject(response);
                            String status = jsData.getString("status");
                            if (status.equalsIgnoreCase("1")) {
                                saveDetails(jsData);
                            } else {
                                String msg = jsData.getString("msg");
                                Tracer.debugLog("Vendor Dash ",msg);
                            }
                        } else {
                            Toast.makeText(mContext, "Invalid Response !!!", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
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
                public Map<String, String> getHeaders() {
                    Map<String, String> header = new HashMap<>();
                    header.put(Itags.Header, "ABC98XYZ53IJ61L");
                    return header;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();

                    params.put("action", UtilsUrl.Action_userDetail);
                    params.put("user_id", SharedPref.getUserID());
                    params.put("fcm", fcm);
                    params.put("device_id", deviceid);
                    Log.e("Request Params ", "" + params);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    private void saveDetails(JSONObject jsData) {
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

            String name = SharedPref.getFirstName()+" "+SharedPref.getLastName();
            String welcomeText = "Welcome "+name;
            txt_username.setText(welcomeText);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
