package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.camp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TableLayout;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.relex.circleindicator.CircleIndicator;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Activties.CartList;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Activties.HomePage;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Adapter.CampFacilityAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Adapter.ImageUrlSliderAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.App.AppController1;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.apputils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.custom_dialog.SweetAlertDialog;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.database.DBfunction;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.database.Database_Utils;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.database.DbOperation;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.RootActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Commons;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Const;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Itags;

/**
 * Created by Prince on 29-01-2018.
 */

public class Camp_viewActivity extends RootActivity implements View.OnClickListener {

    CircleIndicator indicator;
    Button btnBuyNow, btn_book_now, btnAddToCartTop, btnAddToCartBottom;
    TextView viewMoreAdvantage;
    ImageView back_button, btnShareCart, cart_back;
    AppBarLayout app_bar;
    ViewPager viewPager;

    TextView text_open_description, text_dna_description, text_care_description;
    LinearLayout ll_gallery;
    TableLayout dna_table_product;
    // Handel Screen
    private TextView text_product_name, text_final_price;
    private RecyclerView rec_facility;
    List<String> facilityList;
    CampFacilityAdapter CampListFacilityAdapter;

    private ViewPager mPager;
    private int currentPage = 0;
    // private static final Integer[] productImages = {R.drawable.catlog, R.drawable.bungi, R.drawable.camp};
    private ArrayList<String> productArray = new ArrayList<String>();
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    String masterType, serviceID;

    private EditText editTextUserName, edt_comment;
    private RatingBar ratingbar;
    private Button btn_submit_review;

