package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.activity_vendor;

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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.app.AppController;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.number_picker.NumberPicker;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.apputils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.RootActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Itags;


public class ActivityCycleControl extends RootActivity {

    public NumberPicker picker_room;
    private DatePickerDialog checkIN_PickerDialog, checkOUT_PickerDialog;
    Context mContext;
    public TextView txt_Check_in, txt_check_out, txt_available_service, txt_service_name;
    private SimpleDateFormat dateFormatter;

    String str_service_type;

    private Button btn_Apply, btn_cancel;

    private RadioGroup rgp_raft_control;
    RadioButton rdb_open_raft, rdb_close_raft;
    String radio_text_open, radio_text_close;
    String bike_id;
    String Available_text;


    SpotsDialog prog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camp_control);
        setToolbar();
        createIDS();
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
            Toast.makeText(getApplicationContext(), "Please select seats to be Open or close ", Toast.LENGTH_LONG).show();


        } else {
            int selectedId = rgp_raft_control.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(selectedId);
            String rdb_value = radioButton.getText().toString();
            int selected_id = radioButton.getId();
            String action_take = "";


            if (rdb_value.equalsIgnoreCase(radio_text_open)) {
                action_take = "Add";

            } else if (rdb_value.equalsIgnoreCase(radio_text_close)) {
                action_take = "Sub";
            } else {
                Toast.makeText(getApplicationContext(), "Please select seats to be Open or close ", Toast.LENGTH_LONG).show();
            }
            if (txt_check_out.getText().toString().equalsIgnoreCase("To")) {
                Toast.makeText(getApplicationContext(), "Please select Avablity To Date", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "On Process", Toast.LENGTH_LONG).show();
                Log.e("Values", txt_Check_in.getText().toString() + " " + action_take + " " + txt_check_out.getText().toString() + " " + picker_room.getValue()+"  bike id "+bike_id);

                //      SetnewSeats(txt_Check_in.getText().toString(), action_take, txt_check_out.getText().toString(), "", "", picker_room.getValue());

            }

        }

    }

    private void SetnewSeats(final String start_date, final String action_take, final String end_date, final String str_room_typeID, final String str_campID, final int value) {
        Log.e("on Process", "On Process...");

        prog.setTitle("Please wait.");
        prog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
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
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<String, String>();
                header.put(Itags.Header, "ABC98XYZ53IJ61L");
                // params.put("Accept-Language", "fr");

                return header;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                if (str_service_type.equalsIgnoreCase(Itags.Cycle)) {
                    params.put("no_of_cycle", "" + value);
                    params.put("action", UtilsUrl.Action_VendorCampUpdate);
                }  else if (str_service_type.equalsIgnoreCase(Itags.Biking)) {
                    params.put("no_of_bike", "" + value);
                    params.put("action", UtilsUrl.Action_VendorCampUpdate);
                    params.put("bike_id", bike_id);

                }
                params.put("vendor_id", SharedPref.getUserID());
                params.put("startDate", start_date);
                params.put("end_date", end_date);
                params.put("update_action", action_take);

                Log.e("Param Response ", "" + params);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);


    }

    private void getExtra() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            str_service_type = extras.getString("service_type");
            txt_Check_in.setText(extras.getString("select_date"));
            picker_room.setValue(0);
            if (str_service_type.equalsIgnoreCase(Itags.Cycle)) {
                radio_text_open = "Open Cycle";
                radio_text_close = "Close Cycle";

                txt_available_service.setText("Cycle Available");
                txt_service_name.setText("Cycle");
            } else if (str_service_type.equalsIgnoreCase(Itags.Biking)) {
                radio_text_open = "Open Bike";
                radio_text_close = "Close Bike";
                bike_id = extras.getString("bike_id");
                String bike_name = extras.getString("bike_name");
                txt_service_name.setText(bike_name);
                txt_available_service.setText("Bike Available");
            }
            rdb_open_raft.setText(radio_text_open);
            rdb_close_raft.setText(radio_text_close);

        }
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        // toolbar.setTitle("Inventory");
        getSupportActionBar().setTitle("INVENTORY");

    }


    private void DateClickListenr() {
        txt_Check_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);


                Calendar newCalendar = Calendar.getInstance();

                checkIN_PickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        txt_Check_in.setText(dateFormatter.format(newDate.getTime()));
                        txt_check_out.setText("Check out");
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                checkIN_PickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                long now = System.currentTimeMillis() - 1000;
                checkIN_PickerDialog.getDatePicker().setMinDate(now);
                checkIN_PickerDialog.getDatePicker().setMaxDate(now + (1000 * 60 * 60 * 24 * 14));
                checkIN_PickerDialog.show();

            }
        });
        txt_check_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);


                Calendar newCalendar = Calendar.getInstance();

                checkOUT_PickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        txt_check_out.setText(dateFormatter.format(newDate.getTime()));


                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                checkOUT_PickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                long now = System.currentTimeMillis() - 1000;
                checkOUT_PickerDialog.getDatePicker().setMinDate(now);
                checkOUT_PickerDialog.getDatePicker().setMaxDate(now + (1000 * 60 * 60 * 24 * 14));
                checkOUT_PickerDialog.show();


            }
        });


    }

    private void createIDS() {

        mContext = ActivityCycleControl.this;
        prog = new SpotsDialog(mContext, R.style.Custom);

        txt_Check_in = findViewById(R.id.txt_booking_date);
        txt_check_out = findViewById(R.id.txt_check_out);
        txt_available_service = findViewById(R.id.txt_available_service);
        txt_service_name = findViewById(R.id.txt_service_name);
        picker_room = findViewById(R.id.picker_rooms);

        btn_Apply = findViewById(R.id.btn_Apply);
        btn_cancel = findViewById(R.id.btn_cancel);

        rgp_raft_control = findViewById(R.id.rgp_camp_control);
        rdb_open_raft = findViewById(R.id.rdb_open_camp);
        rdb_close_raft = findViewById(R.id.rdb_close_camp);
    }
}
