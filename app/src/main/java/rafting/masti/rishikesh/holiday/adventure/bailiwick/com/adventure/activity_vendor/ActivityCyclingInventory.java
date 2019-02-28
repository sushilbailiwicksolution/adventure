package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.activity_vendor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import dmax.dialog.SpotsDialog;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.adapter_vendor.CycleInventRecyclerAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.app.AppController;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model_vendor.CycleInventoryBeans;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.apputils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.RootActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Const;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Itags;


public class ActivityCyclingInventory extends RootActivity implements CycleInventRecyclerAdapter.ItemClickMasterMenuListInterface {
    private Context context;
    SpotsDialog prog;


    RecyclerView recyclerViewCamp_inventory;
    ArrayList<CycleInventoryBeans> cycle_inventoryList;
    CycleInventRecyclerAdapter campInventoryListRecyclerAdapter;

    private TextView txt_prev_date_schedule, txt_next_date_schedule;
    List<String> campListID = new ArrayList<>();


    String todaysDate, next_week_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cycle_inventory);
        context = ActivityCyclingInventory.this;
        setToolbar();
        createIDS();
        GetTodaysSeats();
        getCycleDetail(SharedPref.getUserID(), "21", todaysDate, next_week_date);

        clickListner();

    }

    private void clickListner() {
        txt_next_date_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cycle_inventoryList.size() > 0) {

                    String str_current_date = cycle_inventoryList.get(cycle_inventoryList.size() - 1).getStr_date();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    Date NextToDate = getDate(str_current_date);
                    Date next7date = getNext7Date(NextToDate);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String next_week_date = format.format(next7date);
                    getCycleDetail(SharedPref.getUserID(), "21", str_current_date, next_week_date);
                } else {
                    Toast.makeText(getApplicationContext(), "There is no Camp Detail", Toast.LENGTH_LONG).show();
                }
            }
        });
        txt_prev_date_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cycle_inventoryList.size() > 0) {
                    String str_current_date = cycle_inventoryList.get(0).getStr_date();

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    Date NextToDate = getDate(str_current_date);
                    Date Before7date = getPrevious7Date(NextToDate);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String next_week_date = format.format(Before7date);
                    getCycleDetail(SharedPref.getUserID(), "21", next_week_date, str_current_date);

                } else {
                    Toast.makeText(getApplicationContext(), "There is no Camp Detail", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void GetTodaysSeats() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        todaysDate = df.format(c);

        Date NextToDate = getDate(todaysDate);
        Date next7date = getNext7Date(NextToDate);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        next_week_date = format.format(next7date);

    }


    private void getCycleDetail(final String userID, final String CampId, final String Start_date, final String End_Date) {
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

                                JSONArray jsCampInventory = jsData.getJSONArray("inventory");
                                cycle_inventoryList.clear();
                                String current_date = Start_date;
                                Date date, NextToDate = null;

                                for (int i = 0; i < jsCampInventory.length(); i++) {

                                    String dayOfTheWeek;
                                    if (i == 0) {
                                        //   date = getDate(current_date);
                                        NextToDate = getDate(current_date);
                                        dayOfTheWeek = (String) DateFormat.format("EEEE", NextToDate);

                                    } else {
                                        date = getNextDate(NextToDate);
                                        NextToDate = date;
                                        dayOfTheWeek = (String) DateFormat.format("EEEE", date);
                                    }
                                    Log.e("Day ", dayOfTheWeek);
                                    Log.e("date", "" + NextToDate);
                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                    String str_date = format.format(NextToDate);
                                    cycle_inventoryList.add(new CycleInventoryBeans(
                                            jsCampInventory.getJSONObject(i).getString("room_type"),
                                            "" + str_date, dayOfTheWeek, jsCampInventory.getJSONObject(i).getString("total_rooms")
                                            , jsCampInventory.getJSONObject(i).getString("available_rooms"),
                                            jsCampInventory.getJSONObject(i).getString("booked_rooms")));

                                }
                                campInventoryListRecyclerAdapter.notifyDataSetChanged();
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

                    return header;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();

                    params.put("action", UtilsUrl.Action_VendorCampDetails);
                    params.put("vendor_id", userID);
                    params.put("camp_id", CampId);
                    params.put("startDate", Start_date);
                    params.put("endDate", End_Date);

                    Log.e("Param Response ", "" + params);
                    return params;
                }
            };

            AppController.getInstance().addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    public static Date getNextDate(Date curDate) {
        Date tomorrow = null;
        try {
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(curDate);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            tomorrow = calendar.getTime();
            return tomorrow;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return tomorrow;
    }

    public static Date getNext7Date(Date curDate) {
        Date tomorrow = null;
        try {
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(curDate);
            calendar.add(Calendar.DAY_OF_YEAR, 7);
            tomorrow = calendar.getTime();
            return tomorrow;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return tomorrow;
    }

    public static Date getPrevious7Date(Date curDate) {
        Date tomorrow = null;
        try {
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(curDate);
            calendar.add(Calendar.DAY_OF_YEAR, -7);
            tomorrow = calendar.getTime();
            return tomorrow;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return tomorrow;
    }

    private Date getDate(String current_date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = null;
        try {
            date = format.parse(current_date);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private void createIDS() {
        prog = new SpotsDialog(context, R.style.Custom);

        recyclerViewCamp_inventory = findViewById(R.id.recyclerview_inventory_camp);

        txt_prev_date_schedule = findViewById(R.id.txt_prev_date_schedule);
        txt_next_date_schedule = findViewById(R.id.txt_next_date_schedule);

        cycle_inventoryList = new ArrayList<>();
        campInventoryListRecyclerAdapter = new CycleInventRecyclerAdapter(context, cycle_inventoryList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setAutoMeasureEnabled(true);
        recyclerViewCamp_inventory.setLayoutManager(mLayoutManager);
        recyclerViewCamp_inventory.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCamp_inventory.setAdapter(campInventoryListRecyclerAdapter);
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("CYCLE INVENTORY");
    }

    @Override
    public void onItemMasterClick(int position) {
        Log.e("i m heree", "i m heree");
        Intent i = new Intent(ActivityCyclingInventory.this, ActivityCycleControl.class);
        i.putExtra("select_date", cycle_inventoryList.get(position).getStr_date());
        i.putExtra("camp_ID", "21");

        i.putExtra("service_type", Itags.Cycle);

        i.putExtra("Available", cycle_inventoryList.get(position).getStr_available_room());
        i.putExtra("Booked", cycle_inventoryList.get(position).getStr_booked_room());
        i.putExtra("Total", cycle_inventoryList.get(position).getStr_total_room());

        startActivity(i);
    }
}
