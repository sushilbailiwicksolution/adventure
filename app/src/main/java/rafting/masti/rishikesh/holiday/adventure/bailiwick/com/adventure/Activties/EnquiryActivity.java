package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Activties;

import android.app.ProgressDialog;
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
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Adapter.EnquiryHistoryAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.App.AppController1;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.AppUtils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Modal.EnquiryHistory;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Support.RootActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Utils.Itags;

/**
 * Created by Prince on 05-10-2018.
 */

public class EnquiryActivity extends RootActivity {

    private RecyclerView mEnquiryRecycler;
    private EnquiryHistoryAdapter enquiryHistoryAdapter;
    private List<EnquiryHistory> enquiryHistoryList;
    private SpotsDialog prg;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry);
        context = this;
        prg = new SpotsDialog(context, R.style.Custom);
        enquiryHistoryList = new ArrayList<>();
        getUiObject();
        setToolbar();
        getEnquireHistory();

    }

    private void getUiObject(){
        mEnquiryRecycler = findViewById(R.id.rv_enquiry_history);
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Enquiry History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getEnquireHistory(){

        prg.setTitle("Please Wait....");
        prg.show();

        StringRequest enquiryHistoryRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (new CheckConnectivity().isConnected(context)){
                    try {

                        prg.dismiss();
                        JSONObject commonData = new JSONObject(response);
                        if (commonData.getInt("status") == 1){

                            JSONArray enquiry_history_details = commonData.getJSONArray("enquiry_history_details");
                            for(int i = 0;i < enquiry_history_details.length();i++){
                                enquiryHistoryList.add(new EnquiryHistory(
                                        enquiry_history_details.getJSONObject(i).getString("userName"),
                                        enquiry_history_details.getJSONObject(i).getString("userEmail"),
                                        enquiry_history_details.getJSONObject(i).getString("userMobile"),
                                        enquiry_history_details.getJSONObject(i).getString("no_of_person"),
                                        enquiry_history_details.getJSONObject(i).getString("packageName"),
                                        enquiry_history_details.getJSONObject(i).getString("serviceName"),
                                        enquiry_history_details.getJSONObject(i).getString("vendorName"),
                                        enquiry_history_details.getJSONObject(i).getString("bookingDate")
                                ));
                            }
                            setListAdapter(enquiryHistoryList);
                        }else{
                            Toast.makeText(context, commonData.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }else {
                    Toast.makeText(context, "Check Your Connection", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                prg.dismiss();
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> headers = new HashMap<>();
                headers.put(Itags.Header,"ABC98XYZ53IJ61L");

                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("action","getEnquiryHistory");
                params.put("user_id", SharedPref.getUserID());

                return params;
            }
        };

        AppController1.getInstance().addToRequestQueue(enquiryHistoryRequest);


    }

    private void setListAdapter(List<EnquiryHistory> enquiryHistoryList) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        mEnquiryRecycler.setLayoutManager(layoutManager);
        mEnquiryRecycler.setAdapter(new EnquiryHistoryAdapter(context,enquiryHistoryList));

    }


}
