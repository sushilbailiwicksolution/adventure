package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.loginRegister;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Activties.HomePage;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.App.AppController1;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.AppUtils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Utils.Itags;


public class SignupTab extends Fragment {

    private EditText edt_mobile, edt_name, edt_Email, edt_password, edt_last_name;
    private Button btn_create_account;
    View RootView;
    private Context context;
    ProgressDialog prg;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RootView = inflater.inflate(R.layout.signup_fragment, container, false);
        context = getActivity();
        createIDs();
        clickEvent();

        return RootView;
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
                    return;
                } else if (last_name.equalsIgnoreCase("")) {
                    edt_last_name.setError("Mandatory");
                    return;
                } else if (email.equalsIgnoreCase("")) {
                    edt_Email.setError("Mandatory");
                    return;
                } else if (password.equalsIgnoreCase("")) {
                    edt_password.setError("Mandatory");
                    return;
                } else if (password.length() < 6) {
                    edt_password.setError("password must be of 6 Character ");
                    return;
                } else if (mobile.equalsIgnoreCase("")) {
                    edt_Email.setError("Mandatory");
                    return;
                } else if (mobile.length() != 10) {
                    edt_mobile.setError("Number Should be 10 character");
                    return;
                } else {
                    RegisterUser(email, name, password, mobile, last_name);
                }
            }
        });
    }

    private void RegisterUser(final String email, final String name, final String password, final String mobile, final String Lastname) {
        prg.setMessage("Please Wait....");
        prg.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL,
                new Response.Listener<String>() {

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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put(Itags.Header, "ABC98XYZ53IJ61L");
                // params.put("Accept-Language", "fr");
                Log.e("Param header ", "" + header);

                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("action", UtilsUrl.Action_register);
                params.put("fname", name);
                params.put("lname", Lastname);

                params.put("email", email);
                params.put("mobile_no", mobile);
                params.put("password", password);
                params.put("isSocial", "0");
                params.put("login_type", "N");

                Log.e("Param Response ", "" + params);
                return params;
            }
        };
        AppController1.getInstance().addToRequestQueue(stringRequest);


    }

    private void createIDs() {
        prg = new ProgressDialog(context);
        edt_mobile = (EditText) RootView.findViewById(R.id.edt_mobile);
        edt_name = (EditText) RootView.findViewById(R.id.edt_name);
        edt_last_name = (EditText) RootView.findViewById(R.id.edt_last_name);
        edt_password = (EditText) RootView.findViewById(R.id.edt_password);
        edt_Email = (EditText) RootView.findViewById(R.id.edt_Email);
        btn_create_account = (Button) RootView.findViewById(R.id.btn_create_account);

    }
}
