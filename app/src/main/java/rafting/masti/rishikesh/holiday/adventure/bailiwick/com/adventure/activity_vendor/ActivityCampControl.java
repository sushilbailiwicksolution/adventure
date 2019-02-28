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
import java.util.Objects;

import dmax.dialog.SpotsDialog;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.app.AppController;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.number_picker.NumberPicker;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.apputils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.RootActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Const;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Itags;


public class ActivityCampControl extends RootActivity {

    public NumberPicker picker_room;
    private DatePickerDialog checkIN_PickerDialog, checkOUT_PickerDialog;
    Context mContext;
    public TextView txt_Check_in, txt_check_out, txt_service_name;
    private SimpleDateFormat dateFormatter;
    String str_room_typeID, str_CampID;
    private Button btn_Apply, btn_cancel;
    private RadioGroup rgp_raft_control;
    RadioButton rdb_open_raft, rdb_close_raft;
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
            Toast.makeText(getApplicationContext(), "Please select rooms has to open or close", Toast.LENGTH_LONG).show();
        } else {
            int selectedId = rgp_raft_control.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(selectedId);
            String rdb_value = radioButton.getText().toString();
            String action_take = "";
            if (rdb_value.equalsIgnoreCase("Open Room")) {
                action_take = "Add";
            } else if (rdb_value.equalsIgnoreCase("Close Room")) {
                action_take = "Sub";
            } else {
                Toast.makeText(getApplicationContext(), "Please select Room has to open or close", Toast.LENGTH_LONG).show();
            }
            if (txt_check_out.getText().toString().equalsIgnoreCase("To")) {
                Toast.makeText(getApplicationContext(), "Please select Room To Date", Toast.LENGTH_LONG).show();
            } else {
                SetnewSeats(txt_Check_in.getText().toString(), action_take, txt_check_out.getText().toString(), str_room_typeID, str_CampID, picker_room.getValue());
            }
        }
    }

    private void SetnewSeats(final String start_date, final String action_take, final String end_date, final String str_room_typeID, final String str_campID, final int value) {

        if (new CheckConnectivity().isConnected(mContext)) {

            prog.setTitle("Please wait.");
            prog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        prog.dismiss();
                        Log.e("Response : => ", response);
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
                    Map<String, String> header = new HashMap<>();
                    header.put(Itags.Header, Const.APP_TOKEN);
                    return header;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("action", UtilsUrl.Action_VendorCampUpdate);
                    params.put("vendor_id", SharedPref.getUserID());
                    params.put("startDate", start_date);
                    params.put("end_date", end_date);
                    params.put("camping_id", str_campID);
                    params.put("room_type_id", str_room_typeID);
                    params.put("no_of_room", "" + value);
                    params.put("update_action", action_take);

                    Log.e("Request Params", "" + params);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
        } else {
            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void getExtra() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            str_room_typeID = extras.getString("room_typeID");
            str_CampID = extras.getString("camp_ID");
            txt_Check_in.setText(extras.getString("select_date"));
            //       picker_room.setValue(Integer.parseInt(extras.getString("Available")));
            picker_room.setValue(0);
            String camp_name = extras.getString("camp_name");

            txt_service_name.setText(camp_name);
        }
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        // toolbar.setTitle("Inventory");
        Objects.requireNonNull(getSupportActionBar()).setTitle("INVENTORY");
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

                        try {
/*
                            DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                            DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd ");

                            String inputDateStr = txt_Check_in.getText().toString();
                            Date date_check_in = inputFormat.parse(inputDateStr);
// check out date
                            String outDateStr = txt_check_out.getText().toString();
                            Date date_check_out = inputFormat.parse(outDateStr);

*/
                            //     String IN_DateStr = outputFormat.format(date_check_in);
                            //    String OUT_DateStr = outputFormat.format(date_check_out);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

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

        mContext = ActivityCampControl.this;
        prog = new SpotsDialog(mContext, R.style.Custom);
        txt_Check_in = findViewById(R.id.txt_booking_date);
        txt_check_out = findViewById(R.id.txt_check_out);
        txt_service_name = findViewById(R.id.txt_service_name);
        picker_room = findViewById(R.id.picker_rooms);

        btn_Apply = findViewById(R.id.btn_Apply);
        btn_cancel = findViewById(R.id.btn_cancel);

        rgp_raft_control = findViewById(R.id.rgp_camp_control);
        rdb_open_raft = findViewById(R.id.rdb_open_camp);
        rdb_close_raft = findViewById(R.id.rdb_close_camp);
    }
}
