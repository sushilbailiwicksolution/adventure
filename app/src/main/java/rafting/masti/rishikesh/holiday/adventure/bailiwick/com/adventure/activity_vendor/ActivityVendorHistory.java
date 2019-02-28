package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.activity_vendor;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.adapter.VendorBookingAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.app.AppController;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.apputils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model.VendorBookingBeans;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.RootActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Const;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Itags;


public class ActivityVendorHistory extends RootActivity implements VendorBookingAdapter.ItemClickMasterMenuListInterface {

    private Context context;
    SpotsDialog prog;
    RecyclerView recyclerview_inventory_history;
    Spinner spinnerServices;


    ArrayList<VendorBookingBeans> ListVendor_Booking;
    VendorBookingAdapter bookingHistoryAdapter;
    //  RadioGroup rdg_history;
    // RadioButton rdb_both, rdb_raft, rdb_camp;
    int service_selected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendor_history);
        setToolbar();
        createIDs();
        getSpinner();
        spinnerClick();
        CheckChanged();
    }

    private void spinnerClick() {

        spinnerServices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Spinner Value  : ", spinnerServices.getSelectedItem().toString());

                getServiceType(spinnerServices.getSelectedItem().toString());
                getDetail();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getSpinner() {
        List<String> service_List = new ArrayList<>();
        service_List.add("All Services");
        service_List.add("Rafting");
        service_List.add("Camping");
        service_List.add("Bungy Jumping");
        service_List.add("Biking");
        service_List.add("Cycling");

        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(ActivityVendorHistory.this, android.R.layout.simple_list_item_1, service_List);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServices.setAdapter(adp1);
        service_selected = getServiceType(spinnerServices.getSelectedItem().toString());
        getDetail();
    }

    private int getServiceType(String service) {
        int serv = 0;
        switch (service) {
            case "All Services":
                serv = 0;
                break;
            case "Rafting":

                serv = 1;
                break;
            case "Camping":

                serv = 2;
                break;
            case "Bungy Jumping":

                serv = 3;
                break;
            case "Biking":

                serv = 4;
                break;
            case "Cycling":
                serv = 5;
                break;

            default:

        }
        return serv;

    }

    private void CheckChanged() {

        /*rdg_history.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                getDetail();
            }
        });*/
    }

    private void getDetail() {

        if (new CheckConnectivity().isConnected(context)) {

            prog.setTitle("Please wait.");
            prog.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

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

                                    if (service_selected == 1) {
                                        Log.e("Service select ", "Rafting");

                                        Value_skipped = jsRaftInventory.getJSONObject(i).getString("adventure_sport");
                                        if (Value_skipped.equalsIgnoreCase("Rafting")) {
                                            addlist(jsRaftInventory, i, Value_skipped);
                                        }

                                    } else if (service_selected == 2) {
                                        Log.e("Service select ", "Camping");

                                        Value_skipped = jsRaftInventory.getJSONObject(i).getString("adventure_sport");
                                        if (Value_skipped.equalsIgnoreCase("camping")) {
                                            addlist(jsRaftInventory, i, Value_skipped);
                                        }

                                    } else if (service_selected == 0) {
                                        Log.e("Service select ", "All Service ... ");

                                        Value_skipped = jsRaftInventory.getJSONObject(i).getString("adventure_sport");
                                        addlist(jsRaftInventory, i, Value_skipped);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Service is on process", Toast.LENGTH_LONG).show();
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
                public Map<String, String> getHeaders() {
                    Map<String, String> header = new HashMap<>();
                    header.put(Itags.Header, Const.APP_TOKEN);
                    // params.put("Accept-Language", "fr");
                    return header;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("action", UtilsUrl.Action_VendorBooking);
                    params.put("vendor_id", SharedPref.getUserID());
                    Log.e("Param Response ", "" + params);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void addlist(JSONArray jsRaftInventory, int i, String value_skipped) {
        try {
            if (value_skipped.equalsIgnoreCase("camping")) {
                ListVendor_Booking.add(new VendorBookingBeans(jsRaftInventory.getJSONObject(i).getString("booking_id"),
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
                ListVendor_Booking.add(new VendorBookingBeans(jsRaftInventory.getJSONObject(i).getString("booking_id"),
                        jsRaftInventory.getJSONObject(i).getString("adventure_sport"),
                        jsRaftInventory.getJSONObject(i).getString("customer_name"),
                        jsRaftInventory.getJSONObject(i).getString("customer_email"),
                        jsRaftInventory.getJSONObject(i).getString("customer_phone"),
                        jsRaftInventory.getJSONObject(i).getString("booking_status"),
                        jsRaftInventory.getJSONObject(i).getString("customer_message"),
                        jsRaftInventory.getJSONObject(i).getString("start_time"),
                        jsRaftInventory.getJSONObject(i).getString("starting_point"),
                        jsRaftInventory.getJSONObject(i).getString("booked_seat"),
                        "", "", "", "", ""));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void createIDs() {
        context = ActivityVendorHistory.this;
        prog = new SpotsDialog(context, R.style.Custom);
        recyclerview_inventory_history = findViewById(R.id.recyclerview_inventory_history);

        ListVendor_Booking = new ArrayList<>();
        bookingHistoryAdapter = new VendorBookingAdapter(context, ListVendor_Booking, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setAutoMeasureEnabled(true);
        recyclerview_inventory_history.setLayoutManager(mLayoutManager);
        recyclerview_inventory_history.setItemAnimator(new DefaultItemAnimator());
        recyclerview_inventory_history.setAdapter(bookingHistoryAdapter);

        spinnerServices = findViewById(R.id.spinnerServices);

        /*rdg_history = (RadioGroup) findViewById(R.id.rgp_camp_control);

        rdb_both = (RadioButton) findViewById(R.id.rdb_both);
        rdb_raft = (RadioButton) findViewById(R.id.rdb_raft);
        rdb_camp = (RadioButton) findViewById(R.id.rdb_camp);
*/
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("INVENTORY");

    }

    @Override
    public void onItemMasterClick(int position) {

    }
}
