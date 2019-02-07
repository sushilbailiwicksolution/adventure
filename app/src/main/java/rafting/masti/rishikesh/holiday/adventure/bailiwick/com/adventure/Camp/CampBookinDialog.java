package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Camp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import dmax.dialog.SpotsDialog;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.App.AppController1;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.AppUtils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.CustomDialog.SweetAlertDialog;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Modal.RoomTypeModel;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Number_picker.Enums.ActionEnum;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Number_picker.Interface.ValueChangedListener;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Number_picker.NumberPicker;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Utils.Itags;

/**
 * Created by Prince on 29-01-2018.
 */

public class CampBookinDialog extends Dialog {
    Context mContext;
    public TextView txt_Check_in, txt_check_out;
    private DatePickerDialog checkIN_PickerDialog, checkOUT_PickerDialog;
    private SimpleDateFormat dateFormatter;

    public NumberPicker picker_adult, picker_child;
    public TextView txt_price;
    public Spinner spinner_Room_Type;
    public List<String> RoomTypeshort = new ArrayList<>();
    public ArrayList<RoomTypeModel> roomTypefull = new ArrayList<>();


    public EditText edt_message, edt_mobile, edt_email, edt_name;
    public Button btn_Apply, btn_cancel;
    public int singleroomPrize = 0, doubleRoomPrice = 0;
    private String campID = "";
    SpotsDialog prog;

