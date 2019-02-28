package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.product;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.relex.circleindicator.CircleIndicator;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.adapter.ImageSliderAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.app.AppController;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.apputils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.custom_dialog.SweetAlertDialog;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.dialog.SumbitQuery;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.enquiryforms.EnquiryForm1;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.RootActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Const;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Itags;

public class ProductViewActivity extends RootActivity implements View.OnClickListener {

    CircleIndicator indicator;
    Button btn_addToCart;
    TextView viewMoreAdvantage;
    ImageView back_button, btnShareCart, cart_back;
    AppBarLayout app_bar;
    ViewPager viewPager;


    TextView text_open_description, text_dna_description, text_care_description;
    LinearLayout ll_gallery;
    TableLayout dna_table_product;
    // Handel Screen
    private TextView text_product_name, text_final_price;

    private ViewPager mPager;
    private int currentPage = 0;
    private static final Integer[] productImages = {R.drawable.catlog, R.drawable.bungi, R.drawable.camp};
    private ArrayList<Integer> productArray = new ArrayList<>();
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    String masterType, serviceID;

    private EditText editTextUserName, edt_comment;
    private RatingBar ratingbar;
    private Button btn_submit_review;

    private Context context;
    ProgressDialog prg;
    String pacakageID, pacakageNAME, pacakageShortDES, pacakageDES, price, image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_product_view);
        context = this;
        prg = new ProgressDialog(context);

        setToolbar();
        getUiObject();
        getIntentdetail();
        clickListner();
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
                    header.put(Itags.Header, Const.APP_TOKEN);
                    // params.put("Accept-Language", "fr");
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
            Toast.makeText(context, "Check Your connetion", Toast.LENGTH_LONG).show();
        }


    }

    private void getIntentdetail() {
        Bundle extras = getIntent().getExtras();
        String userName;

        if (extras != null) {
            masterType = extras.getString("masterType");
            serviceID = extras.getString("serviceID");
            pacakageID = extras.getString("pacakageID");
            pacakageNAME = extras.getString("pacakageNAME");
            pacakageShortDES = extras.getString("pacakageShortDES");
            pacakageDES = extras.getString("pacakageDES");
            price = extras.getString("price");
            image = extras.getString("image");

            text_product_name.setText(pacakageNAME);
            text_final_price.setText("₹ " + price);
            text_open_description.setText(pacakageDES);
            // and get whatever type user account id is
        }
    }


    private void getUiObject() {
        viewMoreAdvantage = findViewById(R.id.view_more_1);
        //viewMoreDna = (TextView) findViewById(R.id.view_more_2);
        text_open_description = findViewById(R.id.description_advantage);
        text_care_description = findViewById(R.id.description_pcare);
        btn_addToCart = findViewById(R.id.btn_addToCart);
        back_button = findViewById(R.id.image_cart_back);
        ll_gallery = findViewById(R.id.ll_gallery);
        cart_back = findViewById(R.id.cart_back);
        btnShareCart = findViewById(R.id.image_cart_share);
        viewPager = findViewById(R.id.slider_product_image);
        dna_table_product = findViewById(R.id.product_dna_table);

        text_product_name = findViewById(R.id.text_product_name);
        text_final_price = findViewById(R.id.text_final_price);
// Add Review
        editTextUserName = findViewById(R.id.editTextUserName);
        edt_comment = findViewById(R.id.edt_comment);
        btn_submit_review = findViewById(R.id.btn_submit_review);
        ratingbar = findViewById(R.id.ratingbar);

        app_bar = findViewById(R.id.app_bar);
        btn_addToCart.bringToFront();

        //viewMoreDna.setOnClickListener(this);
        viewMoreAdvantage.setOnClickListener(this);
        back_button.setOnClickListener(this);
        btn_addToCart.setOnClickListener(this);
        ll_gallery.setOnClickListener(this);
        cart_back.setOnClickListener(this);


        productArray.addAll(Arrays.asList(productImages));

        mPager = findViewById(R.id.slider_product_image);
        mPager.setAdapter(new ImageSliderAdapter(productArray, ProductViewActivity.this));
        indicator = findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (currentPage == productImages.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 5000, 2500);

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

            /*case R.id.view_more_2:
                if (viewMoreDna.getText().toString().equalsIgnoreCase("View Product Dna")) {
                    viewMoreDna.setText("View Less");
                    dna_table_product.setVisibility(View.VISIBLE);
                } else {
                    viewMoreDna.setText("View Product Dna");
                    dna_table_product.setVisibility(View.GONE);
                }
                break;
*/

            case R.id.image_cart_back:
                finish();
                break;

            case R.id.btn_addToCart:
                Log.e("Master type", "Master Type" + masterType);
                if (masterType.equalsIgnoreCase("3")) {
                    OpenQueryForm();

                } else {
                    EnquiryForm1 dialogenq1 = new EnquiryForm1(ProductViewActivity.this);
                    dialogenq1.show();
                }
/*
                Intent addCartIntent = new Intent(this, EnquiryForm1.class);
                startActivity(addCartIntent);
*/

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

        }
    }

    private void OpenQueryForm() {
        final SumbitQuery queryDialog = new SumbitQuery(ProductViewActivity.this);
        queryDialog.btn_Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_date, str_name, str_email, str_mobile, str_count, str_message;
                str_date = queryDialog.txt_booking_date.getText().toString();

                str_name = queryDialog.edt_name.getText().toString().trim();
                str_email = queryDialog.edt_email.getText().toString().trim();
                str_mobile = queryDialog.edt_mobile.getText().toString().trim();
                str_count = queryDialog.edt_no_of_adult.getText().toString().trim();
                str_message = queryDialog.edt_message.getText().toString().trim();
                if (str_date.equalsIgnoreCase("Booking Date")) {
                    queryDialog.txt_booking_date.setError("Please select date ");
                } else if (str_name.equalsIgnoreCase("")) {
                    queryDialog.edt_name.setError("Required");
                    queryDialog.edt_name.requestFocus();
                } else if (str_count.equalsIgnoreCase("")) {
                    queryDialog.edt_no_of_adult.setError("Required");
                    queryDialog.edt_no_of_adult.requestFocus();
                } else if (str_email.equalsIgnoreCase("")) {
                    queryDialog.edt_email.setError("Required");
                    queryDialog.edt_email.requestFocus();
                } else if (!emailValidator(str_email)) {
                    queryDialog.edt_email.setError("Invalid id");
                    queryDialog.edt_email.requestFocus();
                } else if (str_mobile.equalsIgnoreCase("")) {
                    queryDialog.edt_mobile.setError("Required");
                    queryDialog.edt_mobile.requestFocus();
                } else if (str_message.equalsIgnoreCase("")) {
                    queryDialog.edt_message.setError("Required");
                    queryDialog.edt_message.requestFocus();
                } else {
                    Toast.makeText(context, "All Set", Toast.LENGTH_SHORT).show();
                    if (new CheckConnectivity().isConnected(context)) {
                        submitQuery(str_date, str_name, str_email, str_mobile, str_count, str_message, queryDialog);

                    } else {
                        Toast.makeText(getApplicationContext(), "Check Your Connection", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        queryDialog.show();

    }

    private void submitQuery(final String str_date, final String str_name, final String str_email, final String str_mobile, final String str_count, final String str_message, final SumbitQuery queryDialog) {

        if (new CheckConnectivity().isConnected(context)) {
            prg.setMessage("Please Wait....");
            prg.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        prg.dismiss();
                        queryDialog.dismiss();
                        Log.e("Response : prince ", response);
                        if (response != null) {
                            JSONObject jsData = new JSONObject(response);
                            String status = jsData.getString("status");
                            if (status.equalsIgnoreCase("1")) {

                                String msg = jsData.getString("msg");
                                //   Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

                                new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Good job!")
                                        .setContentText(" We Will Contact You Shortly!")
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

                    params.put("action", UtilsUrl.Action_SubmitQuery);
                    params.put("user_id", SharedPref.getUserID());

                    params.put("booking_date", str_date);
                    params.put("adults", str_count);
                    params.put("name", str_name);
                    params.put("email", str_email);
                    params.put("message", str_message);
                    params.put("package_id", pacakageID);
                    params.put("mobile_no", str_mobile);

                    Log.e("Param Response ", "" + params);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);
        } else {
            Toast.makeText(context, "Check Your connetion", Toast.LENGTH_LONG).show();
        }


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
