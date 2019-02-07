package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Activties;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnDismissListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Adapter.ProductListRecyclerAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.App.AppController1;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.AppUtils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Modal.ProductListBean;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Product.ProductViewActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Support.RootActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Utils.Itags;
import dmax.dialog.SpotsDialog;

public class ProductCatagory extends RootActivity implements ProductListRecyclerAdapter.ItemClickRecListInterface {

    private Context context;
    SpotsDialog prog;
    private RecyclerView recyclerview_product_catagory;
    Toolbar toolbar;
    private TextView txt_tittle;
    ArrayList<ProductListBean> productListBeanList;
    ProductListRecyclerAdapter productListRecyclerAdapter;
    private static int FILTER_SORT_TIME_OUT = 3000;
    private LinearLayout ll_filtersort, ll_filter;
    String tittle, masterType, str_Banner, service_id;

    KenBurnsView img_banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_catagory);
        context = ProductCatagory.this;
        createIDS();
        getDetail();
        FilterVisiblity();
        clickListner();

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
            createList(service_id);

        }
    }

    private void clickListner() {
        ll_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogfilter();
            }
        });
    }

    private void ShowDialogfilter() {
        Holder holder;
        holder = new ViewHolder(R.layout.filter_content);
        showOnlyContentDialog(holder, Gravity.BOTTOM, clickListener, dismissListener, seeklistner, true);
    }

    OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(DialogPlus dialog, View view) {
            switch (view.getId()) {

                case R.id.like_it_button:
                    Toast.makeText(ProductCatagory.this, "We're glad that you like it", Toast.LENGTH_LONG).show();
                    break;


            }
            dialog.dismiss();
        }
    };
    SeekBar.OnSeekBarChangeListener seeklistner = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) findViewById(R.id.rangeSeekbar5);
            rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
                @Override
                public void finalValue(Number minValue, Number maxValue) {
                    Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
                }
            });
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    OnDismissListener dismissListener = new OnDismissListener() {
        @Override
        public void onDismiss(DialogPlus dialog) {
            //        Toast.makeText(MainActivity.this, "dismiss listener invoked!", Toast.LENGTH_SHORT).show();
        }
    };

    private void showOnlyContentDialog(Holder holder, int gravity, OnClickListener clickListener, OnDismissListener dismissListener, SeekBar.OnSeekBarChangeListener seeklistner, boolean expanded) {
        final DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(holder)
                .setGravity(gravity)

                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        Log.d("DialogPlus", "onItemClick() called with: " + "item = [" +
                                item + "], position = [" + position + "]");
                    }
                })

                .setExpanded(expanded)
                .setOnClickListener(clickListener)
                .setCancelable(true)

                .create();
        dialog.show();
    }

    private void FilterVisiblity() {
        recyclerview_product_catagory.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == 0) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ll_filtersort.setVisibility(View.GONE);
                        }
                    }, FILTER_SORT_TIME_OUT);

                } else {
                    ll_filtersort.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }


    private void createList(final String service_id) {
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

                                        JSONArray jsArray = jsData.getJSONArray("services");
                                        for (int i = 0; i < jsArray.length(); i++) {
                                            productListBeanList.add(new ProductListBean(
                                                    jsArray.getJSONObject(i).getString("package_id"),
                                                    jsArray.getJSONObject(i).getString("package_name"),
                                                    jsArray.getJSONObject(i).getString("service_name"),
                                                    jsArray.getJSONObject(i).getString("service_id"),
                                                    jsArray.getJSONObject(i).getString("short_description"),
                                                    jsArray.getJSONObject(i).getString("description"),
                                                    jsArray.getJSONObject(i).getString("country"),
                                                    jsArray.getJSONObject(i).getString("city"),
                                                    jsArray.getJSONObject(i).getString("state"),
                                                    jsArray.getJSONObject(i).getString("price"),
                                                    jsArray.getJSONObject(i).getString("image"),
                                                    jsArray.getJSONObject(i).getString("status"),
                                                    false
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

                params.put("action", UtilsUrl.Action_VendorList);
                params.put("service_id", service_id);
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
        ll_filtersort = (LinearLayout) findViewById(R.id.ll_filtersort);
        ll_filter = (LinearLayout) findViewById(R.id.ll_filter);

        recyclerview_product_catagory = (RecyclerView) findViewById(R.id.recyclerview_product_catagory);
        recyclerview_product_catagory.setNestedScrollingEnabled(false);


        productListBeanList = new ArrayList<>();
        productListRecyclerAdapter = new ProductListRecyclerAdapter(context, productListBeanList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setAutoMeasureEnabled(true);
        recyclerview_product_catagory.setLayoutManager(mLayoutManager);
        recyclerview_product_catagory.setItemAnimator(new DefaultItemAnimator());

        recyclerview_product_catagory.setAdapter(productListRecyclerAdapter);


        setSupportActionBar(toolbar);
        prog = new SpotsDialog(this, R.style.Custom);
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Adventure");

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.collapsingToolbarLayoutTitleColor);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsingToolbarLayoutTitleColor);
//
        txt_tittle = (TextView) findViewById(R.id.txt_tittle);
    }


    @Override
    public void onItemClick(int position) {




        Intent i = new Intent(ProductCatagory.this, ProductViewActivity.class);
        i.putExtra("masterType", masterType);
        i.putExtra("serviceID", service_id);
        i.putExtra("pacakageID", productListBeanList.get(position).getStr_package_id());
        i.putExtra("pacakageNAME", productListBeanList.get(position).getStr_package_name());
        i.putExtra("pacakageShortDES", productListBeanList.get(position).getStr_short_description());
        i.putExtra("pacakageDES", productListBeanList.get(position).getStr_description());
        i.putExtra("price", productListBeanList.get(position).getStr_price());
        i.putExtra("image", productListBeanList.get(position).getStr_image());

        startActivity(i);
    }
}
