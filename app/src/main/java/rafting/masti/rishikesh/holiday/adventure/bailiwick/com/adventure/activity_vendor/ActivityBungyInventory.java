package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.activity_vendor;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import dmax.dialog.SpotsDialog;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.adapter_vendor.BungeeInventRecyclerAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.app.AppController;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.apputils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model_vendor.BungeeInventBeans;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.RootActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Const;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Itags;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Tracer;

public class ActivityBungyInventory extends RootActivity implements BungeeInventRecyclerAdapter.ItemClickInterface {

    private Context context;
    SpotsDialog prog;
    TextView tvSelectedDate;
    Button btnSelectDate;
    RecyclerView rvInventoryBungee;
    List<BungeeInventBeans> bungeeBeansList;
    BungeeInventRecyclerAdapter bungeeInventRecyclerAdapter;
    String serviceId;
    private DatePickerDialog checkInPickerDialog;
    private SimpleDateFormat dateFormatter;
    Calendar newCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bungy_inventory);
        context = this;
        setToolbar();
        getUiObject();
        serviceId = getExtra();
        dateListener();
    }

    private void dateListener() {
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInPickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        tvSelectedDate.setText(dateFormatter.format(newDate.getTime()));

                        getBungeeDetail(dateFormatter.format(newDate.getTime()));

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

    @Override
    protected void onResume() {
        getSeats();
        super.onResume();
    }

    private void getSeats() {
        Date c = Calendar.getInstance().getTime();
        Tracer.debugLog("Current Time", "" + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        tvSelectedDate.setText(formattedDate);
        getBungeeDetail(formattedDate);
    }

    private void getBungeeDetail(final String date){
        if (new CheckConnectivity().isConnected(this)){
            prog.setTitle("Please Wait...");
            prog.setCancelable(false);
            prog.show();
            StringRequest bugeeDetailRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    prog.dismiss();
                    if (response != null) {
                        try {
                            JSONObject bungeeDetailRes = new JSONObject(response);
                            if (bungeeDetailRes.getString("status").equalsIgnoreCase("1")){
                                JSONArray jsBungeeInv = bungeeDetailRes.getJSONArray("data");
                                bungeeBeansList.clear();

                                for (int i = 0; i < jsBungeeInv.length(); i++) {
                                    bungeeBeansList.add(new BungeeInventBeans(jsBungeeInv.getJSONObject(i).getString("bungee_id"),
                                            jsBungeeInv.getJSONObject(i).getString("package_name"),
                                            jsBungeeInv.getJSONObject(i).getString("starting_point"),
                                            jsBungeeInv.getJSONObject(i).getString("id"),
                                            jsBungeeInv.getJSONObject(i).getJSONObject("start_time").getString("value"),
                                            jsBungeeInv.getJSONObject(i).getJSONObject("start_time").getString("id"),
                                            jsBungeeInv.getJSONObject(i).getString("total_seat"),
                                            tvSelectedDate.getText().toString()));
                                }
                                bungeeInventRecyclerAdapter.notifyDataSetChanged();
                            }else{
                                Tracer.debugLog("Status",bungeeDetailRes.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Tracer.debugLog("Response","is null");
                        Toast.makeText(context, "Invalid Data", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    prog.dismiss();
                    Tracer.debugLog("Bungee Detail Error",error.toString());
                }
            }){
                @Override
                public Map<String, String> getHeaders() {
                    Map<String,String> headers = new HashMap<>();
                    headers.put(Itags.Header, Const.APP_TOKEN);
                    return headers;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String,String> params = new HashMap<>();
                    params.put(Const.KEY_ACTION,"getVendorBungeeList");
                    params.put("vendor_id", SharedPref.getUserID());
                    params.put("startDate", date);

                    Log.e("Request Params ", "" + params);
                    return params;
                }

            };
            AppController.getInstance().addToRequestQueue(bugeeDetailRequest);
        }else{
            Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }
    }

    private String getExtra() {
        if (getIntent().getExtras() != null) {
            return getIntent().getExtras().getString("service_id");
        } else {
            return "";
        }
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Bungy Inventory");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.clear_black));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getUiObject() {
        tvSelectedDate = findViewById(R.id.tvSelectedDate);
        btnSelectDate = findViewById(R.id.btnSelectDate);
        rvInventoryBungee = findViewById(R.id.rvInventoryBungee);
        prog = new SpotsDialog(context, R.style.Custom);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    }

    @Override
    public void onItemClick(int position) {
        Intent i = new Intent(this, ActivityRaftControl.class);
        i.putExtra("id", bungeeBeansList.get(position).getId());
        i.putExtra("starting_point", bungeeBeansList.get(position).getStartPoint());
        i.putExtra("starting_pointId", bungeeBeansList.get(position).getStartPointId());
        i.putExtra("selected_time", bungeeBeansList.get(position).getTime());
        i.putExtra("selectedtimeId", bungeeBeansList.get(position).getTimeId());
        i.putExtra("available_seat", bungeeBeansList.get(position).getAvailableSeats());
        i.putExtra("selected_date", bungeeBeansList.get(position).getSelectedDate());

        startActivity(i);
    }
}
