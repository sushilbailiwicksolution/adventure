package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.ActivityVendor;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.App.AppController1;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.AppUtils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Modal.RaftingInventory_Beans;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Number_picker.NumberPicker;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Support.RootActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Utils.Itags;

/**
 * Created by Prince on 12-03-2018.
 */

public class Activity_Raft_Seat_Control extends RootActivity {
    private Context mContext;
    public NumberPicker picker_room;
    private DatePickerDialog checkIN_PickerDialog;
    public TextView txt_Check_in, txt_starting_point, txt_timing, txt_avail_seat;
    private SimpleDateFormat dateFormatter;
    private Button btn_Apply, btn_cancel;

    private RadioGroup rgp_raft_control;
    RadioButton rdb_open_raft, rdb_close_raft;
    String strID, strStartingPointID, strTimeID;
    SpotsDialog prog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raft_control);
        createIDS();
        setToolbar();
        getExtra();
        DateClickListenr();
        clickListner();

    }

    private void clickListner() {
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Validation();

            }
        });
    }

    private void Validation() {
        if (rgp_raft_control.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "Please select Seats has to open or close", Toast.LENGTH_LONG).show();


        } else {
            int selectedId = rgp_raft_control.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton) findViewById(selectedId);
            String rdb_value = radioButton.getText().toString();
            String action_take = "";
            if (rdb_value.equalsIgnoreCase("Open raft")) {
                action_take = "Add";

            } else if (rdb_value.equalsIgnoreCase("Close Raft")) {
                action_take = "Sub";
            } else {
                Toast.makeText(getApplicationContext(), "Please select Seats has to open or close", Toast.LENGTH_LONG).show();
            }
            SetnewSeats(strID, txt_starting_point.getText().toString(), strTimeID, picker_room.getValue(), txt_Check_in.getText().toString(), action_take);

        }

    }

    private void SetnewSeats(final String strID, final String strStartingPointID, final String strTimeID, final int value, final String select_date, final String action_take) {
        Log.e("on Process", "On Process...");

        prog.setTitle("Please wait.");
        prog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        if (new CheckConnectivity().isConnected(mContext)) {
                            try {
                                prog.dismiss();
                                Log.e("Response : prince ", response);
                                if (response != null) {
                                    JSONObject jsData = new JSONObject(response);

                                    String status = jsData.getString("status");
                                    if (status.equalsIgnoreCase("1")) {

                                        String msg = jsData.getString("msg");
                                        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
                                        finish();

                                    } else {
                                        String msg = jsData.getString("msg");
                                        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();

                                    }
                                } else {
                                    Toast.makeText(mContext, "Invalid Response !!!", Toast.LENGTH_LONG).show();

                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();

                            }
                        } else {
                            Toast.makeText(mContext, "Check Your connetion", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                prog.dismiss();
                Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG).show();

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

                params.put("action", UtilsUrl.Action_VendorRaftUpdate);
                params.put("vendor_id", SharedPref.getUserID());
                params.put("startDate", select_date);
                params.put("start_points_id", strStartingPointID);
                params.put("start_time", strTimeID);
                params.put("vendor_id", SharedPref.getUserID());
                params.put("no_of_seat", "" + value);
                params.put("update_action", action_take);
                params.put("raft_id", strID);

                Log.e("Param Response ", "" + params);
                return params;
            }
        };

        AppController1.getInstance().addToRequestQueue(stringRequest);


    }

    private void DateClickListenr() {
        txt_Check_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


                Calendar newCalendar = Calendar.getInstance();

                checkIN_PickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        txt_Check_in.setText(dateFormatter.format(newDate.getTime()));

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

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        // toolbar.setTitle("Inventory");
        getSupportActionBar().setTitle("INVENTORY");

    }

    private void getExtra() {
        prog = new SpotsDialog(mContext, R.style.Custom);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            strID = extras.getString("id");
            strStartingPointID = extras.getString("starting_pointID");
            strTimeID = extras.getString("select_timeID");

            txt_Check_in.setText(extras.getString("selected_date"));
            txt_avail_seat.setText(extras.getString("seat_available"));
            txt_timing.setText(extras.getString("select_time"));
            txt_starting_point.setText(extras.getString("starting_point"));
            //picker_room.setValue(Integer.parseInt(txt_avail_seat.getText().toString()));
            picker_room.setValue(0);

        }


    }

    private void createIDS() {
        mContext = Activity_Raft_Seat_Control.this;

        txt_Check_in = (TextView) findViewById(R.id.txt_booking_date);
        txt_starting_point = (TextView) findViewById(R.id.txt_starting_point);
        txt_timing = (TextView) findViewById(R.id.txt_timing);
        txt_avail_seat = (TextView) findViewById(R.id.txt_avail_seat);
        picker_room = (NumberPicker) findViewById(R.id.picker_rooms);

        btn_Apply = (Button) findViewById(R.id.btn_Apply);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        rgp_raft_control = (RadioGroup) findViewById(R.id.rgp_raft_control);
        rdb_open_raft = (RadioButton) findViewById(R.id.rdb_open_raft);
        rdb_close_raft = (RadioButton) findViewById(R.id.rdb_close_raft);
    }

}
