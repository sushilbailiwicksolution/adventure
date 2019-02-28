package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.bungy_jump;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
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
import java.util.Objects;

import dmax.dialog.SpotsDialog;
import me.relex.circleindicator.CircleIndicator;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.activities.AboutRafting;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.activities.CartList;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.activities.HomePage;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.adapter.ImageSliderAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.adapter.SpinnerCustomAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.app.AppController;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.apputils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.database.DbOperation;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model.StateData;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Commons;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Const;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Itags;

public class BungeeJumpActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    ViewPager viewPagerBungy;
    CircleIndicator indicatorBungyJump;
    Spinner stateSpinner, spinnerStartPoint, spinnerNoOfPerson;
    TextView txtBookingDate, txt_amount, tvDescriptionText, tvDescription,tvFinalAmount,txtTitle;
    Button btnBuyNow, btnAddToCart;
    SpinnerCustomAdapter spinnerAdapter;
    List<StateData> stateList;
    String selectedStateId, selectedStateName,selectedPoint,selectedPersonCount;
    SpotsDialog prog;
    EditText edt_name, edt_email, edt_mobile;
    String serviceId, enqDate;
    Toolbar toolbarBungee;
    SimpleDateFormat dateFormatter;
    DatePickerDialog bookDateDialog;
    LinearLayout llPoint;
    ImageView ivInfoBungee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bungee_jump);
        serviceId = Objects.requireNonNull(getIntent().getExtras()).getString("service_id");
        Log.e("service id => ", serviceId);
        stateList = new ArrayList<>();
        getUiObject();
        setToolbar();
        setData();
        setPager();
        getState();
        setDateListener();

    }

    private void setData(){
        String name = SharedPref.getFirstName()+" "+SharedPref.getLastName();
        edt_name.setText(name);
        edt_name.setEnabled(false);
        edt_email.setText(SharedPref.getEmail());
        edt_email.setEnabled(false);
        edt_mobile.setText(SharedPref.getMobile1());
        edt_mobile.setEnabled(false);
    }

    private void setToolbar() {
        setSupportActionBar(toolbarBungee);
        toolbarBungee.setTitle("Bungee Jumping");
        toolbarBungee.setNavigationIcon(getResources().getDrawable(R.drawable.clear_black));
        toolbarBungee.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setDateListener(){
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        bookDateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                Log.e("date ", "" + year + " " + (month + 1) + " " + dayOfMonth);
                txtBookingDate.setText(dateFormatter.format(newDate.getTime()));
                String lastDate;
                try {
                    DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault());
                    DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
                    String inputDateStr = txtBookingDate.getText().toString();

                    Log.e("Input Date => ",inputDateStr);
                    Date date_check_in = inputFormat.parse(inputDateStr);
                    lastDate = outputFormat.format(date_check_in);
                    Log.e("Last Date => ",lastDate);
                    txtBookingDate.setText(inputDateStr);
                    llPoint.setVisibility(View.VISIBLE);
                    setPointSpinner();
                    setPersonSpinner();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        bookDateDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
    }

    private void getBanner() {
        if (new CheckConnectivity().isConnected(this)) {
            prog.setMessage("Please Wait");
            prog.show();
            StringRequest bannerRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    prog.dismiss();
                    Log.e("Bungee Page Res =>", response);
                    if (response != null) {
                        try {
                            JSONObject jsBanner = new JSONObject(response);
                            if (jsBanner.getString("status").equalsIgnoreCase("1")) {
                                JSONObject dataObj = jsBanner.getJSONObject("data");
                                JSONArray bannerArr = dataObj.getJSONArray("banner");
                                if (dataObj.getString("description") != null) {
                                    tvDescription.setText(dataObj.getString("description"));
                                }
                                for (int i = 0; i < bannerArr.length(); i++) {
                                    //productArray.add(bannerArr.get(i));
                                }
                                // setPager();
                            } else {
                                Toast.makeText(BungeeJumpActivity.this, jsBanner.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(BungeeJumpActivity.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    prog.dismiss();
                    Log.e("Bungee Page Error => ", error.toString());
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
                    params.put(Const.KEY_ACTION, "get_banner");
                    params.put("service_id", serviceId);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(bannerRequest);
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void getBungyDetails() {
        if (new CheckConnectivity().isConnected(this)) {
            prog.setMessage("Please Wait...");
            prog.show();
            StringRequest bungeeRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    prog.dismiss();
                    try {
                        Log.e("Response Bungee => ", response);
                        if (response != null) {
                            JSONObject jsonRespone = new JSONObject(response);
                            String status = jsonRespone.getString("status");
                            if (status.equalsIgnoreCase("1")) {
                                JSONArray data = jsonRespone.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    String point_id = data.getJSONObject(i).getString("point_id");
                                    String point_name = data.getJSONObject(i).getString("point_name");
                                    String noOfPerson = data.getJSONObject(i).getString("no_of_person");
                                    String timing = data.getJSONObject(i).getString("timing");
                                    String pricePerJump = data.getJSONObject(i).getString("price_per_jump");
                                }
                                setPointSpinner();
                            } else {
                                Toast.makeText(BungeeJumpActivity.this, jsonRespone.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(BungeeJumpActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("Exception Bungee => ", e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error Bungee => ", error.toString());
                    prog.dismiss();
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
                    params.put(Const.KEY_ACTION, "get_bungy_details");
                    params.put("state_id", selectedStateId);
                    params.put("booking_date", enqDate);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(bungeeRequest);
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void setPointSpinner() {
        List<String> points = new ArrayList<>();
        points.add("Shivpuri");
        points.add("J & K");
        points.add("My Point 1");
        points.add("My Point 2");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.spinner_item,points);
        spinnerStartPoint.setAdapter(arrayAdapter);
    }

    private void setPager() {
        ArrayList<Integer> productArray = new ArrayList<>();
        productArray.add(R.drawable.new_camp_banner);
        productArray.add(R.drawable.new_camp_banner);
        productArray.add(R.drawable.new_camp_banner);
        ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(productArray,this);
        viewPagerBungy.setAdapter(imageSliderAdapter);
        indicatorBungyJump.setViewPager(viewPagerBungy);
    }

    private void getUiObject() {
        viewPagerBungy = findViewById(R.id.viewPagerBungy);
        indicatorBungyJump = findViewById(R.id.indicatorBungyJump);
        stateSpinner = findViewById(R.id.stateSpinner);
        spinnerStartPoint = findViewById(R.id.spinnerStartPoint);
        spinnerNoOfPerson = findViewById(R.id.spinnerNoOfPerson);
        txtBookingDate = findViewById(R.id.txtBookingDate);
        txt_amount = findViewById(R.id.txt_amount);
        tvDescriptionText = findViewById(R.id.tvDescriptionText);
        tvDescription = findViewById(R.id.tvDescription);
        btnBuyNow = findViewById(R.id.btnBuyNow);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        edt_name = findViewById(R.id.edt_name);
        edt_email = findViewById(R.id.edt_email);
        edt_mobile = findViewById(R.id.edt_mobile);
        toolbarBungee = findViewById(R.id.toolbar_main);
        llPoint = findViewById(R.id.llPoint);
        tvFinalAmount = findViewById(R.id.tvFinalAmount);
        txtTitle = findViewById(R.id.txtTitle);
        ivInfoBungee = findViewById(R.id.ivInfoBungee);

        prog = new SpotsDialog(this, R.style.Custom);
        setListeners();
    }

    private void setListeners() {
//        viewPagerBungy.setOnClickListener(this);
        stateSpinner.setOnItemSelectedListener(this);
        spinnerStartPoint.setOnItemSelectedListener(this);
        spinnerNoOfPerson.setOnItemSelectedListener(this);
        txtBookingDate.setOnClickListener(this);
        btnBuyNow.setOnClickListener(this);
        btnAddToCart.setOnClickListener(this);
        ivInfoBungee.setOnClickListener(this);

    }

    private void setPersonSpinner() {
        List<String> person = new ArrayList<>();
        for (int i = 1; i <= 5; i++)
            person.add(""+i);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, person);
        spinnerNoOfPerson.setAdapter(arrayAdapter);
    }

    private void getState() {

        if (new CheckConnectivity().isConnected(this)) {
            prog.setTitle("Wait...");
            prog.show();
            StringRequest stateRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    prog.dismiss();
                    try {
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
                                Toast.makeText(BungeeJumpActivity.this, msg, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(BungeeJumpActivity.this, "Invalid Response !!!", Toast.LENGTH_LONG).show();
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
                    params.put(Const.KEY_ACTION, Const.KEY_GET_STATE);
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
            Toast.makeText(BungeeJumpActivity.this, "No Network Coverage", Toast.LENGTH_SHORT).show();
        }
    }

    private void setSpinner(List<StateData> stateList) {
        spinnerAdapter = new SpinnerCustomAdapter(this, R.layout.spinner_item, R.id.spinTitle, stateList);
        stateSpinner.setAdapter(spinnerAdapter);

        if (!SharedPref.getSelectedState().equalsIgnoreCase("")) {

            Log.e("Inside", "Set Def" + stateList.size());
            for (StateData stateData : stateList) {
                if (SharedPref.getSelectedState().equalsIgnoreCase(stateData.getStateName())) {
                    Log.e("Inside", "Set Def" + SharedPref.getSelectedState());
                    stateSpinner.setSelection(((SpinnerCustomAdapter) stateSpinner.getAdapter()).getPosition(stateData));
                } else {
                    Log.e("Inside", "Set Def else");
                }
            }
        }
    }

    private void validateInput(String action) {

        String name = edt_name.getText().toString();
        String email = edt_email.getText().toString();
        String mobile = edt_mobile.getText().toString();

        if (name.equalsIgnoreCase("")) {
            edt_name.setError("Name is Required");
        } else if (email.equalsIgnoreCase("")) {
            edt_email.setError("Please Fill Email Id");
        } else if (!Commons.isValidEmail(email)) {
            edt_email.setError("Invalid Email");
        } else if (mobile.equalsIgnoreCase("")) {
            edt_mobile.setError("Please Fill Mobile No");
        } else if (!Commons.isValidMobile(mobile)) {
            edt_mobile.setError("Invalid Mobile");
        } else if (txtBookingDate.getText().toString().trim().equalsIgnoreCase("Booking Date")){
            Toast.makeText(this, "Select Booking Date", Toast.LENGTH_SHORT).show();
        }else {
            DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Date curr_date = Calendar.getInstance().getTime();
            String bookDate = inputFormat.format(curr_date);

            int pricePerSeat = Integer.parseInt(txt_amount.getText().toString());

            String totalAmt = String.valueOf(pricePerSeat*Integer.parseInt(selectedPersonCount));

            Log.e("raft count", "Sports Id : " + serviceId);

            int entryExist = DbOperation.getRaftCount(this, serviceId);

            if (entryExist > 0) {
                DbOperation.deleteCartItem(this, serviceId);
                Log.e("raft count", "Sports Id : Deleted");
            }

            boolean isSuccess = DbOperation.insertRaftingCart(this,
                    serviceId, txtTitle.getText().toString(), "bungee jumping",txtBookingDate.getText().toString(), "", "",
                    selectedPersonCount, "", selectedPoint, bookDate, "",
                    txt_amount.getText().toString(), totalAmt, "", Itags.BUNGY_JUMPING,
                    SharedPref.getTEMPfirstName(), SharedPref.getTempmobile1(), "", SharedPref.getTempemail());

            if (isSuccess) {
                Toast.makeText(this, "Saved in Database", Toast.LENGTH_SHORT).show();

                int cartCount = Objects.requireNonNull(DbOperation.getCartList(BungeeJumpActivity.this)).size();
                Log.e("Cart Count => ", "" + cartCount);
                SharedPref.setCartCount(String.valueOf(cartCount));
                SharedPref.saveMobile1(edt_mobile.getText().toString());

                if (action.equalsIgnoreCase("book_now")) {
                    Intent i = new Intent(this, CartList.class);
                    startActivity(i);
                    finish();
                } else if (action.equalsIgnoreCase("cart")) {
                    Intent homeIntent = new Intent(this, HomePage.class);
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(homeIntent);
                    finish();
                }
            } else {
                Log.e("Data no Saved", "Data not Saved");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivInfoBungee:
                Intent aboutBungyIntent = new Intent(this, AboutRafting.class);
                aboutBungyIntent.putExtra("from", "Bungee");
                startActivity(aboutBungyIntent);
                break;
            case R.id.txtBookingDate:
                bookDateDialog.show();
                break;
            case R.id.btnBuyNow:
                validateInput("book_now");
                break;
            case R.id.btnAddToCart:
                validateInput("cart");
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.stateSpinner:
                StateData stateData = (StateData) parent.getSelectedItem();
                String stateId = stateData.getStateId();
                String stateName = stateData.getStateName();
                Log.e("Selected State => ", "state_id => " + stateId + " statename=> " + stateName);
                if (!stateId.equalsIgnoreCase("-1")) {
                    selectedStateId = stateId;
                    selectedStateName = stateName;
                }
                SharedPref.setSelectedState(selectedStateName);
                break;

            case R.id.spinnerStartPoint:
                selectedPoint = (String) parent.getSelectedItem();
                break;

            case R.id.spinnerNoOfPerson:
                selectedPersonCount = (String)parent.getSelectedItem();
                int basePrice = Integer.parseInt(txt_amount.getText().toString());
                String totalPrice = String.valueOf(basePrice*Integer.parseInt(selectedPersonCount));
                tvFinalAmount.setText(totalPrice);
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

}
