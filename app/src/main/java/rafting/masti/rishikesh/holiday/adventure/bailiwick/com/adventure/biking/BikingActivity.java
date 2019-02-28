package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.biking;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import dmax.dialog.SpotsDialog;
import me.relex.circleindicator.CircleIndicator;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.adapter.ImageSliderAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.adapter.SpinnerCustomAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.app.AppController;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.apputils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.custom_dialog.SweetAlertDialog;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model.StateData;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Const;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Itags;

public class BikingActivity extends AppCompatActivity implements View.OnClickListener, RatingBar.OnRatingBarChangeListener, AdapterView.OnItemSelectedListener {

    AppBarLayout appBarBiking;
    CollapsingToolbarLayout ctlBiking;
    ViewPager viewPagerBiking;
    CircleIndicator indicatorBiking;
    Toolbar toolbarBiking;
    ImageView ivBackBiking, ivShareBiking;
    TextView descriptionCycling,viewMoreCycling,tvPlanName;
    EditText editTextUserName,edt_comment;
    RatingBar ratingbar;
    Button btn_submit_review,btnBuyCycling,btnCartCycling;
    SpotsDialog prog;
    String serviceId,userRating,selectedStateid,selectedStateName,selectedCityId,selectedCityName;
    SpinnerCustomAdapter spinnerAdapter;
    List<StateData> stateList,cityList;
    Spinner spinnerStateCycling,spinnerCityCycling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biking);
        stateList = new ArrayList<>();
        cityList = new ArrayList<>();
        serviceId = Objects.requireNonNull(getIntent().getExtras()).getString("service_id");
        Log.e("service id => ",serviceId);
        getUiObject();
        setPager();
        getState();

    }

    private void setPager() {
        ArrayList<Integer> productArray = new ArrayList<>();
        productArray.add(R.drawable.new_camp_banner);
        productArray.add(R.drawable.new_camp_banner);
        productArray.add(R.drawable.new_camp_banner);
        viewPagerBiking.setAdapter(new ImageSliderAdapter(productArray, this));
        indicatorBiking.setViewPager(viewPagerBiking);

        tvPlanName.setText("Adventure Biking");
        descriptionCycling.setText(getText(R.string.pacakage_description));
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
                                Toast.makeText(BikingActivity.this, msg, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(BikingActivity.this, "Invalid Response !!!", Toast.LENGTH_LONG).show();
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
            Toast.makeText(BikingActivity.this, "No Network Coverage", Toast.LENGTH_SHORT).show();
        }
    }

    private void getCity(final String state_id){
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
                                Toast.makeText(BikingActivity.this, msg, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(BikingActivity.this, "Invalid Response !!!", Toast.LENGTH_LONG).show();
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

    private void getUiObject(){

        prog = new SpotsDialog(this);

        tvPlanName = findViewById(R.id.tvPlanNameCycle);
        appBarBiking = findViewById(R.id.appBarBiking);
        ctlBiking = findViewById(R.id.ctlBiking);
        viewPagerBiking = findViewById(R.id.viewPagerBiking);
        indicatorBiking = findViewById(R.id.indicatorBiking);
        toolbarBiking = findViewById(R.id.toolbarBiking);
        ivBackBiking = findViewById(R.id.iv_back_biking);
        ivShareBiking = findViewById(R.id.iv_share_biking);
        descriptionCycling = findViewById(R.id.descriptionCycling);
        viewMoreCycling = findViewById(R.id.viewMoreCycling);
        editTextUserName = findViewById(R.id.editTextUserName);
        edt_comment = findViewById(R.id.edt_comment);
        ratingbar = findViewById(R.id.ratingbar);
        btn_submit_review = findViewById(R.id.btn_submit_review);
        btnBuyCycling = findViewById(R.id.btnBuyCycling);
        btnCartCycling = findViewById(R.id.btnCartCycling);
        spinnerStateCycling = findViewById(R.id.spinnerStateCycling);
        spinnerCityCycling = findViewById(R.id.spinnerCityCycling);

        setListeners();

        setSupportActionBar(toolbarBiking);
        ctlBiking.setTitle("Biking");
        ctlBiking.setExpandedTitleTextAppearance(R.style.collapsingToolbarLayoutTitleColor);
        ctlBiking.setCollapsedTitleTextAppearance(R.style.collapsingToolbarLayoutTitleColor);

    }

    private void setListeners(){
        ivBackBiking.setOnClickListener(this);
        ivShareBiking.setOnClickListener(this);
        viewMoreCycling.setOnClickListener(this);
        ratingbar.setOnRatingBarChangeListener(this);
        btn_submit_review.setOnClickListener(this);
        btnBuyCycling.setOnClickListener(this);
        btnCartCycling.setOnClickListener(this);
        spinnerStateCycling.setOnItemSelectedListener(this);
    }

    private void shareMessage(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Biking");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hello try Biking services at http://indianadventurepackages.com");
        startActivity(Intent.createChooser(shareIntent,"Share Via"));
    }

    private void goToConfirmation(String action){
        Intent goToCnfIntent = new Intent(this,BikingConfirmationActivity.class);
        goToCnfIntent.putExtra("action",action);
        goToCnfIntent.putExtra("state_id",selectedStateid);
        goToCnfIntent.putExtra("state_name",selectedStateName);
        goToCnfIntent.putExtra("service_id",serviceId);
        goToCnfIntent.putExtra("title",tvPlanName.getText().toString());
        startActivity(goToCnfIntent);
    }

    private void reviewBiking(){

        String userName = editTextUserName.getText().toString();
        String comment = edt_comment.getText().toString();
        if(userName.equalsIgnoreCase("")){
            editTextUserName.setError("Username is Required");
            editTextUserName.clearFocus();
            editTextUserName.setFocusableInTouchMode(true);
            editTextUserName.requestFocus();
        }else if (comment.equalsIgnoreCase("")){
            edt_comment.setError("Comment is Required");
            edt_comment.clearFocus();
            edt_comment.setFocusableInTouchMode(true);
            edt_comment.requestFocus();
        }else if (userRating.equalsIgnoreCase("")){
            Toast.makeText(this, "Please Provide Rating", Toast.LENGTH_SHORT).show();
        }else{
            saveReview("",userName,comment,userRating,serviceId);
        }

    }

    private void saveReview(final String pacakageId, final String name, final String comment,
                            final String rating, final String serviceId) {

        if (new CheckConnectivity().isConnected(this)) {
            prog.setMessage("Please Wait....");
            prog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        prog.dismiss();
                        Log.e("Response : ", response);
                        if (response != null) {
                            JSONObject jsData = new JSONObject(response);
                            String status = jsData.getString("status");
                            if (status.equalsIgnoreCase("1")) {
                                editTextUserName.setText("");
                                edt_comment.setText("");
                                ratingbar.setNumStars(0);
                                new SweetAlertDialog(BikingActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Thanks!")
                                        .setContentText(" Your Review is Saved")
                                        .show();
                            } else {
                                String msg = jsData.getString("msg");
                                Toast.makeText(BikingActivity.this, msg, Toast.LENGTH_LONG).show();

                                new SweetAlertDialog(BikingActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Something went wrong!")
                                        .show();
                            }
                        } else {
                            Toast.makeText(BikingActivity.this, "Invalid Response !!!", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    prog.dismiss();
                    Toast.makeText(BikingActivity.this, error.toString(), Toast.LENGTH_LONG).show();
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
                    params.put("service_id", serviceId);
                    params.put("package_id", pacakageId);
                    params.put("rating", rating);
                    params.put("name", name);
                    params.put("comment", comment);

                    Log.e("Param Response ", "" + params);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
        } else {
            Toast.makeText(BikingActivity.this, "Check Your connetion", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back_biking:
                finish();
                break;

            case R.id.iv_share_biking:
                shareMessage();
                break;

            case R.id.viewMoreCycling:
                if (descriptionCycling.getMaxLines() < 6){
                    viewMoreCycling.setVisibility(View.GONE);
                }else if (viewMoreCycling.getText().toString().equalsIgnoreCase("View More")) {
                    viewMoreCycling.setVisibility(View.VISIBLE);
                    descriptionCycling.setMaxLines(Integer.MAX_VALUE);
                    viewMoreCycling.setText("View Less");
                } else {
                    viewMoreCycling.setVisibility(View.VISIBLE);
                    descriptionCycling.setMaxLines(6);
                    viewMoreCycling.setText("View More");
                }
                break;

            case R.id.btn_submit_review:
                reviewBiking();
                break;

            case R.id.btnBuyCycling:
                goToConfirmation("book");
                break;

            case R.id.btnCartCycling:
                goToConfirmation("cart");
                break;
        }
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        userRating = String.valueOf(ratingBar.getRating());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()){
            case R.id.spinnerStateCycling:

                StateData stateData = (StateData) parent.getSelectedItem();
                String stateId = stateData.getStateId();
                String stateName = stateData.getStateName();
                Log.e("Selected State => ", "state_id => " + stateId + " statename=> " + stateName);
                if (!stateId.equalsIgnoreCase("-1")) {
                    selectedStateid = stateId;
                    selectedStateName = stateName;
                }
                getCity(selectedStateid);
                SharedPref.setSelectedState(selectedStateName);

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
