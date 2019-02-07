package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Activties;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Adapter.CartListRecyclerAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.App.AppController1;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.AppUtils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Camp.CampMasterActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Database.DBOperation;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Modal.CartListModel;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Rafting.RaftingActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Support.RootActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Utils.Itags;

import static com.payumoney.core.SdkSession.hashCal;

/**
 * Created by Prince on 23-01-2018.
 */

public class CartList extends RootActivity implements CartListRecyclerAdapter.ItemClickRecListInterface {
    public String TAG = this.getClass().getSimpleName();
    private RecyclerView recyclerview_cart;
    ArrayList<CartListModel> cartListBeanList;
    CartListRecyclerAdapter cartListRecyclerAdapter;
    private Context context;
    TextView txt_sub_total, txt_total_pay;
    private Button btn_book_services;
    ProgressDialog prg;
    // pay u
    private PayUmoneySdkInitializer.PaymentParam mPaymentParams;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);
        context = CartList.this;
        createIds();
        createList();
        clickEvent();
        setToolbar();
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Cart");


    }

    private void clickEvent() {
        btn_book_services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //    BookingFaliure("jdskfh9384hf9438ho","100","Success","sbin00","i8fu3jd8djd339","2090","20910","success");
                if (txt_total_pay.getText().toString().equalsIgnoreCase("0") || txt_total_pay.getText().toString().equalsIgnoreCase("")) {

                    Toast.makeText(getApplication(), "No Value in Cart", Toast.LENGTH_LONG).show();

                } else {
                    SaveCartToServer();
                }

            }
        });
    }

    private void SaveCartToServer() {

        try {

            JSONArray jsARRcamping = new JSONArray();
            JSONArray jsARRrafting = new JSONArray();

            for (int i = 0; i < cartListBeanList.size(); i++) {
                JSONObject jsProductCamping = new JSONObject();
                JSONObject jsProductRafting = new JSONObject();
                if (cartListBeanList.get(i).getIsRafting().equalsIgnoreCase(Itags.CAMPING)) {
                    jsProductCamping.put("name", cartListBeanList.get(i).getStr_name());
                    jsProductCamping.put("email", cartListBeanList.get(i).getStr_email());
                    jsProductCamping.put("phone", cartListBeanList.get(i).getStr_mobile());
                    jsProductCamping.put("message", cartListBeanList.get(i).getStr_message());
                    jsProductCamping.put("from_date", cartListBeanList.get(i).getStr_check_in());
                    jsProductCamping.put("to_date", cartListBeanList.get(i).getStr_check_out());
                    jsProductCamping.put("adult", cartListBeanList.get(i).getStr_adult());
                    jsProductCamping.put("child", cartListBeanList.get(i).getStr_child());
                    jsProductCamping.put("adventure_sport", cartListBeanList.get(i).getStr_service());
                    jsProductCamping.put("plan", cartListBeanList.get(i).getStr_room_type_id());
                    jsProductCamping.put("camp_type", cartListBeanList.get(i).getId());
                    jsProductCamping.put("per_price", cartListBeanList.get(i).getPrice());
                    jsProductCamping.put("total_price", cartListBeanList.get(i).getTotal());
                    jsARRcamping.put(jsProductCamping);
                } else if (cartListBeanList.get(i).getIsRafting().equalsIgnoreCase(Itags.RAFTING)) {
                    jsProductRafting.put("name", cartListBeanList.get(i).getStr_name());
                    jsProductRafting.put("email", cartListBeanList.get(i).getStr_email());
                    jsProductRafting.put("phone", cartListBeanList.get(i).getStr_mobile());
                    jsProductRafting.put("message", cartListBeanList.get(i).getStr_message());
                    jsProductRafting.put("adventure_sport", cartListBeanList.get(i).getStr_service());
                    jsProductRafting.put("selectedtime", cartListBeanList.get(i).getStr_check_out());
                    jsProductRafting.put("start_pointsId", cartListBeanList.get(i).getStr_starting_point());
                    jsProductRafting.put("start_date", cartListBeanList.get(i).getStr_check_in());
                    jsProductRafting.put("cost_prseat", cartListBeanList.get(i).getPrice());
                    jsProductRafting.put("seat_req", cartListBeanList.get(i).getStr__qty());
                    jsProductRafting.put("total_price", cartListBeanList.get(i).getTotal());
                    jsARRrafting.put(jsProductRafting);
                }


            }
            JSONObject productinfo = new JSONObject();
            //   JSONObject raftingObject = new JSONObject();
            productinfo.put("camping", jsARRcamping);
            productinfo.put("rafting", jsARRrafting);
            Map<String, JSONObject> params = new HashMap<>();
            params.put("productinfo", productinfo);
            Log.e("Result ", "" + productinfo);
            CallCampBooking(productinfo);

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    private void CallCampBooking(final JSONObject productInfo) {

        prg.setMessage("Please Wait....");
        prg.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                if (new CheckConnectivity().isConnected(context)) {
                    try {
                        prg.dismiss();
                        Log.e("Response : prince ", response);
                        if (response != null) {
                            JSONObject jsData = new JSONObject(response);
                            String status = jsData.getString("status");
                            if (status.equalsIgnoreCase("1")) {

                                String msg = jsData.getString("msg");
                                String txnID = jsData.getString("order_id");
                                launchPayUMoneyFlow(SharedPref.getMobile1(), "Adventure", SharedPref.getFirstName(), SharedPref.getEmail(), txnID);


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
                prg.dismiss();
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();

                Log.e("Error :", error.toString());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<String, String>();
                header.put(Itags.Header, "ABC98XYZ53IJ61L");

                return header;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("action", UtilsUrl.Action_BOOKING);
                params.put("user_id", SharedPref.getUserID());
                params.put("firstname", SharedPref.getFirstName());
                params.put("amount", txt_total_pay.getText().toString());
                params.put("email", SharedPref.getEmail());
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("productinfo", productInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                params.put("productinfo", "" + jsonObject);

                Log.e("Param Response ", "" + params);
                return params;
            }
        };

        AppController1.getInstance().addToRequestQueue(stringRequest);


    }

    private void createList() {


        cartListBeanList = DBOperation.getCartList(this);

        Log.e("cart get from database", "databse size : " + cartListBeanList.size());

        cartListRecyclerAdapter = new CartListRecyclerAdapter(context, cartListBeanList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setAutoMeasureEnabled(true);
        recyclerview_cart.setLayoutManager(mLayoutManager);
        recyclerview_cart.setItemAnimator(new DefaultItemAnimator());
        recyclerview_cart.setAdapter(cartListRecyclerAdapter);
        int subtotal = 0;
        for (int i = 0; i < cartListBeanList.size(); i++) {
            subtotal = subtotal + Integer.parseInt(cartListBeanList.get(i).getTotal());

        }
        txt_total_pay.setText("" + subtotal);
        txt_sub_total.setText("" + subtotal);

    }

    private void createIds() {
        prg = new ProgressDialog(context);
        recyclerview_cart = findViewById(R.id.recyclerview_cart);
        txt_sub_total = findViewById(R.id.txt_sub_total);
        txt_total_pay = findViewById(R.id.txt_total_pay);
        btn_book_services = findViewById(R.id.btn_book_services);
        cartListBeanList = new ArrayList<>();


    }


    @Override
    public void onItemClick(int position) {
        Log.e("remove", "Remove it : " + cartListBeanList.get(position).getId());

        boolean isSuccess = DBOperation.deleteCartItem(CartList.this, cartListBeanList.get(position).getId());
        if (isSuccess) {
            createList();
        }

    }

    private void launchPayUMoneyFlow(String phone, String productName, String firstName, String email, String bookingID) {

        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();

        //Use this to set your custom text on result screen button
        payUmoneyConfig.setDoneButtonText("Adevnture Ride");

        //Use this to set your custom title for the activity
        payUmoneyConfig.setPayUmoneyActivityTitle("Adevnture Ride");

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();

        double amount = 0;
        try {
            amount = Double.parseDouble(txt_total_pay.getText().toString());

            // amount = Double.parseDouble("1");

        } catch (Exception e) {
            e.printStackTrace();
        }
        String txnId = bookingID;
        String udf1 = "";
        String udf2 = "";
        String udf3 = "";
        String udf4 = "";
        String udf5 = "";
        String udf6 = "";
        String udf7 = "";
        String udf8 = "";
        String udf9 = "";
        String udf10 = "";

        //   AppEnvironment appEnvironment = ((BaseApplication) getApplication()).getAppEnvironment();
        builder.setAmount(amount).setTxnId(txnId).setPhone(phone).setProductName(productName).setFirstName(firstName).setEmail(email).setsUrl("https://www.payumoney.com/mobileapp/payumoney/success.php").setfUrl("https://www.payumoney.com/mobileapp/payumoney/failure.php").setUdf1(udf1).setUdf2(udf2).setUdf3(udf3).setUdf4(udf4).setUdf5(udf5).setUdf6(udf6).setUdf7(udf7).setUdf8(udf8).setUdf9(udf9).setUdf10(udf10).setIsDebug(false).setKey(getResources().getString(R.string.PAYU_Merchant_key)).setMerchantId(getResources().getString(R.string.PAYU_Merchant_ID));

        try {
            mPaymentParams = builder.build();


            //   generateHashFromServer(mPaymentParams);
            mPaymentParams = calculateServerSideHashAndInitiatePayment1(mPaymentParams);

            PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, CartList.this, R.style.AppTheme_default, false);


        } catch (Exception e) {
            // some exception occurred
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            //payNowButton.setEnabled(true);
        }
    }

    private PayUmoneySdkInitializer.PaymentParam calculateServerSideHashAndInitiatePayment1(final PayUmoneySdkInitializer.PaymentParam paymentParam) {

        Log.e("Pay u AMount", "Pay u amount : " + PayUmoneyConstants.AMOUNT);
        StringBuilder stringBuilder = new StringBuilder();
        HashMap<String, String> params = paymentParam.getParams();
        stringBuilder.append(params.get(PayUmoneyConstants.KEY) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.TXNID) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.AMOUNT) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.PRODUCT_INFO) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.FIRSTNAME) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.EMAIL) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF1) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF2) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF3) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF4) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF5) + "||||||");

        //   AppEnvironment appEnvironment = ((BaseApplication) getApplication()).getAppEnvironment();
        stringBuilder.append(getResources().getString(R.string.PAYU_Merchant_salt));

        String hash = hashCal(stringBuilder.toString());
        Log.e("Pay u AMount", "Hash key " + hash);
        paymentParam.setMerchantHash(hash);

        return paymentParam;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result Code is -1 send from Payumoney activity
        Log.e("MainActivity", "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data != null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE);

            ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);

            // Check which object is non-null
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                    //Success Transaction
                    String payuResponse = transactionResponse.getPayuResponse();
                    Log.e("in Success", "in Success");
                    if (payuResponse != null) {
                        try {
                            JSONObject jsData = new JSONObject(payuResponse);
                            JSONObject jsResult = jsData.getJSONObject("result");

                            String transactionID = jsResult.getString("txnid");

                            BookingFaliure(jsResult.getString("txnid"), jsResult.getString("amount"),

                                    jsResult.getString("error_Message"), jsResult.getString("bankcode"), jsResult.getString("bank_ref_num"), jsResult.getString("payuMoneyId"), jsResult.getString("addedon"), jsResult.getString("status"));

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }


                } else {
                    //Failure Transaction
                    Log.e("in Faliure", "in Faliure");
                    String payuResponse = transactionResponse.getPayuResponse();
                    Log.e("in Success", "in Success");
                    if (payuResponse != null) {
                        try {
                            JSONObject jsData = new JSONObject(payuResponse);
                            JSONObject jsResult = jsData.getJSONObject("result");
                            Log.e("Faliure ", payuResponse);
                            //    String transactionID = jsResult.getString("txnid");

                            BookingFaliure(jsResult.getString("txnid"), jsResult.getString("amount"),

                                    jsResult.getString("error_Message"), jsResult.getString("bankcode"), jsResult.getString("bank_ref_num"), jsResult.getString("payuMoneyId"), jsResult.getString("addedon"), jsResult.getString("status"));


                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }


                }

                // Response from Payumoney
                String payuResponse = "";
                payuResponse = transactionResponse.getPayuResponse();
                Log.e("Pay u response : ", payuResponse);
                // Response from SURl and FURL
                String merchantResponse = transactionResponse.getTransactionDetails();
//                Log.e("Pay u response : ", merchantResponse);


/*
                new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setMessage("Payu's Data : " + payuResponse + "\n\n\n Merchant's Data: " + merchantResponse)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }).show();
*/

            } else if (resultModel != null && resultModel.getError() != null) {
                Log.e("prince", "Error response : " + resultModel.getError().getTransactionResponse());
            } else {
                Log.e("prince", "Both objects are null!");
            }
        }
    }

    private void BookingFaliure(final String txnid, final String amount, final String error_message, final String bankcode, final String bank_ref_num, final String payuMoneyId, String addedon, final String status) {

        prg.setMessage("Please Wait....");
        prg.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                if (new CheckConnectivity().isConnected(context)) {
                    try {
                        prg.dismiss();
                        Log.e("Response : prince ", response);
                        if (response != null) {
                            JSONObject jsData = new JSONObject(response);
                            String status = jsData.getString("status");
                            if (status.equalsIgnoreCase("1")) {

                                String msg = jsData.getString("msg");
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                DBOperation.deleteAll_Cart(CartList.this);
                                finish();

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
                prg.dismiss();
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();

                Log.e("Error :", error.toString());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<String, String>();
                header.put(Itags.Header, "ABC98XYZ53IJ61L");

                return header;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("action", UtilsUrl.Action_CONFIRM_BOOKING);
                params.put("user_id", SharedPref.getUserID());
                params.put("status", status);
                params.put("txnid", payuMoneyId);
                params.put("order_id", txnid);
                params.put("amount", amount);
                params.put("reson", error_message);
                params.put("bankcode", bankcode);
                params.put("bank_ref_num", bank_ref_num);
                params.put("payumoneyid", payuMoneyId);


                Log.e("Param Response ", "" + params);
                return params;
            }
        };
        AppController1.getInstance().addToRequestQueue(stringRequest);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_addMore) {


            Log.e("i m heree", "i m heree  cart size " + cartListBeanList.size());
            if (cartListBeanList.size() <= 0) {
                Log.e(TAG, "There is no value in Cart");

                Intent i = new Intent(CartList.this, HomePage.class);
                startActivity(i);

            } else if (cartListBeanList.size() >= 2) {
                Log.e(TAG, "Cart is Full");

                Toast.makeText(getApplicationContext(), "Cart is Full", Toast.LENGTH_LONG).show();
            } else {
                if (cartListBeanList.get(0).getIsRafting().equalsIgnoreCase(Itags.CAMPING)) {
                    Log.e(TAG, "Move to Rafting");

                    Intent i = new Intent(CartList.this, RaftingActivity.class);
                    startActivity(i);
                } else if (cartListBeanList.get(0).getIsRafting().equalsIgnoreCase(Itags.RAFTING)) {
                    Log.e(TAG, "Move to Camping");
                    Intent i = new Intent(CartList.this, CampMasterActivity.class);
                    i.putExtra("masterId", "3");
                    i.putExtra("masterTittle", "Camping");
                    i.putExtra("masterType", "2");
                    i.putExtra("masterImage", "\\/assets\\/images\\/serv_3_1510242628.jpg");

                    startActivity(i);
                } else {
                    Log.e(TAG, "Cart is Full");

                }
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
