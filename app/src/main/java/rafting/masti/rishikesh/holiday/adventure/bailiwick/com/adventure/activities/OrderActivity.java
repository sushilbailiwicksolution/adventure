package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.adapter.UserOrderRecycAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.adapter.VendorBookingAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.app.AppController;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.apputils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model.Order_Beans;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.RootActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Const;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Itags;

/**
 * Created by Prince on 05-10-2018.
 */

public class OrderActivity extends RootActivity implements VendorBookingAdapter.ItemClickMasterMenuListInterface {

    private Context context;
    SpotsDialog prog;
    RecyclerView recyclerview_inventory_history;

    List<Order_Beans> vendorListBooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_history);
        vendorListBooking = new ArrayList<>();
        context = this;
        setToolbar();
        createIDs();
        getDetail();
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
                        Log.e("Response Order History ", response);
                        if (response != null) {
                            JSONObject jsData = new JSONObject(response);
                            vendorListBooking.clear();
                            String status = jsData.getString("status");

                            if (status.equalsIgnoreCase("1")) {

                                JSONArray jsRaftInventory = jsData.getJSONArray("order_history_details");
                                vendorListBooking.clear();
                                for (int i = 0; i < jsRaftInventory.length(); i++) {

                                    int personCount = Integer.parseInt(jsRaftInventory.getJSONObject(i).getString("adult")
                                            + Integer.parseInt(jsRaftInventory.getJSONObject(i).getString("children_blow_5_year"))
                                            + Integer.parseInt(jsRaftInventory.getJSONObject(i).getString("children_above_5_year")));

                                    vendorListBooking.add(new Order_Beans(

                                            jsRaftInventory.getJSONObject(i).getString("adventure_sport"),
                                            jsRaftInventory.getJSONObject(i).getString("user_name"),
                                            jsRaftInventory.getJSONObject(i).getString("user_email"),
                                            jsRaftInventory.getJSONObject(i).getString("mobile_no"),
                                            jsRaftInventory.getJSONObject(i).getString("package_name"), String.valueOf(personCount),
                                            jsRaftInventory.getJSONObject(i).getString("vendorName"),
                                            jsRaftInventory.getJSONObject(i).getString("booking_date"),
                                            jsRaftInventory.getJSONObject(i).getString("order_date"),
                                            jsRaftInventory.getJSONObject(i).getString("paymentStatus"),
                                            jsRaftInventory.getJSONObject(i).getString("starting_point_name"),
                                            jsRaftInventory.getJSONObject(i).getString("campName"),
                                            jsRaftInventory.getJSONObject(i).getString("adult"),
                                            jsRaftInventory.getJSONObject(i).getString("children_above_5_year"),
                                            jsRaftInventory.getJSONObject(i).getString("children_blow_5_year"),
                                            jsRaftInventory.getJSONObject(i).getString("check_in_date"),
                                            jsRaftInventory.getJSONObject(i).getString("check_out_date")

                                    ));

                                            /*if (jsRaftInventory.getJSONObject(i).getString("adventure_sport").equalsIgnoreCase("camping")) {
                                                vendorListBooking.add(new Order_Beans(
                                                        jsRaftInventory.getJSONObject(i).getString("user_name"),
                                                        jsRaftInventory.getJSONObject(i).getString("user_email"),
                                                        jsRaftInventory.getJSONObject(i).getString("mobile_no"),
                                                        jsRaftInventory.getJSONObject(i).getString("vendorName"),
                                                        jsRaftInventory.getJSONObject(i).getString("paymentStatus"),
                                                        jsRaftInventory.getJSONObject(i).getString("campName"),
                                                        jsRaftInventory.getJSONObject(i).getString("adult"),
                                                        jsRaftInventory.getJSONObject(i).getString("children_blow_5_year"),
                                                        jsRaftInventory.getJSONObject(i).getString("children_above_5_year"),
                                                        jsRaftInventory.getJSONObject(i).getString("check_in_date"),
                                                        jsRaftInventory.getJSONObject(i).getString("check_out_date"),
                                                        jsRaftInventory.getJSONObject(i).getString("adventure_sport")
                                                ));
                                            } else if (jsRaftInventory.getJSONObject(i).getString("adventure_sport").equalsIgnoreCase("rafting")) {
                                                int personCount = Integer.parseInt(jsRaftInventory.getJSONObject(i).getString("adult")
                                                        + Integer.parseInt(jsRaftInventory.getJSONObject(i).getString("children_blow_5_year"))
                                                        + Integer.parseInt(jsRaftInventory.getJSONObject(i).getString("children_above_5_year")));
                                                vendorListBooking.add(new Order_Beans(
                                                        jsRaftInventory.getJSONObject(i).getString("user_name"),
                                                        jsRaftInventory.getJSONObject(i).getString("user_email"),
                                                        jsRaftInventory.getJSONObject(i).getString("mobile_no"),
                                                        jsRaftInventory.getJSONObject(i).getString("package_name"),
                                                        String.valueOf(personCount),
                                                        jsRaftInventory.getJSONObject(i).getString("vendorName"),
                                                        jsRaftInventory.getJSONObject(i).getString("booking_date"),
                                                        jsRaftInventory.getJSONObject(i).getString("order_date"),
                                                        jsRaftInventory.getJSONObject(i).getString("paymentStatus"),
                                                        jsRaftInventory.getJSONObject(i).getString("starting_point_name"),
                                                        jsRaftInventory.getJSONObject(i).getString("adventure_sport")
                                                ));
                                            } else {
                                                Toast.makeText(context, "Nothing Special For You", Toast.LENGTH_SHORT).show();
                                            }*/
                                }
                                setListAdapter(vendorListBooking);
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
                    params.put("action", UtilsUrl.Action_orderHistory);
                    params.put("user_id", SharedPref.getUserID());

                    Log.e("Param Response ", "" + params);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(context, "Check Your connetion", Toast.LENGTH_LONG).show();
        }


    }

    private void setListAdapter(List<Order_Beans> order_beansList) {
        recyclerview_inventory_history.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerview_inventory_history.setAdapter(new UserOrderRecycAdapter(context, order_beansList));
    }


    private void createIDs() {
        prog = new SpotsDialog(context, R.style.Custom);
        recyclerview_inventory_history = findViewById(R.id.recyclerview_inventory_history);
    }

    private void setToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Order History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onItemMasterClick(int position) {

    }

}