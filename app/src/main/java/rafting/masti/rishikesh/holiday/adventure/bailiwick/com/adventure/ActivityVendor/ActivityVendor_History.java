package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.ActivityVendor;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Adapter.Vendor_BookingAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.App.AppController1;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.AppUtils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Modal.VendorBooking_Beans;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Support.RootActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Utils.Itags;

/**
 * Created by Prince on 12-03-2018.
 */

public class ActivityVendor_History extends RootActivity implements Vendor_BookingAdapter.ItemClickMasterMenuListInterface {

    private Context context;
    SpotsDialog prog;
    RecyclerView recyclerview_inventory_history;


    ArrayList<VendorBooking_Beans> ListVendor_Booking;
    Vendor_BookingAdapter bookingHistoryAdapter;
    RadioGroup rdg_history;
    RadioButton rdb_both, rdb_raft, rdb_camp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendor_history);
        setToolbar();
        createIDs();
        getDetail();
        CheckChanged();
    }

    private void CheckChanged() {

        rdg_history.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                getDetail();
            }
        });
    }

    private void getDetail() {

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
                                    ListVendor_Booking.clear();
                                    String status = jsData.getString("status");

                                    if (status.equalsIgnoreCase("1")) {

                                        JSONArray jsRaftInventory = jsData.getJSONArray("data");
                                        ListVendor_Booking.clear();

                                        for (int i = 0; i < jsRaftInventory.length(); i++) {
                                            String Value_skipped = "none";
                                            String value_differ = "phone";
                                            if (jsRaftInventory.getJSONObject(i).has("customer_phone")) {
                                                value_differ = "customer_phone";
                                            }
                                            String sports = jsRaftInventory.getJSONObject(i).getString("adventure_sport");


                                            if (rdb_raft.isChecked()) {
                                                Value_skipped = jsRaftInventory.getJSONObject(i).getString("adventure_sport");
                                                if (Value_skipped.equalsIgnoreCase("Rafting")) {
                                                    addlist(jsRaftInventory, i, Value_skipped);

                                                }

                                            } else if (rdb_camp.isChecked()) {
                                                Value_skipped = jsRaftInventory.getJSONObject(i).getString("adventure_sport");
                                                if (Value_skipped.equalsIgnoreCase("camping")) {
                                                    addlist(jsRaftInventory, i, Value_skipped);
                                                }


                                            } else if (rdb_both.isChecked()){
                                                Value_skipped = jsRaftInventory.getJSONObject(i).getString("adventure_sport");

                                                addlist(jsRaftInventory, i, Value_skipped);


                                            }


                                        }
                                        bookingHistoryAdapter.notifyDataSetChanged();


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

                params.put("action", UtilsUrl.Action_VendorBooking);
                params.put("vendor_id", SharedPref.getUserID());


                Log.e("Param Response ", "" + params);
                return params;
            }
        };

        AppController1.getInstance().addToRequestQueue(stringRequest);


    }

    private void addlist(JSONArray jsRaftInventory, int i, String value_skipped) {
        try {
            if (value_skipped.equalsIgnoreCase("camping")) {
                ListVendor_Booking.add(new VendorBooking_Beans(
                        jsRaftInventory.getJSONObject(i).getString("booking_id"),
                        jsRaftInventory.getJSONObject(i).getString("adventure_sport"),
                        jsRaftInventory.getJSONObject(i).getString("customer_name"),
                        jsRaftInventory.getJSONObject(i).getString("customer_email"),
                        jsRaftInventory.getJSONObject(i).getString("phone"),
                        jsRaftInventory.getJSONObject(i).getString("booking_status"),
                        jsRaftInventory.getJSONObject(i).getString("customer_message"),
                        jsRaftInventory.getJSONObject(i).getString("check_in"),
                        jsRaftInventory.getJSONObject(i).getString("check_out"),
                        jsRaftInventory.getJSONObject(i).getString("no_of_adult"),
                        jsRaftInventory.getJSONObject(i).getString("no_of_children"),
                        jsRaftInventory.getJSONObject(i).getString("camp_id"),
                        jsRaftInventory.getJSONObject(i).getString("room_type"),
                        jsRaftInventory.getJSONObject(i).getString("price_per_person"),
                        jsRaftInventory.getJSONObject(i).getString("total_price")

                ));

            } else {
                ListVendor_Booking.add(new VendorBooking_Beans(
                        jsRaftInventory.getJSONObject(i).getString("booking_id"),
                        jsRaftInventory.getJSONObject(i).getString("adventure_sport"),
                        jsRaftInventory.getJSONObject(i).getString("customer_name"),
                        jsRaftInventory.getJSONObject(i).getString("customer_email"),
                        jsRaftInventory.getJSONObject(i).getString("customer_phone"),
                        jsRaftInventory.getJSONObject(i).getString("booking_status"),
                        jsRaftInventory.getJSONObject(i).getString("customer_message"),
                        jsRaftInventory.getJSONObject(i).getString("start_time"),
                        jsRaftInventory.getJSONObject(i).getString("starting_point"),
                        jsRaftInventory.getJSONObject(i).getString("booked_seat"),
                        "",
                        "",
                        "",
                        "",
                        ""

                ));
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void createIDs() {
        context = ActivityVendor_History.this;
        prog = new SpotsDialog(context, R.style.Custom);
        recyclerview_inventory_history = (RecyclerView) findViewById(R.id.recyclerview_inventory_history);

        ListVendor_Booking = new ArrayList<>();
        bookingHistoryAdapter = new Vendor_BookingAdapter(context, ListVendor_Booking, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setAutoMeasureEnabled(true);
        recyclerview_inventory_history.setLayoutManager(mLayoutManager);
        recyclerview_inventory_history.setItemAnimator(new DefaultItemAnimator());
        recyclerview_inventory_history.setAdapter(bookingHistoryAdapter);

        rdg_history = (RadioGroup) findViewById(R.id.rgp_camp_control);

        rdb_both = (RadioButton) findViewById(R.id.rdb_both);
        rdb_raft = (RadioButton) findViewById(R.id.rdb_raft);
        rdb_camp = (RadioButton) findViewById(R.id.rdb_camp);

    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("INVENTORY");

    }

    @Override
    public void onItemMasterClick(int position) {

    }
}