    private Context context;
    ProgressDialog prg;
    String pacakageID, camp_id;
    String jsResponse = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_camp_view);
        context = Camp_viewActivity.this;
        prg = new ProgressDialog(context);
        setToolbar();
        getUiObject();
        getIntentdetail();
        clickListner();

        if (text_open_description.getLineCount() < 6){
            viewMoreAdvantage.setVisibility(View.GONE);
        }else{
            viewMoreAdvantage.setVisibility(View.VISIBLE);
        }

    }

    private void clickListner() {
        btn_submit_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, comment;
                name = editTextUserName.getText().toString().trim();
                comment = edt_comment.getText().toString().trim();

                float rating = ratingbar.getRating();
                Log.e("check star", "rating" + rating);
                if (name.equalsIgnoreCase("")) {
                    editTextUserName.setError("Mandatory");
                } else if (comment.equalsIgnoreCase("")) {
                    edt_comment.setError("Mandatory");
                } else if (rating == 0) {
                    Toast.makeText(getApplicationContext(), "Please give rating", Toast.LENGTH_SHORT).show();
                } else {
                    saveReview(pacakageID, name, comment, "" + rating, serviceID);
                }
            }
        });
    }

    private void saveReview(final String pacakageID, final String name, final String comment, final String rating, final String serviceID) {

        if (new CheckConnectivity().isConnected(context)) {

            prg.setMessage("Please Wait....");
            prg.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        prg.dismiss();
                        Log.e("Response : prince ", response);
                        if (response != null) {
                            JSONObject jsData = new JSONObject(response);
                            String status = jsData.getString("status");
                            if (status.equalsIgnoreCase("1")) {

                                String msg = jsData.getString("msg");
                                //   Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                                editTextUserName.setText("");
                                edt_comment.setText("");
                                ratingbar.setNumStars(0);
                                new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Thanks!")
                                        .setContentText(" Your Review is Saved")
                                        .show();

                            } else {
                                String msg = jsData.getString("msg");
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

                                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Something went wrong!")
                                        .show();
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
                    prg.dismiss();
                    Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();

                    Log.e("Error :", error.toString());
                }
            }) {

                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> header = new HashMap<>();
                    header.put(Itags.Header, "ABC98XYZ53IJ61L");
                    // params.put("Accept-Language", "fr");
                    Log.e("Param header ", "" + header);

                    return header;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();

                    params.put(Const.KEY_ACTION, UtilsUrl.Action_SubmitReview);
                    params.put(Const.KEY_USER_ID, SharedPref.getUserID());

                    params.put(Const.KEY_SERVICE_ID, serviceID);
                    params.put(Const.KEY_PACKAGE_ID, pacakageID);
                    params.put(Const.KEY_RATING, "" + rating);
                    params.put(Const.KEY_NAME, name);
                    params.put(Const.KEY_COMMENT, comment);

                    Log.e("Param Response ", "" + params);
                    return params;
                }
            };
            AppController1.getInstance().addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(context, "Check Your connetion", Toast.LENGTH_LONG).show();
        }


    }

    private void getIntentdetail() {
        Bundle extras = getIntent().getExtras();
        String userName;

        if (extras != null) {
            camp_id = extras.getString("camp_id");
            pacakageID = extras.getString("camp_id");
            text_product_name.setText(extras.getString("camp_name"));
            text_final_price.setText(extras.getString("camp_amount") + " Rs.");
            text_open_description.setText(extras.getString("camp_desc"));

            // and get whatever type user account id is
            getCampDetail(camp_id);
        }
    }

    private void getCampDetail(final String camp_id) {

        if (new CheckConnectivity().isConnected(context)) {
            prg.setMessage("Please Wait....");
            prg.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        prg.dismiss();
                        Log.e("Response : prince ", response);
                        jsResponse = response;
                        if (response != null) {
                            JSONObject jsData = new JSONObject(response);
                            String status = jsData.getString("status");
                            if (status.equalsIgnoreCase("1")) {

                                String msg = jsData.getString("msg");
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

                                JSONArray jsFacilities = jsData.getJSONArray("facilities");
                                for (int i = 0; i < jsFacilities.length(); i++) {

                                    facilityList.add("" + jsFacilities.getString(i));
                                    Log.e("check", "" + jsFacilities.getString(i));

                                }
                                JSONArray jsCamp_image = jsData.getJSONArray("camp_image");
                                for (int j = 0; j < jsCamp_image.length(); j++) {

                                    productArray.add("" + jsCamp_image.getString(j));
                                    Log.e("check", "" + "" + jsCamp_image.getString(j));
                                }

                                CampListFacilityAdapter.notifyDataSetChanged();
                                mPager.setAdapter(new ImageUrlSliderAdapter(productArray, Camp_viewActivity.this));
                                indicator.setViewPager(mPager);
//                                        final Handler handler = new Handler();
                                final Runnable update = new Runnable() {
                                    @Override
                                    public void run() {
                                        if (currentPage == productArray.size()) {
                                            currentPage = 0;
                                        }
                                        mPager.setCurrentItem(currentPage++, true);
                                    }
                                };

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
                    prg.dismiss();
                    Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
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
                    params.put(Const.KEY_ACTION, UtilsUrl.Action_getCAMPDetail);
                    params.put(Const.KEY_CAMP_ID, camp_id);
                    Log.e("Request Param => ", "" + params);
                    return params;
                }
            };
            AppController1.getInstance().addToRequestQueue(stringRequest);
        } else {
            Toast.makeText(context, "Check Your connetion", Toast.LENGTH_LONG).show();
        }
    }


    private void getUiObject() {
        viewMoreAdvantage = findViewById(R.id.view_more_1);
        //viewMoreDna = (TextView) findViewById(R.id.view_more_2);
        text_open_description = findViewById(R.id.description_advantage);
        text_care_description = findViewById(R.id.description_pcare);
        btnBuyNow = findViewById(R.id.btnBuyNow);
        btn_book_now = findViewById(R.id.btn_book_now);
        btnAddToCartTop = findViewById(R.id.btnAddToCartTop);
        btnAddToCartBottom = findViewById(R.id.btnAddToCartBottom);
        back_button = findViewById(R.id.image_cart_back);
        ll_gallery = findViewById(R.id.ll_gallery);
        cart_back = findViewById(R.id.cart_back);
        btnShareCart = findViewById(R.id.image_cart_share);
        viewPager = findViewById(R.id.viewPagerCamp);
        dna_table_product = findViewById(R.id.product_dna_table);

        text_product_name = findViewById(R.id.text_product_name);
        text_final_price = findViewById(R.id.text_final_price);

        rec_facility = findViewById(R.id.rec_facility);


        facilityList = new ArrayList<>();
        CampListFacilityAdapter = new CampFacilityAdapter(context, facilityList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Camp_viewActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mLayoutManager.setAutoMeasureEnabled(true);
        rec_facility.setLayoutManager(mLayoutManager);
        rec_facility.setItemAnimator(new DefaultItemAnimator());
        rec_facility.setAdapter(CampListFacilityAdapter);

        // Add Review
        editTextUserName = findViewById(R.id.editTextUserName);
        edt_comment = findViewById(R.id.edt_comment);
        btn_submit_review = findViewById(R.id.btn_submit_review);
        ratingbar = findViewById(R.id.ratingbar);

        app_bar = findViewById(R.id.app_bar);
        btnBuyNow.bringToFront();
        btn_book_now.bringToFront();
        //viewMoreDna.setOnClickListener(this);
        viewMoreAdvantage.setOnClickListener(this);
        back_button.setOnClickListener(this);
        btnBuyNow.setOnClickListener(this);
        btn_book_now.setOnClickListener(this);
        ll_gallery.setOnClickListener(this);
        cart_back.setOnClickListener(this);
        btnAddToCartBottom.setOnClickListener(this);
        btnAddToCartTop.setOnClickListener(this);

        mPager = findViewById(R.id.slider_product_image);
        indicator = findViewById(R.id.indicator);



      /*  Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 5000, 2500);
*/
    }

    private void setToolbar() {

        AppBarLayout mAppBarLayout = findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                } else if (isShow) {
                    isShow = false;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.view_more_1:
                if (viewMoreAdvantage.getText().toString().equalsIgnoreCase("View More")) {
                    text_open_description.setMaxLines(Integer.MAX_VALUE);
                    viewMoreAdvantage.setText("View Less");
                } else {
                    text_open_description.setMaxLines(6);
                    viewMoreAdvantage.setText("View More");
                }
                break;

            case R.id.image_cart_back:
                finish();
                break;

            case R.id.btnBuyNow:
                Log.e("Master type", "Master Type" + masterType);
                if (jsResponse.equalsIgnoreCase("")) {
                    Toast.makeText(context, "Camp Detail Not Exist Please wait while we Loading it", Toast.LENGTH_LONG).show();
                    getCampDetail(camp_id);
                } else {
                    campBookingForm("book_now");
                }

                break;
            case R.id.btn_book_now:
                Log.e("Master type", "Master Type" + masterType);
                if (jsResponse.equalsIgnoreCase("")) {
                    Toast.makeText(context, "Camp Detail Not Exist Please wait while we Loading it", Toast.LENGTH_LONG).show();
                    getCampDetail(camp_id);
                } else {
                    campBookingForm("book_now");
                }

                break;

            case R.id.ll_video:
                cart_back.setVisibility(View.VISIBLE);
                indicator.setVisibility(View.GONE);
                ll_gallery.setVisibility(View.VISIBLE);
                back_button.setVisibility(View.GONE);
                btnShareCart.setVisibility(View.GONE);
                viewPager.setVisibility(View.GONE);

                break;

            case R.id.ll_gallery:
                cart_back.setVisibility(View.GONE);
                ll_gallery.setVisibility(View.GONE);
                indicator.setVisibility(View.VISIBLE);
                back_button.setVisibility(View.VISIBLE);
                btnShareCart.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.VISIBLE);
                break;

            case R.id.cart_back:
                finish();
                break;

            case R.id.btnAddToCartTop:
                Log.e("Master type", "Master Type" + masterType);
                if (jsResponse.equalsIgnoreCase("")) {
                    Toast.makeText(context, "Camp Detail Not Exist Please wait while we Loading it", Toast.LENGTH_LONG).show();
                    getCampDetail(camp_id);
                } else {
                    campBookingForm("cart");
                }
                break;

            case R.id.btnAddToCartBottom:
                Log.e("Master type", "Master Type" + masterType);
                if (jsResponse.equalsIgnoreCase("")) {
                    Toast.makeText(context, "Camp Detail Not Exist Please wait while we Loading it", Toast.LENGTH_LONG).show();
                    getCampDetail(camp_id);
                } else {
                    campBookingForm("cart");
                }
                break;

        }
    }

    /* public void addToCart() {
         Intent homeIntent = new Intent(this, HomePage.class);
         homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
         startActivity(homeIntent);
     }*/

    private void campBookingForm(final String action) {

        final CampBookinDialog campBooking = new CampBookinDialog(Camp_viewActivity.this, jsResponse, camp_id);
        campBooking.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBfunction.ExportDatabasee(context, Database_Utils.DB_NAME);
                campBooking.dismiss();
            }
        });
        campBooking.btn_Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_check_in, str_check_out, str_adult_count, str_child_count, str_name, str_price, Str_room_type_id,
                        str_email, str_mobile, str_message;

                str_check_in = campBooking.txt_Check_in.getText().toString();
                str_check_out = campBooking.txt_check_out.getText().toString().trim();
                str_adult_count = "" + campBooking.picker_adult.getValue();
                str_child_count = "" + campBooking.picker_child.getValue();
                str_name = campBooking.edt_name.getText().toString().trim();
                str_price = campBooking.txt_price.getText().toString().trim();

                Str_room_type_id = campBooking.roomTypefull.get(campBooking.spinner_Room_Type.getSelectedItemPosition()).getStr_room_type_id();
                str_email = campBooking.edt_email.getText().toString().trim();
                str_mobile = campBooking.edt_mobile.getText().toString().trim();

                str_message = campBooking.edt_message.getText().toString().trim();
                if (str_check_in.equalsIgnoreCase("check In")) {
                    Toast.makeText(getApplicationContext(), "Please select Check In Date ", Toast.LENGTH_SHORT).show();
                    // campBooking.txt_Check_in.setError("Please select Check In Date ");
                } else if (str_check_out.equalsIgnoreCase("check Out")) {
                    Toast.makeText(getApplicationContext(), "Please select Check out Date ", Toast.LENGTH_SHORT).show();
                    // campBooking.txt_Check_in.setError("Please select Check In Date ");
                } else if (str_name.equalsIgnoreCase("")) {
                    campBooking.edt_name.setError("Required ");
                } else if (str_email.equalsIgnoreCase("")) {
                    campBooking.edt_email.setError("Required");
                    campBooking.edt_email.requestFocus();
                } else if (!emailValidator(str_email)) {
                    campBooking.edt_email.setError("Invalid id");
                    campBooking.edt_email.requestFocus();
                } else if (str_mobile.equalsIgnoreCase("")) {
                    campBooking.edt_mobile.setError("Required");
                    campBooking.edt_mobile.requestFocus();
                } else if (str_message.equalsIgnoreCase("")) {
                    campBooking.edt_message.setError("Required");
                    campBooking.edt_message.requestFocus();
                } else if (!Commons.isValidMobile(str_mobile)) {
                    campBooking.edt_mobile.setError("Enter correct Mobile Number");
                    campBooking.edt_mobile.requestFocus();
                } else {
                    String IN_DateStr = "", OUT_DateStr = "", Days_count = "";
                    Date date_check_in = null,date_check_out = null;
                    boolean isSuccess = false;
                    try {
                        DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
                        String inputDateStr = str_check_in;
                        date_check_in = inputFormat.parse(inputDateStr);
                        // check out date
                        String outDateStr = str_check_out;
                        date_check_out = inputFormat.parse(outDateStr);

                        long diff = date_check_out.getTime() - date_check_in.getTime();

                        System.out.println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                        Days_count = "" + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                        IN_DateStr = outputFormat.format(date_check_in);
                        OUT_DateStr = outputFormat.format(date_check_out);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    String image_url = "";
                    if (productArray.size() > 0) {
                        image_url = productArray.get(0);
                    }

                    int EntryExist = DbOperation.getRaftCount(Camp_viewActivity.this, camp_id);
                    if (EntryExist > 0) {
                        DbOperation.deleteCartItem(Camp_viewActivity.this, camp_id);
                    }
                    int totalamount = (Integer.parseInt(str_price) * Integer.parseInt(Days_count));

                    if ((Objects.requireNonNull(date_check_in).compareTo(date_check_out)) < 0 /*|| (date_check_in.compareTo(date_check_out)==0)*/){
                        isSuccess = DbOperation.insertCampaningCart(Camp_viewActivity.this,
                                camp_id, text_product_name.getText().toString(), "camping", IN_DateStr, OUT_DateStr,
                                Days_count, str_adult_count, str_child_count, "", "", Days_count,
                                "" + campBooking.singleroomPrize, "" + totalamount, image_url, Itags.CAMPING,
                                campBooking.roomTypefull.get(campBooking.spinner_Room_Type.getSelectedItemPosition()).getStr_room_type_id(),
                                str_name, str_mobile, str_message, str_email);
                    }else {
                        Toast.makeText(context, "Check-in Date Should be less than Check-Out Date", Toast.LENGTH_SHORT).show();
                    }
                    Log.e("is saved", "" + isSuccess);

                    if (isSuccess) {
                        Toast.makeText(Camp_viewActivity.this, "Saved in Database", Toast.LENGTH_SHORT).show();
                        //DB_Function.ExportDatabasee(getActivity(), Database_Utils.DB_NAME);
                       // int cartCount = DbOperation.getRaftCount(Camp_viewActivity.this, camp_id);
                        int cartCount = Objects.requireNonNull(DbOperation.getCartList(Camp_viewActivity.this)).size();
                        Log.e("Cart Count => ", "" + cartCount);
                        SharedPref.setCartCount(String.valueOf(cartCount));
                        SharedPref.saveMobile1(str_mobile);
                        if (action.equalsIgnoreCase("book_now")) {
                            Intent i = new Intent(Camp_viewActivity.this, CartList.class);
                            startActivity(i);
                            finish();
                        } else if (action.equalsIgnoreCase("cart")) {
                            Intent homeIntent = new Intent(Camp_viewActivity.this, HomePage.class);
                            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(homeIntent);
                            finish();
                        }

                        /*Intent i = new Intent(Camp_viewActivity.this, CartList.class);
                        startActivity(i);
                        campBooking.dismiss();
                        finish();*/
                    } else {
                        Log.e("Data no Saved", "Data not Saved");
                    }

                }
            }
        });
        campBooking.show();
    }


    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
