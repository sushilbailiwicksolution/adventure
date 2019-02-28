package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

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
import java.util.Objects;

import dmax.dialog.SpotsDialog;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.adapter.PaymentHistoryAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.app.AppController;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.apputils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model.PaymentHistory;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.RootActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Itags;

/**
 * Created by Prince on 05-10-2018.
 */

public class PaymentQueryActivity extends RootActivity {

    private Context context;
    private PaymentHistoryAdapter paymentHistoryAdapter;
    private List<PaymentHistory> paymentHistoryList;
    private RecyclerView mPaymentHistoryRecycler;
    private SpotsDialog prog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);
        context = this;
        paymentHistoryList = new ArrayList<>();
        getUiObject();
        setToolbar();
        getPaymentHistoryData();

    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Payment History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getUiObject() {
        mPaymentHistoryRecycler = findViewById(R.id.rv_payment_history);

        prog = new SpotsDialog(context, R.style.Custom);
    }

    private void setPaymentListAdapter(List<PaymentHistory> paymentHistoryList) {
        mPaymentHistoryRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        mPaymentHistoryRecycler.setAdapter(new PaymentHistoryAdapter(context, paymentHistoryList));
    }


    private void getPaymentHistoryData() {
        prog.setTitle("Please wait.");
        prog.show();

        StringRequest paymentHistoryRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (new CheckConnectivity().isConnected(context)) {
                    try {
                        prog.dismiss();
                        if (response != null) {

                            JSONObject payemntData = new JSONObject(response);
                            if (payemntData.getInt("status") == 1) {

                                JSONArray payment_history_details = payemntData.getJSONArray("payment_history_details");
                                for (int i = 0; i < payment_history_details.length(); i++) {
                                    paymentHistoryList.add(new PaymentHistory(
                                            payment_history_details.getJSONObject(i).getString("serviceName"),
                                            payment_history_details.getJSONObject(i).getString("order_time"),
                                            payment_history_details.getJSONObject(i).getString("price"),
                                            payment_history_details.getJSONObject(i).getString("payment_status"),
                                            payment_history_details.getJSONObject(i).getString("txn_id")
                                    ));
                                }
                                setPaymentListAdapter(paymentHistoryList);

                            } else {
                                Toast.makeText(context, payemntData.getString("msg"), Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(context, "Check Your Connection", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                prog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put(Itags.Header, "ABC98XYZ53IJ61L");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "getPaymentHistory");
                params.put("user_id", SharedPref.getUserID());
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(paymentHistoryRequest);

    }


}
