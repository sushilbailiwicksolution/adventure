package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.ActivityVendor;

import android.app.DatePickerDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import dmax.dialog.SpotsDialog;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Adapter.CampInventoryListRecyclerAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Adapter.RaftInventoryListRecyclerAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.App.AppController1;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.AppUtils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Modal.CampInventory_Beans;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Modal.RaftingInventory_Beans;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Support.RootActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Utils.Itags;

/**
 * Created by Prince on 08-03-2018.
 */

public class ActivityRAftingInventory extends RootActivity implements RaftInventoryListRecyclerAdapter.ItemClickMasterMenuListInterface {

    private Context context;
    SpotsDialog prog;
    private Button btn_select_date;
    private TextView txt_selected_date;


    RecyclerView recyclerview_inventory_rafting;
    ArrayList<RaftingInventory_Beans> rafting_inventoryList;
    RaftInventoryListRecyclerAdapter raftInventoryListRecyclerAdapter;

    private SimpleDateFormat dateFormatter;
    Calendar newCalendar;
    private DatePickerDialog checkIN_PickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.raft_inventory);

        context = ActivityRAftingInventory.this;
        setToolbar();
        createIDS();
        //createList();
        dateListenr();

        clickListner();


    }


    private void clickListner() {

    }

    private void GetTodaysSeats() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String formattedDate = df.format(c);
        txt_selected_date.setText(formattedDate);
        getRaftDetail(formattedDate);

    }

    private void dateListenr() {
        btn_select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkIN_PickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        txt_selected_date.setText(dateFormatter.format(newDate.getTime()));

                        getRaftDetail(dateFormatter.format(newDate.getTime()));

                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                checkIN_PickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                long now = System.currentTimeMillis() - 1000;
                checkIN_PickerDialog.getDatePicker().setMinDate(now);
                checkIN_PickerDialog.getDatePicker().setMaxDate(now + (1000 * 60 * 60 * 24 * 14));
                checkIN_PickerDialog.show();
            }
        });
    }

    private void getRaftDetail(final String selected_date) {

        Log.e("on Process", "On Process...");

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

                                        JSONArray jsRaftInventory = jsData.getJSONArray("data");
                                        rafting_inventoryList.clear();

                                        for (int i = 0; i < jsRaftInventory.length(); i++) {


                                            rafting_inventoryList.add(new RaftingInventory_Beans(
                                                    jsRaftInventory.getJSONObject(i).getString("rafting_id"),
                                                    jsRaftInventory.getJSONObject(i).getString("package_name"),
                                                    jsRaftInventory.getJSONObject(i).getString("starting_point"),
                                                    jsRaftInventory.getJSONObject(i).getString("id"),

                                                    jsRaftInventory.getJSONObject(i).getJSONObject("start_time").getString("value"),
                                                    jsRaftInventory.getJSONObject(i).getJSONObject("start_time").getString("id"),
                                                    jsRaftInventory.getJSONObject(i).getString("total_seat"), txt_selected_date.getText().toString()
                                            ));

                                        }
                                        raftInventoryListRecyclerAdapter.notifyDataSetChanged();


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

                params.put("action", UtilsUrl.Action_VendorRaftDetails);
                params.put("vendor_id", SharedPref.getUserID());
                params.put("startDate", selected_date);

                Log.e("Param Response ", "" + params);
                return params;
            }
        };

        AppController1.getInstance().addToRequestQueue(stringRequest);


    }


    private void createIDS() {
        prog = new SpotsDialog(context, R.style.Custom);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        newCalendar = Calendar.getInstance();

        recyclerview_inventory_rafting = (RecyclerView) findViewById(R.id.recyclerview_inventory_rafting);
        btn_select_date = (Button) findViewById(R.id.btn_select_date);
        txt_selected_date = (TextView) findViewById(R.id.txt_selected_date);

        rafting_inventoryList = new ArrayList<>();
        raftInventoryListRecyclerAdapter = new RaftInventoryListRecyclerAdapter(context, rafting_inventoryList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setAutoMeasureEnabled(true);
        recyclerview_inventory_rafting.setLayoutManager(mLayoutManager);
        recyclerview_inventory_rafting.setItemAnimator(new DefaultItemAnimator());
        recyclerview_inventory_rafting.setAdapter(raftInventoryListRecyclerAdapter);
        txt_selected_date.setText("");
        // txt_selected_date.setVisibility(View.GONE);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("INVENTORY");


    }

    @Override
    protected void onResume() {
        GetTodaysSeats();
        super.onResume();
    }

    @Override
    public void onItemMasterClick(int position) {

        Intent i = new Intent(ActivityRAftingInventory.this, Activity_Raft_Seat_Control.class);
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
