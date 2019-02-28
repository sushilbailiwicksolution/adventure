package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.activity_vendor;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import dmax.dialog.SpotsDialog;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.adapter.RaftInventRecyclerAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.app.AppController;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.apputils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model.RaftingInventoryBeans;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.RootActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Const;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Itags;

public class ActivityRaftingInventory extends RootActivity implements RaftInventRecyclerAdapter.ItemClickMasterMenuListInterface {

    private Context context;
    SpotsDialog prog;
    private Button btn_select_date;
    private TextView txt_selected_date;

    RecyclerView recyclerview_inventory_rafting;
    ArrayList<RaftingInventoryBeans> rafting_inventoryList;
    RaftInventRecyclerAdapter raftInventRecyclerAdapter;

    private SimpleDateFormat dateFormatter;
    Calendar newCalendar;
    private DatePickerDialog checkInPickerDialog;
    private String service_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.raft_inventory);
        context = ActivityRaftingInventory.this;
        setToolbar();
        createIDS();
        getExtraData();
        dateListener();
    }

    private void getExtraData() {
        if (getIntent().getExtras() != null) {
            service_id = getIntent().getExtras().getString("service_id");
        }
    }

    private void getSeats() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = df.format(c);
        txt_selected_date.setText(formattedDate);
        getRaftDetail(formattedDate);

    }

    private void dateListener() {
        btn_select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInPickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        txt_selected_date.setText(dateFormatter.format(newDate.getTime()));

                        getRaftDetail(dateFormatter.format(newDate.getTime()));

                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                checkInPickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                long now = System.currentTimeMillis() - 1000;
                checkInPickerDialog.getDatePicker().setMinDate(now);
                checkInPickerDialog.getDatePicker().setMaxDate(now + (1000 * 60 * 60 * 24 * 14));
                checkInPickerDialog.show();
            }
        });
    }

    private void getRaftDetail(final String selected_date) {

        Log.e("on Process", "On Process...");

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

                            String status = jsData.getString("status");
                            if (status.equalsIgnoreCase("1")) {

                                JSONArray jsRaftInventory = jsData.getJSONArray("data");
                                rafting_inventoryList.clear();

                                for (int i = 0; i < jsRaftInventory.length(); i++) {
                                    rafting_inventoryList.add(new RaftingInventoryBeans(jsRaftInventory.getJSONObject(i).getString("rafting_id"),
                                            jsRaftInventory.getJSONObject(i).getString("package_name"),
                                            jsRaftInventory.getJSONObject(i).getString("starting_point"),
                                            jsRaftInventory.getJSONObject(i).getString("id"),
                                            jsRaftInventory.getJSONObject(i).getJSONObject("start_time").getString("value"),
                                            jsRaftInventory.getJSONObject(i).getJSONObject("start_time").getString("id"),
                                            jsRaftInventory.getJSONObject(i).getString("total_seat"),
                                            txt_selected_date.getText().toString()));
                                }
                                raftInventRecyclerAdapter.notifyDataSetChanged();
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
                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> header = new HashMap<>();
                    header.put(Itags.Header, Const.APP_TOKEN);
                    return header;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    if (service_id.equalsIgnoreCase(Itags.RAFTING)) {
                        params.put("action", UtilsUrl.Action_VendorRaftDetails);
                    } else if (service_id.equalsIgnoreCase(Itags.Bungy)) {
                        params.put("action", UtilsUrl.Action_VendorRaftDetails);
                    }
                    params.put("vendor_id", SharedPref.getUserID());
                    params.put("startDate", selected_date);

                    Log.e("Request Params ", "" + params);
                    return params;
                }
            };

            AppController.getInstance().addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }


    private void createIDS() {
        prog = new SpotsDialog(context, R.style.Custom);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        newCalendar = Calendar.getInstance();

        recyclerview_inventory_rafting = findViewById(R.id.recyclerview_inventory_rafting);
        btn_select_date = findViewById(R.id.btn_select_date);
        txt_selected_date = findViewById(R.id.txt_selected_date);

        rafting_inventoryList = new ArrayList<>();
        raftInventRecyclerAdapter = new RaftInventRecyclerAdapter(context, rafting_inventoryList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setAutoMeasureEnabled(true);
        recyclerview_inventory_rafting.setLayoutManager(mLayoutManager);
        recyclerview_inventory_rafting.setItemAnimator(new DefaultItemAnimator());
        recyclerview_inventory_rafting.setAdapter(raftInventRecyclerAdapter);
        txt_selected_date.setText("");
        // txt_selected_date.setVisibility(View.GONE);
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Rafting Inventory");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.clear_black));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        getSeats();
        super.onResume();
    }


    @Override
    public void onItemMasterClick(int position) {

        Intent i = new Intent(ActivityRaftingInventory.this, ActivityRaftControl.class);
        i.putExtra("id", rafting_inventoryList.get(position).getStr_id());
        i.putExtra("starting_point", rafting_inventoryList.get(position).getStr_start_point());
        i.putExtra("starting_pointID", rafting_inventoryList.get(position).getStr_start_pointID());
        i.putExtra("select_time", rafting_inventoryList.get(position).getStr_time());
        i.putExtra("select_timeID", rafting_inventoryList.get(position).getStr_timeID());
        i.putExtra("seat_available", rafting_inventoryList.get(position).getStr_seats_avilable());
        i.putExtra("selected_date", rafting_inventoryList.get(position).getStr_selected_date());

        startActivity(i);
    }
}
