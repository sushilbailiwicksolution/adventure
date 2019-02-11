package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Rafting;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Activties.AboutRafting;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Activties.CartList;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.App.AppController1;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.AppUtils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Database.DBOperation;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Utils.Itags;


/**
 * Created by Prince on 22-01-2018.
 */

public class RaftingActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txt_amount, txt_avail_seat, txt_total_seat, txt_booking_date;
    private EditText edt_name, edt_email, edt_mobile;
    private Spinner spinner_time, spinner_raft_point, spinner_select_seat;
    private LinearLayout lnt_startpoint, lnt_bookingdate, lnt_seats_total, lnt_seats;
    private Button btn_book_now;

    Context context;
    SpotsDialog prog;
    String TEMPJSON;

    String sportsId, sportsImage, servicename, serviceTittle;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    List<String> timeListID = new ArrayList<>();
    List<String> pointListID = new ArrayList<>();

    private ImageView iv_image_rafting_activity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rafting_activity);
        context = this;
        getUiObject();

        setDetails();
        getRaftingDetail();

        setDataTiming();
        spinnerClick();
        GoneAllModule();
        SetDateListner();

    }

    private void SetDateListner() {

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Calendar newCalendar = Calendar.getInstance();

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                Log.e("date ", "" + year + " " + (monthOfYear - 1) + "" + dayOfMonth);

                txt_booking_date.setText(dateFormatter.format(newDate.getTime()));
                String IN_DateStr = "";
                try {
                    DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                    DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd ");
                    String inputDateStr = txt_booking_date.getText().toString();
                    Date date_check_in = inputFormat.parse(inputDateStr);
                    IN_DateStr = outputFormat.format(date_check_in);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                getRaftingSeat(IN_DateStr, pointListID.get(spinner_raft_point.getSelectedItemPosition()),
                        timeListID.get(spinner_time.getSelectedItemPosition() - 1));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        toDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        txt_booking_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toDatePickerDialog.show();
            }
        });
    }

    private void getRaftingSeat(final String selecteddate, final String selectingPoint, final String selectedTime) {
        Log.e("check", selecteddate + " point : " + selectingPoint + "  Time : " + selectedTime);

        prog.setTitle("Please wait.");
        prog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
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

                                JSONObject SeatsDetail = jsData.getJSONObject("seat_details");

                                if (SeatsDetail.getString("avg_price") == null || SeatsDetail.getString("avg_price").equals("") || SeatsDetail.getString("avg_price").equalsIgnoreCase("null")) {
                                    SeatSelection(View.INVISIBLE);
                                    Toast.makeText(context, "No Seat Avilable", Toast.LENGTH_LONG).show();
                                } else {
                                    SeatSelection(View.VISIBLE);
                                    txt_amount.setText(SeatsDetail.getString("avg_price"));
                                    txt_total_seat.setText(SeatsDetail.getString("total_seat"));
                                    txt_avail_seat.setText(SeatsDetail.getString("available_seat"));
                                    int avail_seat = Integer.parseInt(txt_avail_seat.getText().toString());

                                    List count = new ArrayList();
                                    for (int i = 0; i < avail_seat; i++) {
                                        count.add("" + (i + 1));
                                    }

                                    ArrayAdapter<String> adp1 = new ArrayAdapter<String>(RaftingActivity.this,
                                            android.R.layout.simple_list_item_1, count);
                                    adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinner_select_seat.setAdapter(adp1);

                                }


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

                params.put("action", UtilsUrl.Action_getRaftingSeats);
                params.put("selectedtime", selectedTime);
                params.put("start_date", selecteddate);
                params.put("start_pointsId", selectingPoint);

                Log.e("Param Response ", "" + params);
                return params;
            }
        };

        AppController1.getInstance().addToRequestQueue(stringRequest);

    }

    private void SeatSelection(int visiblity) {
        lnt_seats_total.setVisibility(visiblity);
        lnt_seats.setVisibility(visiblity);
        btn_book_now.setVisibility(visiblity);
    }

    private void GoneAllModule() {
        lnt_startpoint.setVisibility(View.GONE);
        lnt_bookingdate.setVisibility(View.GONE);
        lnt_seats.setVisibility(View.GONE);
        lnt_seats_total.setVisibility(View.GONE);

    }

    private void spinnerClick() {
        spinner_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    HideSubmodule(false);
                } else {
                    Log.e("id", timeListID.get(position - 1).toString());
                    txt_booking_date.setText("Select Date");
                    GetRaftingPoint(timeListID.get(position - 1).toString());
                    SeatSelection(View.INVISIBLE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_raft_point.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txt_booking_date.setText("Select Date");
                SeatSelection(View.INVISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void GetRaftingPoint(final String timeSlot) {
        prog.setTitle("Please wait.");
        prog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
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

                                JSONArray jsRaftingPoint = jsData.getJSONArray("points");
                                List<String> raftingPoint_list = new ArrayList<>();
//                                        raftingPoint_list.add("Select Point");
                                pointListID.clear();
                                for (int i = 0; i < jsRaftingPoint.length(); i++) {
                                    raftingPoint_list.add(jsRaftingPoint.getJSONObject(i).getString("point_name"));

                                    pointListID.add(jsRaftingPoint.getJSONObject(i).getString("point_id"));
                                }
                                ArrayAdapter<String> adp1 = new ArrayAdapter<String>(RaftingActivity.this,
                                        android.R.layout.simple_list_item_1, raftingPoint_list);
                                adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner_raft_point.setAdapter(adp1);

                                HideSubmodule(true);

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

                params.put("action", UtilsUrl.Action_getRaftingPoint);
                params.put("selectedtime", timeSlot);

                Log.e("Param Response ", "" + params);
                return params;
            }
        };

        AppController1.getInstance().addToRequestQueue(stringRequest);

    }

    private void setDataTiming() {
        try {
            if (TEMPJSON != null) {
                JSONObject jsData = new JSONObject(TEMPJSON);
                String status = jsData.getString("status");
                JSONObject raftingDetail = jsData.getJSONObject("rafting_details");
                String price = raftingDetail.getString("price");

                sportsId = raftingDetail.getString("id");
                sportsImage = raftingDetail.getString("images");
                servicename = raftingDetail.getString("service_name");
                serviceTittle = raftingDetail.getString("package_name");

                txt_amount.setText(price);
                JSONArray jsTime = jsData.getJSONArray("time_list");
                List<String> time_list = new ArrayList<>();
                time_list.add("Select Time");
                for (int i = 0; i < jsTime.length(); i++) {
                    time_list.add(jsTime.getJSONObject(i).getString("value"));
                    timeListID.add(jsTime.getJSONObject(i).getString("id"));
                }
                ArrayAdapter<String> adp1 = new ArrayAdapter<String>(RaftingActivity.this,
                        android.R.layout.simple_list_item_1, time_list);
                adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_time.setAdapter(adp1);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void HideSubmodule(boolean isEnable) {

        if (!isEnable) {
            lnt_bookingdate.setVisibility(View.GONE);
            lnt_startpoint.setVisibility(View.GONE);

        } else {
            lnt_bookingdate.setVisibility(View.VISIBLE);
            lnt_startpoint.setVisibility(View.VISIBLE);

        }

    }

    private void getUiObject() {

        prog = new SpotsDialog(context, R.style.Custom);
        txt_amount = findViewById(R.id.txt_amount);
        edt_name = findViewById(R.id.edt_name);
        edt_email = findViewById(R.id.edt_email);
        edt_mobile = findViewById(R.id.edt_mobile);

        iv_image_rafting_activity = findViewById(R.id.iv_image_rafting_activity);

        spinner_time = findViewById(R.id.spinner_time);
        spinner_raft_point = findViewById(R.id.spinner_raft_point);
        spinner_select_seat = findViewById(R.id.spinner_select_seat);

        txt_avail_seat = findViewById(R.id.txt_avail_seat);
        txt_total_seat = findViewById(R.id.txt_total_seat);

        lnt_startpoint = findViewById(R.id.lnt_startpoint);
        lnt_bookingdate = findViewById(R.id.lnt_bookingdate);
        lnt_seats_total = findViewById(R.id.lnt_seats_total);
        lnt_seats = findViewById(R.id.lnt_seats);
        txt_booking_date = findViewById(R.id.txt_booking_date);

        btn_book_now = findViewById(R.id.btn_book_now);
        TEMPJSON = SharedPref.getTempJSON();

        btn_book_now.setOnClickListener(this);
        iv_image_rafting_activity.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_book_now:
                validateInputs();
                break;
            case R.id.iv_image_rafting_activity:
                goToAboutRafting();
                break;

        }
    }

    private void goToAboutRafting() {
        Intent intent = new Intent(this, AboutRafting.class);
        startActivity(intent);
    }


    private void validateInputs() {
        try {
            Log.e("Check", "" + "i m heree");

            //DB_Function.ExportDatabasee(this(), Database_Utils.DB_NAME);

            SharedPref.saveTempemail(edt_email.getText().toString());
            SharedPref.saveTempfirstName(edt_name.getText().toString());
            SharedPref.saveTempmobile1(edt_mobile.getText().toString());
            if (!emailValidator(edt_email.getText().toString().trim())) {
                edt_email.setError("Invalid id");
                edt_email.requestFocus();
                return;
            }

            if (!mobileValidator(edt_mobile.getText().toString())) {
                edt_mobile.setError("Invalid Mobile");
                edt_email.requestFocus();
                return;
            }

            DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
            DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd ");
            String inputDateStr = txt_booking_date.getText().toString();
            Date date = inputFormat.parse(inputDateStr);
            String outputDateStr = outputFormat.format(date);

            String selecteSeats = spinner_select_seat.getSelectedItem().toString();
            String startingPoint = pointListID.get(spinner_raft_point.getSelectedItemPosition());
            String pricePerSeat = txt_amount.getText().toString();
            String timing = timeListID.get(spinner_time.getSelectedItemPosition() - 1);
            int total = (Integer.parseInt(pricePerSeat)) * (Integer.parseInt(selecteSeats));

            Log.e("raft count", "Sports Id : " + sportsId);

            int EntryExist = DBOperation.getRaftCount(this, sportsId);

            if (EntryExist > 0) {
                DBOperation.deleteCartItem(this, sportsId);
                Log.e("raft count", "Sports Id : Deleted");

            }

            boolean isSuccess = DBOperation.insertRaftingCart(this,
                    sportsId, serviceTittle, servicename, outputDateStr, timing, selecteSeats,
                    spinner_raft_point.getSelectedItem().toString(), spinner_time.getSelectedItem().toString(), startingPoint, outputDateStr, selecteSeats,
                    pricePerSeat, "" + total, sportsImage, Itags.RAFTING,
                    SharedPref.getTEMPfirstName(), SharedPref.getTempmobile1(), "", SharedPref.getTempemail());

            if (isSuccess) {
                Toast.makeText(this, "Saved in Database", Toast.LENGTH_SHORT).show();
                //DB_Function.ExportDatabasee(this(), Database_Utils.DB_NAME);
                Intent i = new Intent(this, CartList.class);
                startActivity(i);
                this.finish();
            } else {
                Log.e("Data no Saved", "Data not Saved");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setDetails() {
        edt_name.setText(SharedPref.getFirstName() + " " + SharedPref.getLastName());
        edt_mobile.setText(SharedPref.getMobile1());
        edt_email.setText(SharedPref.getEmail());
    }

    private void getRaftingDetail() {
        prog.setTitle("Please wait.");
        prog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
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

                                SharedPref.saveTempJSON(response);

                                JSONObject raftingDetail = jsData.getJSONObject("rafting_details");
                                String price = raftingDetail.getString("price");
                                txt_amount.setText(price);


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

                params.put("action", UtilsUrl.Action_getRaftingDetail);
                Log.e("Param Response ", "" + params);
                return params;
            }
        };

        AppController1.getInstance().addToRequestQueue(stringRequest);

    }


    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean mobileValidator(String mobile) {
        Pattern pattern;
        Matcher matcher;
        final String MOBILE_PATTERN = "^[6-9][0-9]{9}$";
        pattern = Pattern.compile(MOBILE_PATTERN);
        matcher = pattern.matcher(mobile);

        return matcher.matches();
    }

}
