package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Activties;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.App.AppController1;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.apputils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.custom_dialog.SweetAlertDialog;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.RootActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Const;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Itags;

/**
 * Created by Prince on 12-09-2018.
 */

public class OTPscreenActivity extends RootActivity {

    EditText edt_otp, edt_password, edt_confirm;
    Button btn_Apply;
    private boolean isPassword = false;
    String email;
    ProgressDialog prg;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otpscreen);
        context = OTPscreenActivity.this;
        createIDS();
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        clickEvent();
        setPassword(isPassword);
    }

    private void setPassword(boolean isPassword) {

        if (isPassword) {
            edt_otp.setVisibility(View.GONE);
            edt_password.setVisibility(View.VISIBLE);
            edt_confirm.setVisibility(View.VISIBLE);

        } else {
            edt_otp.setVisibility(View.VISIBLE);
            edt_password.setVisibility(View.GONE);
            edt_confirm.setVisibility(View.GONE);
        }
    }

    private void clickEvent() {
        btn_Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPassword) {
                    String password = edt_password.getText().toString();
                    String confirm = edt_confirm.getText().toString();

                    if (password.equalsIgnoreCase("")) {
                        edt_password.setError("Mandatory");
                    } else if (password.length() < 6) {
                        edt_password.setError("Password should be at least 6 character");
                    } else if (!password.equalsIgnoreCase(confirm)) {
                        edt_confirm.setError("Confirm password not match ");
                    } else {
                        changePassword(password, email);
                    }
                } else {
                    String otp_value = edt_otp.getText().toString().trim();
                    if (otp_value.equalsIgnoreCase("")) {
                        edt_otp.setError("Please enter Otp");
                    } else {
                        checkOTP(otp_value);
                    }
                }

            }
        });
    }

    private void changePassword(final String password, final String email) {

        if (new CheckConnectivity().isConnected(context)) {
            prg.setMessage("Please Wait....");
            prg.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        prg.dismiss();
                        Log.e("Response : prince ", response);
                        if (response != null) {
                            JSONObject jsData = new JSONObject(response);
                            String status = jsData.getString("status");
                            if (status.equalsIgnoreCase("1")) {

                                String msg = jsData.getString("msg");
                                Log.e("Message => ", msg);
                                showSweetDialog();

                            } else {
                                String msg = jsData.getString("msg");
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(context, "Invalid Response !!!", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    prg.dismiss();
                    Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();

                    Log.e("Error :", error.toString());
                }
            }) {

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> header = new HashMap<>();
                    header.put(Itags.Header, Const.APP_TOKEN);
                    // params.put("Accept-Language", "fr");
                    Log.e("Param header ", "" + header);

                    return header;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put(Const.KEY_ACTION, UtilsUrl.Action_updatePassword);
                    params.put(Const.KEY_EMAIL, email);
                    params.put(Const.KEY_PASSWORD, password);

                    Log.e("Param Response", "" + params);
                    return params;
                }
            };
            AppController1.getInstance().addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(context, "Check Your connetion", Toast.LENGTH_LONG).show();
        }

    }

    private void showSweetDialog() {
        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE).setTitleText("Good job!").setContentText(" Your Password has been change").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                sDialog.dismissWithAnimation();
                finish();
            }
        }).show();
    }

    private void checkOTP(final String otpValue) {

        if (new CheckConnectivity().isConnected(context)) {
            prg.setMessage("Please Wait....");
            prg.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        prg.dismiss();
                        Log.e("Response : prince ", response);
                        if (response != null) {
                            JSONObject jsData = new JSONObject(response);
                            String status = jsData.getString("status");
                            if (status.equalsIgnoreCase("1")) {

                                String msg = jsData.getString("msg");
                                Log.e("message : ", msg);
                                isPassword = true;
                                setPassword(isPassword);
                            } else {
                                String msg = jsData.getString("msg");
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(context, "Invalid Response !!!", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    prg.dismiss();
                    Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();

                    Log.e("Error :", error.toString());
                }
            }) {

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> header = new HashMap<>();
                    header.put(Itags.Header, "ABC98XYZ53IJ61L");
                    // params.put("Accept-Language", "fr");
                    Log.e("Param header ", "" + header);

                    return header;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("action", UtilsUrl.Action_OTPVerify);
                    params.put("email", email);
                    params.put("otp", otpValue);

                    Log.e("Param Response", "" + params);
                    return params;
                }
            };
            AppController1.getInstance().addToRequestQueue(stringRequest);
        } else {
            Toast.makeText(context, "Check Your connetion", Toast.LENGTH_LONG).show();
        }

    }

    private void createIDS() {
        prg = new ProgressDialog(context);
        btn_Apply = findViewById(R.id.btn_Apply);
        edt_password = findViewById(R.id.edt_password);
        edt_confirm = findViewById(R.id.edt_confirm);
        edt_otp = findViewById(R.id.edt_otp);

    }
}
