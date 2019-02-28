package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.camp;

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
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.app.AppController;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.number_picker.Enums.ActionEnum;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.number_picker.Interface.ValueChangedListener;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.number_picker.NumberPicker;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.apputils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.custom_dialog.SweetAlertDialog;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model.RoomTypeModel;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Const;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Itags;

/**
 * Created by Prince on 29-01-2018.
 */

public class CampBookinDialog extends Dialog {
    private Context mContext;
    TextView txt_Check_in, txt_check_out;
    private DatePickerDialog checkInPickerDialog, checkOutPickerDialog;
    private SimpleDateFormat dateFormatter;

    NumberPicker picker_adult, picker_child;
    TextView txt_price;
    Spinner spinner_Room_Type;
    private List<String> roomTypeShort = new ArrayList<>();
    ArrayList<RoomTypeModel> roomTypefull = new ArrayList<>();

    EditText edt_message, edt_mobile, edt_email, edt_name;
    public Button btn_Apply, btn_cancel;
    int singleroomPrize = 0, doubleRoomPrice = 0;
    private String campID;
    private SpotsDialog prog;

    public CampBookinDialog(Activity con, String jsResponse, String camp_id) {
        super(con);
        this.mContext = con;
        campID = camp_id;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.cst_dialog_book_camp);
        createIDS();
        getJsnonDetail(jsResponse);
        clickEvent();
        dateClickListener();
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
                setPrice(picker_adult.getValue(), singleroomPrize, doubleRoomPrice);
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
                roomTypeShort.add(jsRoom_Type.getJSONObject(i).getString("room_type_title"));
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

                ArrayAdapter<String> adp1 = new ArrayAdapter<>(mContext,R.layout.spinner_item, roomTypeShort);
               // adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_Room_Type.setAdapter(adp1);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void dateClickListener() {
        txt_Check_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

                Calendar newCalendar = Calendar.getInstance();

                checkInPickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        txt_Check_in.setText(dateFormatter.format(newDate.getTime()));
                        txt_check_out.setText(mContext.getText(R.string.check_out));
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                checkInPickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                long now = System.currentTimeMillis() - 1000;
                checkInPickerDialog.getDatePicker().setMinDate(now);
                checkInPickerDialog.getDatePicker().setMaxDate(now + (1000 * 60 * 60 * 24 * 14));
                txt_Check_in.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkInPickerDialog.show();
                    }
                });
            }
        });
        txt_check_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

                Calendar newCalendar = Calendar.getInstance();

                checkOutPickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        txt_check_out.setText(dateFormatter.format(newDate.getTime()));

                        try {
                            DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                            DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                            String inputDateStr = txt_Check_in.getText().toString();
                            Date date_check_in = inputFormat.parse(inputDateStr);
// check out date
                            String outDateStr = txt_check_out.getText().toString();
                            Date date_check_out = inputFormat.parse(outDateStr);

                            String inDatefStr = outputFormat.format(date_check_in);
                            String outDatefStr = outputFormat.format(date_check_out);

                            if ((date_check_in.compareTo(date_check_out)) < 0){
                                getRoomDetail(campID, inDatefStr, outDatefStr, roomTypefull.get(spinner_Room_Type.getSelectedItemPosition()).getStr_room_type_id());
                            }else{
                                Toast.makeText(mContext, "Check-in Date Should be less than Check-Out Date", Toast.LENGTH_SHORT).show();
                            }

                           /* if ((date_check_in.compareTo(date_check_out)) < 0 || (date_check_in.compareTo(date_check_out)==0)){
                                getRoomDetail(campID, inDatefStr, outDatefStr, roomTypefull.get(spinner_Room_Type.getSelectedItemPosition()).getStr_room_type_id());
                            }else{
                                Toast.makeText(mContext, "Check-in Date Should be equal or less than Check-Out Date", Toast.LENGTH_SHORT).show();
                            }*/

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                checkOutPickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                long now = System.currentTimeMillis() - 1000;
                checkOutPickerDialog.getDatePicker().setMinDate(now);
                checkOutPickerDialog.getDatePicker().setMaxDate(now + (1000 * 60 * 60 * 24 * 14));

                txt_check_out.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkOutPickerDialog.show();
                    }
                });
            }
        });

    }

    private void getRoomDetail(final String campID, final String in_dateStr, final String out_dateStr, final String str_room_type_id) {

        if (new CheckConnectivity().isConnected(mContext)) {
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
                                new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Yeah!")
                                        .setContentText("Camp Available")
                                        .show();
                            } else {
                                String msg = jsData.getString("msg");
                                txt_check_out.setText(mContext.getText(R.string.check_in));
                                new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Sorry !")
                                        .setContentText("Camp Not Available")
                                        .show();
                            }
                        } else {
                            txt_check_out.setText(mContext.getText(R.string.check_in));
                            Toast.makeText(mContext, "Invalid Response !!!", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        txt_check_out.setText(mContext.getText(R.string.check_in));
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
                    // params.put("Accept-Language", "fr");
                    return header;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();

                    params.put(Const.KEY_ACTION, UtilsUrl.Action_getRoomDetail);
                    params.put(Const.KEY_CAMP_ID, campID);
                    params.put("room_type_id", str_room_type_id);
                    params.put("from_date", in_dateStr);
                    params.put("to_date", out_dateStr);

                    Log.e("Param Response ", "" + params);
                    return params;
                }
            };

            AppController.getInstance().addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(mContext, "Check Your Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void clickEvent() {
        picker_adult.setValueChangedListener(new ValueChangedListener() {
            @Override
            public void valueChanged(int value, ActionEnum action) {
                setPrice(value, singleroomPrize, doubleRoomPrice);
            }
        });
    }

    private void setPrice(int value, int singleroomPrize, int doubleRoomPrice) {
        if ((value % 2) == 0) {
            if (value == 2) {
                Log.e("i m hereee", "1111");
                txt_price.setText(String.valueOf(doubleRoomPrice));

            } else {
                int divisor = value / 2;
                Log.e("i m hereee", "222");
                int price = divisor * doubleRoomPrice;
                txt_price.setText(String.valueOf(price));

            }
        } else {
            if (value == 1) {
                Log.e("i m hereee", "1111");
                txt_price.setText(String.valueOf(singleroomPrize));

            } else {
                int divisor = (value - 1) / 2;
                Log.e("i m hereee", "222");
                int price = (divisor * doubleRoomPrice) + singleroomPrize;
                txt_price.setText(String.valueOf(price));
            }
        }
    }

    private void createIDS() {
        prog = new SpotsDialog(mContext, R.style.Custom);
        txt_price = findViewById(R.id.txt_amount);
        picker_adult = findViewById(R.id.picker_adult);
        picker_child = findViewById(R.id.picker_child);
        spinner_Room_Type = findViewById(R.id.spinner_Room_Type);

        txt_Check_in = findViewById(R.id.txt_booking_date);
        txt_check_out = findViewById(R.id.txt_check_out);
        edt_message = findViewById(R.id.edt_message);
        edt_mobile = findViewById(R.id.edt_mobile);
        edt_email = findViewById(R.id.edt_email);
        edt_name = findViewById(R.id.edt_name);
        btn_Apply = findViewById(R.id.btn_Apply);
        btn_cancel = findViewById(R.id.btn_cancel);

        edt_name.setText(SharedPref.getFirstName());
        edt_name.setEnabled(false);
        edt_email.setText(SharedPref.getEmail());
        edt_email.setEnabled(false);
        edt_mobile.setText(SharedPref.getMobile1());
        edt_mobile.setEnabled(false);
    }
}