package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.loginRegister;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.ActivityVendor.Activity_VendorDashboard;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Activties.HomePage;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Activties.OTPscreenActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.App.AppController1;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.AppUtils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Database.DBOperation;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Utils.Itags;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.dialog.ForgetDialog;


/**
 * Created by Rajesh on 11/2/2017.
 */

public class LoginTab extends Fragment {
    private Button btn_signin;
    private EditText edit_text_email_signin, edit_text_password_signin;
    TextInputLayout inpt_email, inputEditTextUserName;
    private TextView txt_forget_pasword;

    private Context context;
    ProgressDialog prg;

    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.login_fragment, container, false);
        context = getActivity();
        createIDS();
        clickEvent();
        DBOperation.deleteAll_Cart(getActivity());
        getHashKey();
        return rootView;
    }

    private void getHashKey() {
        try {
            PackageInfo info = getActivity().getPackageManager().getPackageInfo("rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    private void clickEvent() {
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validate();

            }
        });
        txt_forget_pasword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showforgetDialog();


            }
        });
    }

    private void showforgetDialog() {

        final ForgetDialog dialog = new ForgetDialog(getActivity());
        dialog.show();
        dialog.btn_Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dialog.edt_email.getText().toString().equalsIgnoreCase("")) {
                    checkeMail(dialog.edt_email.getText().toString(),dialog);

                } else {
                    dialog.edt_email.setError("Mandatory");


                }
            }
        });
        dialog.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void checkeMail(final String email, final ForgetDialog dialog) {
        prg.setMessage("Please Wait....");
        prg.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                if (new CheckConnectivity().isConnected(context)) {
                    try {
                        prg.dismiss();
                        Log.e("Response : prince ", response);
                        if (response != null) {
                            JSONObject jsData = new JSONObject(response);
                            String status = jsData.getString("status");
                            if (status.equalsIgnoreCase("1")) {

                                String msg = jsData.getString("msg");
                                Intent i = new Intent(getActivity(), OTPscreenActivity.class);
                                i.putExtra("email",email);
                                startActivity(i);
                                dialog.dismiss();
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
                } else {
                    Toast.makeText(context, "Check Your connetion", Toast.LENGTH_LONG).show();
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
                Map<String, String> header = new HashMap<String, String>();
                header.put(Itags.Header, "ABC98XYZ53IJ61L");
                // params.put("Accept-Language", "fr");
                Log.e("Param header ", "" + header);

                return header;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("action", UtilsUrl.Action_forgetPassword);
                params.put("email", email);
                Log.e("Param Response", "" + params);
                return params;
            }
        };
        AppController1.getInstance().addToRequestQueue(stringRequest);

    }

    private void Validate() {

        if (edit_text_email_signin.getText().toString().trim().equalsIgnoreCase("")) {

            edit_text_email_signin.setError("Mandatory");
            return;
        } else if (edit_text_password_signin.getText().toString().trim().equalsIgnoreCase("")) {
            edit_text_email_signin.setError("Mandatory");
            return;

        } else {
           /* Intent i = new Intent(getActivity(), HomePage.class);
            startActivity(i);*/

            LoginApi(edit_text_email_signin.getText().toString().trim(), edit_text_password_signin.getText().toString().trim(), "0");


        }
    }


    private void LoginApi(final String email, final String password, final String LoginType) {
        prg.setMessage("Please Wait....");
        prg.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                if (new CheckConnectivity().isConnected(context)) {
                    try {
                        prg.dismiss();
                        Log.e("Response : prince ", response);
                        if (response != null) {
                            JSONObject jsData = new JSONObject(response);
                            String status = jsData.getString("status");
                            if (status.equalsIgnoreCase("1")) {

                                String msg = jsData.getString("msg");
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

                                JSONObject data = jsData.getJSONObject("data");

                                SaveDetal(jsData, email);
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
                } else {
                    Toast.makeText(context, "Check Your connetion", Toast.LENGTH_LONG).show();
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
                Map<String, String> header = new HashMap<String, String>();
                header.put(Itags.Header, "ABC98XYZ53IJ61L");
                // params.put("Accept-Language", "fr");
                Log.e("Param header ", "" + header);

                return header;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("action", UtilsUrl.Action_Login);

                params.put("email", email);
                params.put("password", password);
                params.put("isSocial", LoginType);

                Log.e("Param Response", "" + params);
                return params;
            }
        };
        AppController1.getInstance().addToRequestQueue(stringRequest);


    }

    private void SaveDetal(JSONObject jsData, String email) {
        try {
            JSONObject data = jsData.getJSONObject("data");
            SharedPref.saveLogin(true);
            SharedPref.saveUserID(data.getString("user_id"));
            SharedPref.saveUserTYPE(data.getString("user_type"));
            SharedPref.saveFirstName(data.getString("fname"));
            SharedPref.saveLastName(data.getString("lname"));
            SharedPref.saveMobile1(data.getString("mobile_no"));
            SharedPref.saveUserTYPE(data.getString("user_type"));
            SharedPref.saveEmail(email);

            if (data.getString("user_type").equalsIgnoreCase(Itags.Customer)) {
                Intent i = new Intent(getActivity(), HomePage.class);
                startActivity(i);
                getActivity().finish();

            } else if (data.getString("user_type").equalsIgnoreCase(Itags.VENDOR)) {
                Intent i = new Intent(getActivity(), Activity_VendorDashboard.class);
                startActivity(i);
                getActivity().finish();

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void createIDS() {
        prg = new ProgressDialog(context);

        btn_signin = rootView.findViewById(R.id.btn_signin);
        edit_text_email_signin = rootView.findViewById(R.id.edit_text_email_signin);
        edit_text_password_signin = rootView.findViewById(R.id.edit_text_password_signin);
        inputEditTextUserName = rootView.findViewById(R.id.inputEditTextUserName);
        inpt_email = rootView.findViewById(R.id.inpt_email);
        txt_forget_pasword = rootView.findViewById(R.id.txt_forget_pasword);

        // user
        //  edit_text_email_signin.setText("prince@prince.com");
        // edit_text_password_signin.setText("123456");
// vendor
      /*  edit_text_email_signin.setText("test@gmail.com");
        edit_text_password_signin.setText("12345678");*/

    }


}