    public CampBookinDialog(Activity con, String jsResponse, String camp_id) {
        super(con);
        this.mContext = con;
        campID = camp_id;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.cst_dialog_book_camp);
        createIDS();
        getJsnonDetail(jsResponse);
        clicEvent();
        DateClickListenr();
        spinnerClickEvent();

    }

    private void spinnerClickEvent() {
        spinner_Room_Type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!roomTypefull.get(position).getStr_single_sell_rate().equalsIgnoreCase("")) {
                    //txt_price.setText(jsRoom_Type.getJSONObject(0).getString("single_sell_rate"));
                    singleroomPrize = Integer.parseInt(roomTypefull.get(position).getStr_single_sell_rate());


                } else {
                    singleroomPrize = 0;
                }
                if (!roomTypefull.get(position).getStr_double_sell_rate().equalsIgnoreCase("")) {

                    doubleRoomPrice = Integer.parseInt(roomTypefull.get(position).getStr_double_sell_rate());
                } else {
                    doubleRoomPrice = 0;
                }
                SetPrice(picker_adult.getValue(), singleroomPrize, doubleRoomPrice);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void getJsnonDetail(String jsResponse) {

        try {
            JSONObject jsDetail = new JSONObject(jsResponse);
            JSONArray jsRoom_Type = jsDetail.getJSONArray("camp_room");

            for (int i = 0; i < jsRoom_Type.length(); i++) {
                RoomTypeshort.add(jsRoom_Type.getJSONObject(i).getString("room_type_title"));
                roomTypefull.add(new RoomTypeModel(jsRoom_Type.getJSONObject(i).getString("room_type_id"),
                        jsRoom_Type.getJSONObject(i).getString("room_type_title"),
                        jsRoom_Type.getJSONObject(i).getString("description"),
                        jsRoom_Type.getJSONObject(i).getString("single_sell_rate"),
                        jsRoom_Type.getJSONObject(i).getString("double_sell_rate")));
                if (!jsRoom_Type.getJSONObject(0).getString("single_sell_rate").equalsIgnoreCase("")) {
                    txt_price.setText(jsRoom_Type.getJSONObject(0).getString("single_sell_rate"));
                    singleroomPrize = Integer.parseInt(txt_price.getText().toString());
                }
                if (!jsRoom_Type.getJSONObject(0).getString("double_sell_rate").equalsIgnoreCase("")) {

                    doubleRoomPrice = Integer.parseInt(jsRoom_Type.getJSONObject(0).getString("double_sell_rate"));
                }

                ArrayAdapter<String> adp1 = new ArrayAdapter<String>(mContext,
                        android.R.layout.simple_list_item_1, RoomTypeshort);
                adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_Room_Type.setAdapter(adp1);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
                        txt_check_out.setText("Check out");
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                checkIN_PickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                long now = System.currentTimeMillis() - 1000;
                checkIN_PickerDialog.getDatePicker().setMinDate(now);
                checkIN_PickerDialog.getDatePicker().setMaxDate(now + (1000 * 60 * 60 * 24 * 14));
                txt_Check_in.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkIN_PickerDialog.show();
                    }
                });
            }
        });
        txt_check_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


                Calendar newCalendar = Calendar.getInstance();

                checkOUT_PickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        txt_check_out.setText(dateFormatter.format(newDate.getTime()));

                        try {
                            DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                            DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd ");

                            String inputDateStr = txt_Check_in.getText().toString();
                            Date date_check_in = inputFormat.parse(inputDateStr);
// check out date
                            String outDateStr = txt_check_out.getText().toString();
                            Date date_check_out = inputFormat.parse(outDateStr);

                            String IN_DateStr = outputFormat.format(date_check_in);
                            String OUT_DateStr = outputFormat.format(date_check_out);

                            getRoomDetail(campID, IN_DateStr, OUT_DateStr, roomTypefull.get(spinner_Room_Type.getSelectedItemPosition()).getStr_room_type_id());

                        } catch (Exception ex) {

                            ex.printStackTrace();
                        }


                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                checkOUT_PickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                long now = System.currentTimeMillis() - 1000;
                checkOUT_PickerDialog.getDatePicker().setMinDate(now);
                checkOUT_PickerDialog.getDatePicker().setMaxDate(now + (1000 * 60 * 60 * 24 * 14));


                txt_check_out.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkOUT_PickerDialog.show();
                    }
                });
            }
        });


    }

    private void getRoomDetail(final String campID, final String in_dateStr, final String out_dateStr, final String str_room_type_id) {

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
                                        new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                                                .setTitleText("Good job!")
                                                .setContentText(" Room is Avilable")
                                                .show();

                                    } else {
                                        String msg = jsData.getString("msg");
                                        txt_check_out.setText("Check In");
                                        new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                                                .setTitleText("Sorry !")
                                                .setContentText(" Room NOT Avilable")
                                                .show();


                                    }
                                } else {
                                    txt_check_out.setText("Check In");
                                    Toast.makeText(mContext, "Invalid Response !!!", Toast.LENGTH_LONG).show();

                                }
                            } catch (Exception ex) {
                                txt_check_out.setText("Check In");
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

                params.put("action", UtilsUrl.Action_getRoomDetail);
                params.put("camp_id", campID);
                params.put("room_type_id", str_room_type_id);
                params.put("from_date", in_dateStr);
                params.put("to_date", out_dateStr);


                Log.e("Param Response ", "" + params);
                return params;
            }
        };

        AppController1.getInstance().addToRequestQueue(stringRequest);


    }

    private void clicEvent() {
        picker_adult.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {

                SetPrice(value, singleroomPrize, doubleRoomPrice);

            }
        });

    }

    private void SetPrice(int value, int singleroomPrize, int doubleRoomPrice) {
        if ((value % 2) == 0) {
            if (value == 2) {
                Log.e("i m hereee", "1111");
                txt_price.setText("" + doubleRoomPrice);

            } else {
                int divisor = value / 2;
                Log.e("i m hereee", "222");
                int price = divisor * doubleRoomPrice;
                txt_price.setText("" + price);

            }
        } else {
            if (value == 1) {
                Log.e("i m hereee", "1111");
                txt_price.setText("" + singleroomPrize);

            } else {
                int divisor = (value - 1) / 2;
                Log.e("i m hereee", "222");
                int price = (divisor * doubleRoomPrice) + singleroomPrize;
                txt_price.setText("" + price);
            }
        }
    }

    private void createIDS() {
        prog = new SpotsDialog(mContext, R.style.Custom);
        txt_price = (TextView) findViewById(R.id.txt_amount);
        picker_adult = (NumberPicker) findViewById(R.id.picker_adult);
        picker_child = (NumberPicker) findViewById(R.id.picker_child);
        spinner_Room_Type = (Spinner) findViewById(R.id.spinner_Room_Type);

        txt_Check_in = (TextView) findViewById(R.id.txt_booking_date);
        txt_check_out = (TextView) findViewById(R.id.txt_check_out);
        edt_message = (EditText) findViewById(R.id.edt_message);
        edt_mobile = (EditText) findViewById(R.id.edt_mobile);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_name = (EditText) findViewById(R.id.edt_name);
        btn_Apply = (Button) findViewById(R.id.btn_Apply);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        edt_name.setText(SharedPref.getFirstName());
        edt_email.setText(SharedPref.getEmail());
        edt_mobile.setText(SharedPref.getMobile1());

    }
}