package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.cycling;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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
import java.util.concurrent.TimeUnit;

import dmax.dialog.SpotsDialog;
import me.relex.circleindicator.CircleIndicator;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.activities.CartList;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.activities.HomePage;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.adapter.ImageSliderAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.adapter.SpinnerCustomAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.app.AppController;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.apputils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.custom_dialog.SweetAlertDialog;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.database.DbFunction;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.database.DbUtils;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.database.DbOperation;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model.StateData;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Commons;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Const;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Itags;

public class CyclingActivity extends AppCompatActivity implements View.OnClickListener,
        RatingBar.OnRatingBarChangeListener, AdapterView.OnItemSelectedListener {

    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ViewPager viewPagerCycling;
    private CircleIndicator indicatorCycling;
    Toolbar toolbarCycling;
    ImageView iv_back_cycling, iv_share_cycling;
    TextView tvPlanNameCycle, tvPriceCycling, descriptionCycling, viewMoreCycling,
            txt_booking_date, txt_amount, txt_check_out, tvTotalCyclefare, tvDaysCount;
    LinearLayout llCycleCart, llCyclenDays;
    Button btnBuyCycling, btnCartCycling, btn_submit_review, btnSubmit, btnCancel;
    EditText editTextUserName, edt_comment, edt_name, edt_email, edt_mobile, edt_message;
    RatingBar ratingbar;
    String userRating;
    Spinner spinnerStateCycling, spinnerNoOfCycle,spinnerCityCycling;
    SpotsDialog prog;
    List<StateData> stateList,cityList;
    String selectedStateId, selectedStateName, selectedNoOfCycles,selectedCityId,selectedCityName;
    SpinnerCustomAdapter spinnerAdapter;
    AlertDialog alertDialog;
    DatePickerDialog fromDateDialog, toDateDialog;
    SimpleDateFormat dateFormatter;
    int price;
    private List<String> noOfCycle;
    String service_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycling);
        noOfCycle = new ArrayList<>();
        stateList = new ArrayList<>();
        cityList = new ArrayList<>();
        service_id = Objects.requireNonNull(getIntent().getExtras()).getString("service_id");
        Log.e("Service Id => ", service_id);
        getUiObject();

        getCyclingDetails();
        setPager();
        if (descriptionCycling.getLineCount() < 6) {
            viewMoreCycling.setVisibility(View.GONE);
        } else {
            viewMoreCycling.setVisibility(View.VISIBLE);
        }

    }

    private void setPager() {
        ArrayList<Integer> productArray = new ArrayList<>();
        productArray.add(R.drawable.new_camp_banner);
        productArray.add(R.drawable.new_camp_banner);
        productArray.add(R.drawable.new_camp_banner);
        viewPagerCycling.setAdapter(new ImageSliderAdapter(productArray, this));
        indicatorCycling.setViewPager(viewPagerCycling);

        tvPlanNameCycle.setText("Ramesh Cycle");
        descriptionCycling.setText(getText(R.string.pacakage_description));
    }

    private void getCyclingDetails() {

        if (new CheckConnectivity().isConnected(this)) {
            prog.setTitle("Wait...");
            prog.show();
            StringRequest stateRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        prog.dismiss();
                        Log.e("State Response => ", response);
                        if (response != null) {
                            JSONObject jsData = new JSONObject(response);
                            String status = jsData.getString("status");
                            if (status.equalsIgnoreCase("1")) {
                                JSONArray data = jsData.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    String description = data.getJSONObject(i).getString("description");
                                    String packName = data.getJSONObject(i).getString("package_name");
                                    String state_name = data.getJSONObject(i).getString("state_name");

                                    tvPlanNameCycle.setText(packName);
                                    descriptionCycling.setText(description);
                                }
                                getState();
                            } else {
                                String msg = jsData.getString("msg");
                                Toast.makeText(CyclingActivity.this, msg, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(CyclingActivity.this, "Invalid Response !!!", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    prog.dismiss();
                    Log.e("On Error => ", error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("action", "get_banner");
                    params.put("service_id", service_id);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put(Itags.Header, UtilsUrl.APP_TOKEN);
                    return headers;
                }
            };
            AppController.getInstance().addToRequestQueue(stateRequest);
        } else {
            Toast.makeText(this, "No Network Coverage", Toast.LENGTH_SHORT).show();
        }
    }

    private void getState() {
        if (new CheckConnectivity().isConnected(this)) {
            prog.setTitle("Wait...");
            prog.show();
            StringRequest stateRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        prog.dismiss();
                        Log.e("State Response => ", response);
                        if (response != null) {
                            JSONObject jsData = new JSONObject(response);
                            String status = jsData.getString("status");
                            if (status.equalsIgnoreCase("1")) {
                                JSONArray data = jsData.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    String state_id = data.getJSONObject(i).getString("state_id");
                                    String state_name = data.getJSONObject(i).getString("state_name");
                                    stateList.add(new StateData(state_id, state_name));
                                }
                                setSpinner(stateList);
                            } else {
                                String msg = jsData.getString("msg");
                                Toast.makeText(CyclingActivity.this, msg, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(CyclingActivity.this, "Invalid Response !!!", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    prog.dismiss();
                    Log.e("On Error => ", error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("action", "get_state");
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put(Itags.Header, UtilsUrl.APP_TOKEN);
                    return headers;
                }
            };
            AppController.getInstance().addToRequestQueue(stateRequest);
        } else {
            Toast.makeText(this, "No Network Coverage", Toast.LENGTH_SHORT).show();
        }
    }

    private void getCycCity(final String state_id){
        if (new CheckConnectivity().isConnected(this)) {
            prog.setTitle("Wait...");
            prog.show();
            StringRequest cityRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        prog.dismiss();
                        Log.e("State Response => ", response);
                        if (response != null) {
                            JSONObject jsData = new JSONObject(response);
                            String status = jsData.getString("status");
                            if (status.equalsIgnoreCase("1")) {
                                cityList.clear();
                                JSONArray data = jsData.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    String city_id = data.getJSONObject(i).getString("city_id");
                                    String city_name = data.getJSONObject(i).getString("city_name");
                                    cityList.add(new StateData(city_id, city_name));
                                }
                                setCitySpinner(cityList);
                            } else {
                                String msg = jsData.getString("msg");
                                Toast.makeText(CyclingActivity.this, msg, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(CyclingActivity.this, "Invalid Response !!!", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    prog.dismiss();
                    Log.e("On Error => ", error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("action", "get_city");
                    params.put("state_id",state_id);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put(Itags.Header, UtilsUrl.APP_TOKEN);
                    return headers;
                }
            };
            AppController.getInstance().addToRequestQueue(cityRequest);
        } else {
            Toast.makeText(this, "No Network Coverage", Toast.LENGTH_SHORT).show();
        }
    }

    private void setCycleCountSpinner() {
        for (int i = 1; i <= 10; i++)
            noOfCycle.add("" + i);
        ArrayAdapter<String> cycleCountAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, noOfCycle);
        spinnerNoOfCycle.setAdapter(cycleCountAdapter);
    }

    private void setSpinner(List<StateData> stateList) {
        spinnerAdapter = new SpinnerCustomAdapter(this, R.layout.spinner_item, R.id.spinTitle, stateList);
        spinnerStateCycling.setAdapter(spinnerAdapter);

        if (!SharedPref.getSelectedState().equalsIgnoreCase("")) {

            Log.e("Inside", "Set Def" + stateList.size());
            for (StateData stateData : stateList) {
                if (SharedPref.getSelectedState().equalsIgnoreCase(stateData.getStateName())) {
                    Log.e("Inside", "Set Def" + SharedPref.getSelectedState());
                    spinnerStateCycling.setSelection(((SpinnerCustomAdapter) spinnerStateCycling.getAdapter()).getPosition(stateData));
                } else {
                    Log.e("Inside", "Set Def else");
                }
            }
        }
    }


    private void setCitySpinner(List<StateData> cityList) {

        spinnerAdapter = new SpinnerCustomAdapter(this, R.layout.spinner_item, R.id.spinTitle, cityList);
        spinnerCityCycling.setAdapter(spinnerAdapter);

        if (!SharedPref.getSelectedCity().equalsIgnoreCase("")) {

            Log.e("Inside", "Set Def" + cityList.size());
            for (StateData stateData : cityList) {
                if (SharedPref.getSelectedCity().equalsIgnoreCase(stateData.getStateName())) {
                    Log.e("Inside", "Set Def" + SharedPref.getSelectedCity());
                    spinnerCityCycling.setSelection(((SpinnerCustomAdapter) spinnerCityCycling.getAdapter()).getPosition(stateData));
                } else {
                    Log.e("Inside", "Set Def else");
                }
            }
        }
    }


    private void setDateListener() {

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Calendar newCalendar = Calendar.getInstance();

        fromDateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                Log.e("date ", "" + year + " " + (monthOfYear + 1) + " " + dayOfMonth);
                txt_booking_date.setText(dateFormatter.format(newDate.getTime()));
                String lastDate = "";
                try {
                    DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String inputDateStr = txt_booking_date.getText().toString();

                    Log.e("Input Date => ", inputDateStr);
                    Date date_check_in = inputFormat.parse(inputDateStr);
                    lastDate = outputFormat.format(date_check_in);
                    Log.e("Last Date => ", lastDate);
                    txt_booking_date.setText(inputDateStr);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        fromDateDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        toDateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                Log.e("date ", "" + year + " " + (monthOfYear + 1) + " " + dayOfMonth);
                txt_check_out.setText(dateFormatter.format(newDate.getTime()));
                String lastDate = "";
                try {
                    DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                    String inputDateStr = txt_booking_date.getText().toString();
                    Date date_check_in = inputFormat.parse(inputDateStr);
                    // check out date
                    String outDateStr = txt_check_out.getText().toString();
                    Date date_check_out = inputFormat.parse(outDateStr);

                    String inDatefStr = outputFormat.format(date_check_in);
                    String outDatefStr = outputFormat.format(date_check_out);

                    if (!txt_booking_date.getText().toString().equalsIgnoreCase("")) {
                        if (!txt_check_out.getText().toString().equalsIgnoreCase("")) {
                            if (Objects.requireNonNull(date_check_in).before(date_check_out)) {
                                getNoOfCycles(txt_booking_date.getText().toString(), txt_check_out.getText().toString());
                                llCyclenDays.setVisibility(View.VISIBLE);
                                getNoOfDays(txt_booking_date.getText().toString(), txt_check_out.getText().toString());
                            } else {
                                Toast.makeText(CyclingActivity.this, "Check-in Date Should Before Check-Out", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        toDateDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);


    }

    private void getNoOfCycles(final String fromDate, final String toDate) {

        if (new CheckConnectivity().isConnected(this)) {
            prog.setMessage("Please Wait...");
            prog.show();
            StringRequest getCycleRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        prog.dismiss();
                        Log.e("Response : ", response);
                        if (response != null) {
                            JSONObject jsData = new JSONObject(response);
                            String status = jsData.getString("status");
                            if (status.equalsIgnoreCase("1")) {
                                /**
                                 * cycle selection code
                                 */
                            } else {
                                Toast.makeText(CyclingActivity.this, jsData.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CyclingActivity.this, "Invalid Response !!!", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    prog.dismiss();
                    Log.e("Error :", error.toString());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put(Itags.Header, Const.APP_TOKEN);
                    return headers;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put(Const.KEY_ACTION, Const.KEY_GET_CYCLE);
                    params.put("state_id", selectedStateId);
                    params.put("from", fromDate);
                    params.put("to", toDate);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(getCycleRequest);
        } else {
            Toast.makeText(this, "No Network Coverage", Toast.LENGTH_SHORT).show();
        }

    }

    private void getUiObject() {

        appBarLayout = findViewById(R.id.appBarCycling);
        collapsingToolbarLayout = findViewById(R.id.ctlCycling);
        viewPagerCycling = findViewById(R.id.viewPagerCycling);
        indicatorCycling = findViewById(R.id.indicatorCycling);
        toolbarCycling = findViewById(R.id.toolbarCycling);
        iv_back_cycling = findViewById(R.id.iv_back_cycling);
        iv_share_cycling = findViewById(R.id.iv_share_cycling);
        tvPlanNameCycle = findViewById(R.id.tvPlanNameCycle);
        tvPriceCycling = findViewById(R.id.tvPriceCycling);
        descriptionCycling = findViewById(R.id.descriptionCycling);
        viewMoreCycling = findViewById(R.id.viewMoreCycling);
        llCycleCart = findViewById(R.id.llCycleCart);
        btnBuyCycling = findViewById(R.id.btnBuyCycling);
        btnCartCycling = findViewById(R.id.btnCartCycling);
        btn_submit_review = findViewById(R.id.btn_submit_review);
        editTextUserName = findViewById(R.id.editTextUserName);
        edt_comment = findViewById(R.id.edt_comment);
        ratingbar = findViewById(R.id.ratingbar);
        spinnerStateCycling = findViewById(R.id.spinnerStateCycling);
        spinnerCityCycling = findViewById(R.id.spinnerCityCycling);
        prog = new SpotsDialog(this, R.style.Custom);

        setListeners();

        setSupportActionBar(toolbarCycling);
        collapsingToolbarLayout.setTitle("Cycling");
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.collapsingToolbarLayoutTitleColor);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsingToolbarLayoutTitleColor);

    }

    private void setListeners() {
        iv_back_cycling.setOnClickListener(this);
        iv_share_cycling.setOnClickListener(this);
        viewMoreCycling.setOnClickListener(this);
        btnBuyCycling.setOnClickListener(this);
        btnCartCycling.setOnClickListener(this);
        btn_submit_review.setOnClickListener(this);
        ratingbar.setOnRatingBarChangeListener(this);
        spinnerStateCycling.setOnItemSelectedListener(this);
    }

    private void openConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.cycling_dialog_form, null, false);
        getUiFromDialog(dialogView);
        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void getUiFromDialog(View view) {
        txt_booking_date = view.findViewById(R.id.txt_booking_date);
        txt_amount = view.findViewById(R.id.txt_amount);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnCancel = view.findViewById(R.id.btnCancel);
        edt_name = view.findViewById(R.id.edt_name);
        edt_email = view.findViewById(R.id.edt_email);
        edt_mobile = view.findViewById(R.id.edt_mobile);
        edt_message = view.findViewById(R.id.edt_message);
        spinnerNoOfCycle = view.findViewById(R.id.spinnerNoOfCycle);
        //spinnerNoOfDays = view.findViewById(R.id.spinnerNoOfDays);
        tvTotalCyclefare = view.findViewById(R.id.tvTotalCyclefare);
        txt_check_out = view.findViewById(R.id.txt_check_out);
        llCyclenDays = view.findViewById(R.id.llCyclenDays);
        tvDaysCount = view.findViewById(R.id.tvDaysCount);

        spinnerNoOfCycle.setOnItemSelectedListener(this);
        //   spinnerNoOfDays.setOnItemSelectedListener(this);
        txt_booking_date.setOnClickListener(this);
        txt_check_out.setOnClickListener(this);

        String name = SharedPref.getFirstName() + " " + SharedPref.getLastName();
        edt_name.setText(name);
        edt_email.setText(SharedPref.getEmail());
        edt_mobile.setText(SharedPref.getMobile1());

        List<String> cycleList = new ArrayList<>();
        for (int i = 1; i <= 10; i++)
            cycleList.add("" + i);

        setCycleCountSpinner();

    }

    private String getNoOfDays(String startDate, String endDate) {
        long convert = 0;
        if (!startDate.equalsIgnoreCase("") && !endDate.equalsIgnoreCase("")) {
            SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Log.e("startDate => ", startDate);
            Log.e("endDate => ", endDate);
            try {
                Date date1 = myFormat.parse(startDate);
                Date date2 = myFormat.parse(endDate);
                long diff = date2.getTime() - date1.getTime();
                convert = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
                tvDaysCount.setText("" + convert);
                String price = String.valueOf(convert * Integer.parseInt(selectedNoOfCycles.trim()) * Integer.parseInt(txt_amount.getText().toString().trim()));
                tvTotalCyclefare.setText(price);
                Log.e("Days: ", "" + convert);
            } catch (ParseException e) {
                Log.e("Message -> ", e.getMessage());
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Select Start Date and End Date", Toast.LENGTH_SHORT).show();
        }

        return String.valueOf(convert);
    }

    private void validateDialogInput(final String action) {
        openConfirmationDialog();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbFunction.ExportDatabasee(CyclingActivity.this, DbUtils.DB_NAME);
                alertDialog.dismiss();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(action);
            }
        });
    }

    private void validate(String action) {

        String name = edt_name.getText().toString();
        String no_of_days = getNoOfDays(txt_booking_date.getText().toString(), txt_check_out.getText().toString());
        String email = edt_email.getText().toString();
        String mobile = edt_mobile.getText().toString();
        String msg = edt_message.getText().toString();
        String lastAmt = tvTotalCyclefare.getText().toString();

        if (name.equalsIgnoreCase("")) {
            edt_name.setError("Name is Required");
        } else if (txt_booking_date.getText().toString().equalsIgnoreCase("From")) {
            Toast.makeText(this, "Please select from Date", Toast.LENGTH_SHORT).show();
        } else if (txt_check_out.getText().toString().equalsIgnoreCase("To")) {
            Toast.makeText(this, "Please select to Date", Toast.LENGTH_SHORT).show();
        } else if (email.equalsIgnoreCase("")) {
            edt_email.setError("Please Fill Email Id");
        } else if (!Commons.isValidEmail(email)) {
            edt_email.setError("Invalid Email");
        } else if (mobile.equalsIgnoreCase("")) {
            edt_mobile.setError("Please Fill Mobile No");
        } else if (!Commons.isValidMobile(mobile)) {
            edt_mobile.setError("Invalid Mobile");
        } else if (msg.equalsIgnoreCase("")) {
            edt_message.setError("Please provide some message");
        } else {
            DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Date curr_date = Calendar.getInstance().getTime();
            String bookDate = inputFormat.format(curr_date);

            String checkInDateStr = txt_booking_date.getText().toString().trim();
            String checkOutDateStr = txt_check_out.getText().toString().trim();
            Date date_check_in = null, date_check_out = null;
            boolean isSuccess = false;
            try {
                date_check_in = inputFormat.parse(checkInDateStr);
                date_check_out = inputFormat.parse(checkOutDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            int entryExist = DbOperation.getRaftCount(this, service_id);

            if (entryExist > 0) {
                DbOperation.deleteCartItem(this, service_id);
                Log.e("cycle count", entryExist + " id deleted");
            }

            if (Objects.requireNonNull(date_check_in).before(date_check_out)) {
                isSuccess = DbOperation.insertCampaningCart(CyclingActivity.this,
                        service_id, tvPlanNameCycle.getText().toString(), "cycling", txt_booking_date.getText().toString(),
                        txt_check_out.getText().toString(), no_of_days, "", "", "",
                        bookDate, selectedNoOfCycles, txt_amount.getText().toString(), lastAmt, "",
                        Itags.CYCLING, "", name, mobile, msg, email);
            } else {
                Toast.makeText(this, "Check-in Date Should Before Check-Out", Toast.LENGTH_SHORT).show();
            }


            if (isSuccess) {
                Toast.makeText(CyclingActivity.this, "Saved in Database", Toast.LENGTH_SHORT).show();
                //DB_Function.ExportDatabasee(getActivity(), DbUtils.DB_NAME);
                //int cartCount = DbOperation.getRaftCount(CyclingActivity.this, service_id);
                int cartCount = Objects.requireNonNull(DbOperation.getCartList(CyclingActivity.this)).size();

                Log.e("Cart Count => ", "" + cartCount);
                SharedPref.setCartCount(String.valueOf(cartCount));
                SharedPref.saveMobile1(mobile);
                if (action.equalsIgnoreCase("book_now")) {
                    alertDialog.dismiss();
                    Intent i = new Intent(CyclingActivity.this, CartList.class);
                    startActivity(i);
                    finish();
                } else if (action.equalsIgnoreCase("cart")) {
                    alertDialog.dismiss();
                    Intent homeIntent = new Intent(CyclingActivity.this, HomePage.class);
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(homeIntent);
                    finish();
                }
            } else {
                Log.e("Data no Saved", "Data not Saved");
            }

        }
    }

    private void reviewCycling() {

        String userName = editTextUserName.getText().toString();
        String comment = edt_comment.getText().toString();
        if (userName.equalsIgnoreCase("")) {
            editTextUserName.setError("Username is Required");
            editTextUserName.clearFocus();
            editTextUserName.setFocusableInTouchMode(true);
            editTextUserName.requestFocus();
        } else if (comment.equalsIgnoreCase("")) {
            edt_comment.setError("Comment is Required");
            edt_comment.clearFocus();
            edt_comment.setFocusableInTouchMode(true);
            edt_comment.requestFocus();
        } else if (userRating.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please Provide Rating", Toast.LENGTH_SHORT).show();
        } else {
            saveReview("", userName, comment, userRating, service_id);
        }

    }

    private void saveReview(final String pacakageID, final String name, final String comment,
                            final String rating, final String serviceID) {

        if (new CheckConnectivity().isConnected(this)) {
            prog.setMessage("Please Wait....");
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
                                editTextUserName.setText("");
                                edt_comment.setText("");
                                ratingbar.setNumStars(0);
                                new SweetAlertDialog(CyclingActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Thanks!")
                                        .setContentText(" Your Review is Saved")
                                        .show();
                            } else {
                                String msg = jsData.getString("msg");
                                Toast.makeText(CyclingActivity.this, msg, Toast.LENGTH_LONG).show();

                                new SweetAlertDialog(CyclingActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Something went wrong!")
                                        .show();
                            }
                        } else {
                            Toast.makeText(CyclingActivity.this, "Invalid Response !!!", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    prog.dismiss();
                    Toast.makeText(CyclingActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    Log.e("Error :", error.toString());
                }
            }) {

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> header = new HashMap<>();
                    header.put(Itags.Header, Const.APP_TOKEN);
                    Log.e("Param header ", "" + header);
                    return header;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put(Const.KEY_ACTION, UtilsUrl.Action_SubmitReview);
                    params.put("user_id", SharedPref.getUserID());
                    params.put("service_id", serviceID);
                    params.put("package_id", pacakageID);
                    params.put("rating", "" + rating);
                    params.put("name", name);
                    params.put("comment", comment);

                    Log.e("Param Response ", "" + params);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
        } else {
            Toast.makeText(CyclingActivity.this, "Check Your connetion", Toast.LENGTH_LONG).show();
        }

    }

    private void shareMessage() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Cycling");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hello try Cycling services at http://indianadventurepackages.com");
        startActivity(Intent.createChooser(shareIntent, "Share Via"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back_cycling:
                finish();
                break;
            case R.id.iv_share_cycling:
                shareMessage();
                break;
            case R.id.viewMoreCycling:
                if (viewMoreCycling.getText().toString().equalsIgnoreCase("View More")) {
                    descriptionCycling.setMaxLines(Integer.MAX_VALUE);
                    viewMoreCycling.setText("View Less");
                } else {
                    descriptionCycling.setMaxLines(6);
                    viewMoreCycling.setText("View More");
                }
                break;
            case R.id.btnBuyCycling:
                validateDialogInput("book_now");
                break;
            case R.id.btnCartCycling:
                validateDialogInput("cart");
                break;
            case R.id.btn_submit_review:
                reviewCycling();
                break;
            case R.id.txt_booking_date:
                setDateListener();
                fromDateDialog.show();
                break;
            case R.id.txt_check_out:
                setDateListener();
                toDateDialog.show();
                break;


        }
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        userRating = String.valueOf(ratingBar.getRating());
        Log.e("Rating => ", userRating);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinnerStateCycling:
                StateData stateData = (StateData) parent.getSelectedItem();
                String stateId = stateData.getStateId();
                String stateName = stateData.getStateName();
                Log.e("Selected State => ", "state_id => " + stateId + " statename=> " + stateName);
                if (!stateId.equalsIgnoreCase("-1")) {
                    selectedStateId = stateId;
                    selectedStateName = stateName;
                }
                getCycCity(selectedStateId);
                SharedPref.setSelectedState(selectedStateName);
                break;

            case R.id.spinnerNoOfCycle:
                selectedNoOfCycles = (String) parent.getSelectedItem();
                Log.e("selected no of seat => ", selectedNoOfCycles);
                int noOfdays = Integer.parseInt(getNoOfDays(txt_booking_date.getText().toString(), txt_check_out.getText().toString()));
                Log.e("total fare => ", "" + noOfdays);
                int baseFare = Integer.parseInt(txt_amount.getText().toString());
                String fare = String.valueOf(noOfdays * Integer.parseInt(selectedNoOfCycles) * baseFare);
                Log.e("total fare => ", fare);
                tvTotalCyclefare.setText(fare);
                break;

            case R.id.spinnerCityCycling:
                StateData cityData = (StateData) parent.getSelectedItem();
                String cityId = cityData.getStateId();
                String cityName = cityData.getStateName();
                Log.e("Selected State => ", "state_id => " + cityId + " statename=> " + cityName);
                if (!cityId.equalsIgnoreCase("-1")) {
                    selectedCityId = cityId;
                    selectedCityName = cityName;
                }
                SharedPref.setSelectedCity(selectedCityName);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
