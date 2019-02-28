package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.loginRegister;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.activities.HomePage;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.app.AppController;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.apputils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Commons;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Const;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Itags;


public class SignupTab extends Fragment {

    private EditText edt_mobile, edt_name, edt_Email, edt_password, edt_last_name;
    private Button btn_create_account;
    private Context context;
    ProgressDialog prg;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.signup_fragment, container, false);
        context = getActivity();
        createIDs(rootView);
        clickEvent();

        return rootView;
    }

    private void clickEvent() {
        btn_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, name, password, mobile, last_name;

                email = edt_Email.getText().toString().trim();
                name = edt_name.getText().toString().trim();
                password = edt_password.getText().toString().trim();
                mobile = edt_mobile.getText().toString().trim();
                last_name = edt_last_name.getText().toString().trim();

                if (name.equalsIgnoreCase("")) {
                    edt_name.setError("Mandatory");
                } else if (last_name.equalsIgnoreCase("")) {
                    edt_last_name.setError("Mandatory");
                } else if (email.equalsIgnoreCase("")) {
                    edt_Email.setError("Mandatory");
                } else if (password.equalsIgnoreCase("")) {
                    edt_password.setError("Mandatory");
                } else if (password.length() < 6) {
                    edt_password.setError("Password must be of 6 Character ");
                } else if (mobile.equalsIgnoreCase("")) {
                    edt_Email.setError("Mandatory");
                } else if (!Commons.isValidMobile(mobile)) {
                    edt_mobile.setError("Invalid Mobile Number");
                } else if(!Commons.isValidEmail(email)){
                    edt_Email.setError("Invalid Email Format");
                }else {
                    registerUser(email, name, password, mobile, last_name);
                }
            }
        });
    }

    private void registerUser(final String email, final String name, final String password, final String mobile, final String lastname) {

        if (new CheckConnectivity().isConnected(context)) {
            prg.setMessage("Please Wait....");
            prg.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Display the first 500 characters of the response string.
                    try {
                        prg.dismiss();
                        Log.e("Response : prince ", response);
                        if (response != null) {
                            JSONObject jsData = new JSONObject(response);
                            String status = jsData.getString("status");
                            if (status.equalsIgnoreCase("1")) {
                                String msg = jsData.getString("msg");
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getActivity(), HomePage.class);
                                startActivity(i);
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

                    params.put(Const.KEY_ACTION, UtilsUrl.Action_register);
                    params.put(Const.KEY_FNAME, name);
                    params.put(Const.KEY_LNAME, lastname);

                    params.put(Const.KEY_EMAIL, email);
                    params.put(Const.KEY_MOBILE, mobile);
                    params.put(Const.KEY_PASSWORD, password);
                    params.put(Const.KEY_ISSOCIAL, "0");
                    params.put(Const.KEY_LOGIN_TYPE, "N");

                    Log.e("Param Response ", "" + params);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(context, "Check Your connetion", Toast.LENGTH_LONG).show();
        }
    }

    private void createIDs(View rootView) {
        prg = new ProgressDialog(context);
        edt_mobile = rootView.findViewById(R.id.edt_mobile);
        edt_name = rootView.findViewById(R.id.edt_name);
        edt_last_name = rootView.findViewById(R.id.edt_last_name);
        edt_password = rootView.findViewById(R.id.edt_password);
        edt_Email = rootView.findViewById(R.id.edt_Email);
        btn_create_account = rootView.findViewById(R.id.btn_create_account);
    }

}
