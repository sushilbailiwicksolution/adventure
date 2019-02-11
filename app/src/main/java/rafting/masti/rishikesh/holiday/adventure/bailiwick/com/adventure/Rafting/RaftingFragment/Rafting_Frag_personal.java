package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Rafting.RaftingFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.App.AppController1;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.AppUtils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Rafting.RaftingActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Utils.Itags;
import dmax.dialog.SpotsDialog;

/**
 * Created by Prince on 22-01-2018.
 */

public class Rafting_Frag_personal extends Fragment {

    View rootView;
    Button btn_next;
    EditText edt_email, edt_name, edt_mobile;
    TextView txt_amount;
    Context context;
    SpotsDialog prog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.raft_personal_frag, container, false);
        context = getActivity();
        createIDS();
        clickEvent();
        setDetails();
        editTextChangeListenr();
        getRaftingDetail();
        return rootView;
}

    private void getRaftingDetail() {
        prog.setTitle("Please wait.");
        prog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        if (new CheckConnectivity().isConnected(context)) {
                            try {
                                prog.dismiss();
                                Log.e("Response : prince ", response);
                                if (response != null) {
                                    JSONObject jsData = new JSONObject(response);

                                    String status = jsData.getString("status");
                                    if (status.equalsIgnoreCase("1")) {

                                        SharedPref.saveTempJSON(response);

                                        JSONObject raftingDetail = jsData.getJSONObject("rafting_details");
                                        String price = raftingDetail.getString("price");
                                        txt_amount.setText(price);


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
                prog.dismiss();
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();

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

                params.put("action", UtilsUrl.Action_getRaftingDetail);
                Log.e("Param Response ", "" + params);
                return params;
            }
        };

        AppController1.getInstance().addToRequestQueue(stringRequest);


    }

    private void editTextChangeListenr() {
        edt_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    CheckForm();
                } else if (count == 0) {
                    CheckForm();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edt_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    CheckForm();
                } else if (count == 0) {
                    CheckForm();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edt_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("its count", "count : " + count);

                if (count == 1) {
                    Log.e("its count", "i m heree 111");

                    CheckForm();
                } else if (count == 0) {
                    Log.e("its count", "i m heree 222");

                    CheckForm();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

   /* private void setSwipeisEnable(boolean isEnable) {
        ((RaftingActivity) getActivity()).mViewPager.setSwipeable(isEnable);

    }
*/
    private void setDetails() {
        edt_name.setText(SharedPref.getFirstName() + " " + SharedPref.getLastName());
        edt_mobile.setText(SharedPref.getMobile1());
        edt_email.setText(SharedPref.getEmail());
        CheckForm();
    }

    private void CheckForm() {
        if (!edt_name.getText().toString().equalsIgnoreCase("") && !edt_email.getText().toString().equalsIgnoreCase("") && !edt_mobile.getText().toString().equalsIgnoreCase("")) {
            // setSwipeisEnable(true);
            btn_next.setEnabled(true);
        } else {
            Log.e("it disable", "it diseable");
            //   setSwipeisEnable(false);
            btn_next.setEnabled(false);

        }
    }


    private void clickEvent() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPref.saveTempemail(edt_email.getText().toString());
                SharedPref.saveTempfirstName(edt_name.getText().toString());
                SharedPref.saveTempmobile1(edt_mobile.getText().toString());
                if (!emailValidator(edt_email.getText().toString().trim())) {
                    edt_email.setError("Invalid id");
                    edt_email.requestFocus();
                    return;
                }

               /* ((RaftingActivity) getActivity()).mViewPager.setCurrentItem(1);*/

                //            ((RaftingActivity) getActivity()).mViewPager.setSwipeable(true);
            }
        });
    }

    private void createIDS() {
        prog = new SpotsDialog(context, R.style.Custom);
        btn_next = (Button) rootView.findViewById(R.id.btn_next);
        edt_email = (EditText) rootView.findViewById(R.id.edt_email);
        edt_name = (EditText) rootView.findViewById(R.id.edt_name);
        edt_mobile = (EditText) rootView.findViewById(R.id.edt_mobile);
        txt_amount = (TextView) rootView.findViewById(R.id.txt_amount);

    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
