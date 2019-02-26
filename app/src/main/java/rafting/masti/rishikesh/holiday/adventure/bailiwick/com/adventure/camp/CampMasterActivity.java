package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.camp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.flaviofaria.kenburnsview.KenBurnsView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import dmax.dialog.SpotsDialog;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Adapter.CampMasterAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Adapter.ProductListRecyclerAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Adapter.SpinnerCustomAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.App.AppController1;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.apputils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model.CampMasterModel;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model.ProductListBean;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model.StateData;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.RootActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Const;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.GpsTracker;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Itags;

/**
 * Created by Prince on 25-01-2018.
 */

public class CampMasterActivity extends RootActivity implements CampMasterAdapter.ItemClickMasterMenuListInterface,
        ProductListRecyclerAdapter.ItemClickRecListInterface, AdapterView.OnItemSelectedListener {


    Context context;
    String tittle, masterType, str_Banner, service_id;

    SpotsDialog prog;
    private RecyclerView recyclerview_product_catagory, recyclerview_camp_list;

    Toolbar toolbar;
    private TextView txt_tittle;
    KenBurnsView img_banner;

    ArrayList<CampMasterModel> campListBeanList;
    CampMasterAdapter campListRecyclerAdapter;

    ArrayList<ProductListBean> productListBeanList;
    ProductListRecyclerAdapter productListRecyclerAdapter;
    Spinner spinnerState;
    List<StateData> stateList;
    String selectedStateId, selectedStateName;

    /*private TabLayout campTypeTab;
    private ViewPager campViewPager;
    private ViewPagerAdapter viewPagerAdapter;
    int numberOfTabs = 0;*/
    String campType;
    SpinnerCustomAdapter spinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camp_master);
        context = CampMasterActivity.this;
        stateList = new ArrayList<>();
        Toast.makeText(context, "Select Camp Type to See Packages", Toast.LENGTH_SHORT).show();
        createIDS();
        getDetail();
        getState();
       // checkAndRequestPermissions();

    }


    private void getDetail() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            service_id = extras.getString("masterId");
            tittle = extras.getString("masterTittle");
            masterType = extras.getString("masterType");
            str_Banner = extras.getString("masterImage");
            txt_tittle.setText(tittle);
            img_banner = findViewById(R.id.img_banner);
            Log.e("Image ban => ",str_Banner);
            Glide.with(context).load("http://bailiwicksolution.com/adventure" + str_Banner).error(R.drawable.new_camp_banner).into(img_banner);

        }
    }

    private void getCurrentLocation() {

        Log.e("Inside", "getCurrent Loc");
        double latitude,longitude;
        GpsTracker gps = new GpsTracker(this);
        if(gps.canGetLocation()){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            String stateNameByLoc = getStateByLatLong(latitude, longitude);
            Log.e("Loc", "lat " + latitude + " long" + longitude + " state " + stateNameByLoc);
            // \n is for new line
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
        /*LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        double longitude,latitude;
        Location location;
        try {
            location = Objects.requireNonNull(locationManager).getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location != null){
                Log.e("Loc => ","Lat and long not null");
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                String stateNameByLoc = getStateByLatLong(latitude, longitude);
                Log.e("Loc", "lat " + latitude + " long" + longitude + " state " + stateNameByLoc);
            }else{
                Log.e("Loc => ","Lat and long null");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("GPS is disabled Please on GPS in High Accuracy Mode");
                builder.setPositiveButton("GPS On", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent gpsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(gpsIntent);
                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }*/




    }

    private String getStateByLatLong(double lati, double longi) {
        Geocoder geocoder;
        String address, city, state = null, country, postalCode, knownName;
        List<Address> addressList = null;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addressList = geocoder.getFromLocation(lati, longi, 1);
            Log.e("Loc => ","address list size " + addressList.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addressList != null) {

            address = addressList.get(0).getAddressLine(0);
            city = addressList.get(0).getLocality();
            state = addressList.get(0).getAdminArea();
            country = addressList.get(0).getCountryName();
            postalCode = addressList.get(0).getPostalCode();
            knownName = addressList.get(0).getFeatureName();
        }else{
            Log.e("Loc => ",""+ Objects.requireNonNull(addressList).toString());
        }
        return state;
    }

    @Override
    protected void onResume() {
        checkAndRequestPermissions();
        super.onResume();
    }

    private void createListMaster() {

        if (new CheckConnectivity().isConnected(context)) {
            prog.setTitle("Loading Please wait.");
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

                                JSONArray jsArray = jsData.getJSONArray("camp_type");

                                /*List<String> campTypeName = new ArrayList<>();
                                List<String> campType = new ArrayList<>();*/

                                for (int i = 0; i < jsArray.length(); i++) {
                                    Log.e("Camp Value", jsArray.getJSONObject(i).getString("camp_type_name"));

                                    campListBeanList.add(new CampMasterModel(jsArray.getJSONObject(i).getString("camp_type_id")
                                            , jsArray.getJSONObject(i).getString("camp_type_name")));
                                    /*campTypeName.add(jsArray.getJSONObject(i).getString("camp_type_name"));
                                    campType.add(jsArray.getJSONObject(i).getString("camp_type_id"));*/

                                }
                                campListRecyclerAdapter.notifyDataSetChanged();
                                //   numberOfTabs = campListBeanList.size();
                                if (jsArray.length() > 0) {
                                    campType = jsArray.getJSONObject(0).getString("camp_type_id");
                                    //createListCamp(camp_type_id);
                                    //setTabLayout(numberOfTabs, campTypeName,campType);
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
                public Map<String, String> getHeaders() {
                    Map<String, String> header = new HashMap<>();
                    header.put(Itags.Header, Const.APP_TOKEN);
                    // params.put("Accept-Language", "fr");
                    return header;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();

                    params.put(Const.KEY_ACTION, UtilsUrl.Action_getCAMPTYPE);
                    Log.e("Param Response ", "" + params);
                    return params;
                }
            };
            AppController1.getInstance().addToRequestQueue(stringRequest);
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
        }


    }

    private void setTabLayout(int numberOfTabs, List<String> campTypeName, List<String> campType) {
        /*viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), numberOfTabs, campTypeName,campType);

        campViewPager = findViewById(R.id.viewPagerCampType);
        campViewPager.setAdapter(viewPagerAdapter);
        campTypeTab = findViewById(R.id.tabCampType);
        campTypeTab.setupWithViewPager(campViewPager)*/
        ;
    }

    private void createListCamp(final String camp_type, final String stateName) {

        if (new CheckConnectivity().isConnected(context)) {

            prog.setTitle("Loading Please wait.");
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
                                if (productListBeanList.size() > 0) {
                                    Log.e("product list size ->", "" + productListBeanList.size());
                                    productListBeanList.clear();
                                }
                                JSONArray jsArray = jsData.getJSONArray("camps");
                                for (int i = 0; i < jsArray.length(); i++) {
                                    productListBeanList.add(new ProductListBean(
                                            jsArray.getJSONObject(i).getString("camp_id"),
                                            jsArray.getJSONObject(i).getString("camp_name"),
                                            jsArray.getJSONObject(i).getString("camp_city"),
                                            jsArray.getJSONObject(i).getString("camp_id"),
                                            jsArray.getJSONObject(i).getString("camp_short_desc"),
                                            jsArray.getJSONObject(i).getString("camp_short_desc"),
                                            "india",
                                            jsArray.getJSONObject(i).getString("camp_city"),
                                            jsArray.getJSONObject(i).getString("camp_city"),
                                            jsArray.getJSONObject(i).getString("price_perunit"),
                                            jsArray.getJSONObject(i).getString("camp_image"),
                                            jsArray.getJSONObject(i).getString("camp_id"),
                                            true,
                                            jsArray.getJSONObject(i).getString("including_food")
                                    ));
                                }
                                productListRecyclerAdapter.notifyDataSetChanged();

                            } else {
                                String msg = jsData.getString("msg");
                                productListBeanList.clear();
                                productListRecyclerAdapter.notifyDataSetChanged();
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(context, "Invalid Response !!!", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
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
                public Map<String, String> getHeaders() {
                    Map<String, String> header = new HashMap<>();
                    header.put(Itags.Header, Const.APP_TOKEN);
                    // params.put("Accept-Language", "fr");
                    Log.e("Param header ", "" + header);
                    return header;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put(Const.KEY_ACTION, UtilsUrl.Action_getCAMPList);
                    params.put(Const.KEY_CAMP_TYPE, camp_type);
                    params.put(Const.KEY_STATE_NAME, stateName);
                    Log.e("Param Request ", "" + params);
                    return params;
                }
            };
            AppController1.getInstance().addToRequestQueue(stringRequest);
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    private void getState() {

        if (new CheckConnectivity().isConnected(context)) {
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
                                createListMaster();
                                JSONArray data = jsData.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    String state_id = data.getJSONObject(i).getString("state_id");
                                    String state_name = data.getJSONObject(i).getString("state_name");
                                    stateList.add(new StateData(state_id, state_name));
                                }
                                setSpinner(stateList);
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
            AppController1.getInstance().addToRequestQueue(stateRequest);
        } else {
            Toast.makeText(context, "No Network Coverage", Toast.LENGTH_SHORT).show();
        }
    }

    private void setSpinner(List<StateData> stateList) {
        spinnerAdapter = new SpinnerCustomAdapter(this, R.layout.spinner_item, R.id.spinTitle, stateList);
        spinnerState.setAdapter(spinnerAdapter);

        if (!SharedPref.getSelectedState().equalsIgnoreCase("")) {

            Log.e("Inside","Set Def"+stateList.size());
            for (StateData stateData : stateList) {
                if (SharedPref.getSelectedState().equalsIgnoreCase(stateData.getStateName())) {
                    Log.e("Inside","Set Def"+SharedPref.getSelectedState());
                    spinnerState.setSelection(((SpinnerCustomAdapter) spinnerState.getAdapter()).getPosition(stateData));
                }else{
                    Log.e("Inside","Set Def else");
                }
            }

        }

    }

    private void createIDS() {

        toolbar = findViewById(R.id.toolbar_cat);
        toolbar.setTitle("Adventure");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        recyclerview_product_catagory = findViewById(R.id.recyclerview_product_catagory);
        recyclerview_camp_list = findViewById(R.id.recyclerview_camp_list);

        recyclerview_camp_list.setNestedScrollingEnabled(false);
        recyclerview_product_catagory.setNestedScrollingEnabled(false);

// Master List
        campListBeanList = new ArrayList<>();
        campListRecyclerAdapter = new CampMasterAdapter(context, campListBeanList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CampMasterActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager.setAutoMeasureEnabled(true);
        recyclerview_product_catagory.setLayoutManager(mLayoutManager);
        recyclerview_product_catagory.setItemAnimator(new DefaultItemAnimator());
        recyclerview_product_catagory.setAdapter(campListRecyclerAdapter);


        productListBeanList = new ArrayList<>();
        productListRecyclerAdapter = new ProductListRecyclerAdapter(context, productListBeanList, this);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(context);
        mLayoutManager1.setAutoMeasureEnabled(true);
        recyclerview_camp_list.setLayoutManager(mLayoutManager1);
        recyclerview_camp_list.setItemAnimator(new DefaultItemAnimator());
        recyclerview_camp_list.setAdapter(productListRecyclerAdapter);

        setSupportActionBar(toolbar);
        prog = new SpotsDialog(this, R.style.Custom);
        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Camping");

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.collapsingToolbarLayoutTitleColor);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsingToolbarLayoutTitleColor);
//
        txt_tittle = findViewById(R.id.txt_tittle);
        spinnerState = findViewById(R.id.spinnerState);

        spinnerState.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemClick(int position) {
        Log.e("my position ", ": " + position);

        Intent i = new Intent(CampMasterActivity.this, Camp_viewActivity.class);
        i.putExtra("camp_id", productListBeanList.get(position).getStr_package_id());
        i.putExtra("camp_name", productListBeanList.get(position).getStr_package_name());
        i.putExtra("camp_amount", productListBeanList.get(position).getStr_price());
        i.putExtra("camp_desc", productListBeanList.get(position).getStr_description());

        startActivity(i);
    }

    @Override
    public void onItemMasterClick(int position) {
        campType = campListBeanList.get(position).getStr_id();
        createListCamp(campType,selectedStateName);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        StateData stateData = (StateData) parent.getSelectedItem();
        String stateId = stateData.getStateId();
        String stateName = stateData.getStateName();
        Log.e("Selected State => ", "state_id => " + stateId + " statename=> " + stateName);
        if (!stateId.equalsIgnoreCase("-1")) {
            selectedStateId = stateId;
            selectedStateName = stateName;
        }
        SharedPref.setSelectedState(selectedStateName);
        if (campType != null && !campType.equalsIgnoreCase("")) {
            createListCamp(campType, selectedStateName);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        StateData stateData = (StateData) parent.getItemAtPosition(0);
        String stateId = stateData.getStateId();
        String stateName = stateData.getStateName();
        Log.e("On nothing => ", "state_id => " + stateId + " statename=> " + stateName);
        if (!stateId.equalsIgnoreCase("-1")) {
            selectedStateId = stateId;
            selectedStateName = stateName;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        Log.d("SplashScreen", "Permission callback called-------");
        switch (requestCode) {
            case 101: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);

                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                            perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Log.d("Camp Page", "location services permission granted");
                        // process the normal flow
                        getCurrentLocation();
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d("Camp Page", "Some permissions are not granted ask again ");

                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) ) {

                            showDialogOK("Enable Permission required for this app", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            checkAndRequestPermissions();
                                            break;
                                        case DialogInterface.BUTTON_NEGATIVE:
                                            finish();
                                            break;
                                    }
                                }
                            });

                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            showMessageOKCancel("Enable permissions to access application!", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                        }
                    }
                }
            }
        }

    }

    private void checkAndRequestPermissions() {

        int readLocationCoarse = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int Location_fine = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (readLocationCoarse != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (Location_fine != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 101);
        } else {
            getCurrentLocation();
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(this).setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", CampMasterActivity.this.getPackageName(), null);
                intent.setData(uri);
                CampMasterActivity.this.startActivity(intent);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        }).create().show();

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(this).setMessage(message).setPositiveButton("OK", okListener).setNegativeButton("Cancel", okListener).setCancelable(false).create().show();
    }


}

