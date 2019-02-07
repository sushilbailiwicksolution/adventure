package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Camp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.flaviofaria.kenburnsview.KenBurnsView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Adapter.CAmpMasterAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Adapter.ProductListRecyclerAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.App.AppController1;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.AppUtils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Modal.CampMasterModel;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Modal.ProductListBean;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Support.RootActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Utils.Itags;

/**
 * Created by Prince on 25-01-2018.
 */

public class CampMasterActivity extends RootActivity implements CAmpMasterAdapter.ItemClickMasterMenuListInterface, ProductListRecyclerAdapter.ItemClickRecListInterface {


    Context context;
    String tittle, masterType, str_Banner, service_id;

    SpotsDialog prog;
    private RecyclerView recyclerview_product_catagory, recyclerview_camp_list;

    Toolbar toolbar;
    private TextView txt_tittle;
    KenBurnsView img_banner;


    ArrayList<CampMasterModel> CampListBeanList;
    CAmpMasterAdapter CampListRecyclerAdapter;

    ArrayList<ProductListBean> productListBeanList;
    ProductListRecyclerAdapter productListRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camp_master);
        context = CampMasterActivity.this;
        createIDS();
        getDetail();
    }


    private void getDetail() {
        Bundle extras = getIntent().getExtras();
        String userName;

        if (extras != null) {
            service_id = extras.getString("masterId");
            tittle = extras.getString("masterTittle");
            masterType = extras.getString("masterType");
            str_Banner = extras.getString("masterImage");
            txt_tittle.setText(tittle);

            img_banner = (KenBurnsView) findViewById(R.id.img_banner);

            Glide.with(context).load("http://bailiwicksolution.com/adventure" + str_Banner).error(R.drawable.camp_banner).into(img_banner);

            // and get whatever type user account id is
            createListMaster();

        }
    }

    private void createListMaster() {
        prog.setTitle("Loading Please wait.");
        prog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL,
                new Response.Listener<String>() {
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

                                        JSONArray jsArray = jsData.getJSONArray("camp_type");
                                        for (int i = 0; i < jsArray.length(); i++) {
                                            Log.e("Camp Value", jsArray.getJSONObject(i).getString("camp_type_name"));

                                            CampListBeanList.add(new CampMasterModel(jsArray.getJSONObject(i).getString("camp_type_id")
                                                    , jsArray.getJSONObject(i).getString("camp_type_name")));
                                        }
                                        CampListRecyclerAdapter.notifyDataSetChanged();
                                        if (jsArray.length() > 0) {
                                            createListCamp(jsArray.getJSONObject(0).getString("camp_type_id"));
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

                params.put("action", UtilsUrl.Action_getCAMPTYPE);
                Log.e("Param Response ", "" + params);
                return params;
            }
        };

        AppController1.getInstance().addToRequestQueue(stringRequest);


    }


    private void createListCamp(final String camp_type) {
        prog.setTitle("Loading Please wait.");
        prog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL,
                new Response.Listener<String>() {
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
                                        if (productListBeanList.size() > 0) {
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
                                                    true
                                            ));


                                        }

                                        productListRecyclerAdapter.notifyDataSetChanged();

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
                Log.e("Param header ", "" + header);

                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("action", UtilsUrl.Action_getCAMPList);
                params.put("camp_type", camp_type);
                Log.e("Param Response ", "" + params);
                return params;
            }
        };

        AppController1.getInstance().addToRequestQueue(stringRequest);


    }


    private void createIDS() {

        toolbar = (Toolbar) findViewById(R.id.toolbar_cat);
        toolbar.setTitle("Adventure");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        recyclerview_product_catagory = (RecyclerView) findViewById(R.id.recyclerview_product_catagory);
        recyclerview_camp_list = (RecyclerView) findViewById(R.id.recyclerview_camp_list);

        recyclerview_camp_list.setNestedScrollingEnabled(false);
        recyclerview_product_catagory.setNestedScrollingEnabled(false);

// Master List
        CampListBeanList = new ArrayList<>();
        CampListRecyclerAdapter = new CAmpMasterAdapter(context, CampListBeanList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CampMasterActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager.setAutoMeasureEnabled(true);
        recyclerview_product_catagory.setLayoutManager(mLayoutManager);
        recyclerview_product_catagory.setItemAnimator(new DefaultItemAnimator());
        recyclerview_product_catagory.setAdapter(CampListRecyclerAdapter);


        productListBeanList = new ArrayList<>();
        productListRecyclerAdapter = new ProductListRecyclerAdapter(context, productListBeanList, this);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(context);
        mLayoutManager1.setAutoMeasureEnabled(true);
        recyclerview_camp_list.setLayoutManager(mLayoutManager1);
        recyclerview_camp_list.setItemAnimator(new DefaultItemAnimator());

        recyclerview_camp_list.setAdapter(productListRecyclerAdapter);


        setSupportActionBar(toolbar);
        prog = new SpotsDialog(this, R.style.Custom);
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Camping");

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.collapsingToolbarLayoutTitleColor);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsingToolbarLayoutTitleColor);
//
        txt_tittle = (TextView) findViewById(R.id.txt_tittle);

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
        createListCamp(CampListBeanList.get(position).getStr_id());

    }
}

