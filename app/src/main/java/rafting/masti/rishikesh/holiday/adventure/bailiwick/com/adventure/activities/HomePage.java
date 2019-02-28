package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.activities;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.rom4ek.arcnavigationview.ArcNavigationView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.app.AppController;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.firebase.MyFirebaseInstanceIDService;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.apputils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.loginRegister.LoginRegisterActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.loginRegister.ServiceTabNew;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.RootActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.terms.TermsActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Const;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Itags;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Tracer;

/**
 * Created by Prince on 14-11-2017.
 */

public class HomePage extends RootActivity implements ArcNavigationView.OnNavigationItemSelectedListener {

    private Context context;
    ProgressDialog prg;
    String TAG;

    private DrawerLayout drawer;
    Toolbar toolbar;
    CoordinatorLayout cordinate_layout;
    //
// Navigation item
    TextView tvUserName, tvVersionName;
    ImageView ivProfile;
    TextView txt_username;
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    EditText etName, etTitle, etMobile, etQuery;
    Button mSendQuery;
    LinearLayout llProfile;

    TextView textViewCount;
    int count;
    View badge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        context = HomePage.this;
        TAG = context.getPackageName();

        createIDs();
        setToolbar();
        setCollapseToolabr();
        setTabview();
        Toast.makeText(getApplicationContext(), SharedPref.getFirstName(), Toast.LENGTH_LONG).show();

    }


    private void setTabview() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        // Set Tab
        TabLayout tabLayout = findViewById(R.id.tabs);


        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    private void setCollapseToolabr() {
        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        String name = SharedPref.getFirstName() + " " + SharedPref.getLastName();
        txt_username.setText(name);

        collapsingToolbarLayout.setTitle(" ");
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("Adventure");
                    collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.collapsingToolbarLayoutTitleColor);
                    collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsingToolbarLayoutTitleColor);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

    }

    private void setToolbar() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.home_menu, menu);

        badge = menu.findItem(R.id.cart_item).getActionView();
        textViewCount = badge.findViewById(R.id.txtCount);
        //count = Integer.parseInt(textViewCount.getText().toString());
        Log.e("Cart count", "" + SharedPref.getCartCount());

        if (!SharedPref.getCartCount().isEmpty() && !SharedPref.getCartCount().equals("0")) {
            Log.e("Cart count", "" + SharedPref.getCartCount());
            count = Integer.parseInt(SharedPref.getCartCount());
            updateCountBadge(count);
        } else {
            Log.e("Cart count", "" + SharedPref.getCartCount());
            updateCountBadge(count);
        }

       /* textViewCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Badge Count=> ",""+count);
                updateCountBadge(count++);
            }
        });*/

        badge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Some Action", "Here Clicked");
                Intent intent = new Intent(HomePage.this, CartList.class);
                startActivity(intent);
            }
        });

        return true;
    }

    private void updateCountBadge(int new_count) {
        count = new_count;
        if (count < 0) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (count == 0) {
                    textViewCount.setVisibility(View.GONE);
                } else {
                    textViewCount.setVisibility(View.VISIBLE);
                    textViewCount.setText(String.valueOf(count));
                }
            }
        });
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart_item:
                Intent intent = new Intent(this,CartList.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    private void createIDs() {
        prg = new ProgressDialog(context);

        cordinate_layout = findViewById(R.id.cordinate_layout);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
// Navigation item
        final ArcNavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerLayout = navigationView.getHeaderView(0);
        tvUserName = headerLayout.findViewById(R.id.tvUserName);
        tvVersionName = headerLayout.findViewById(R.id.tvVersionName);
        ivProfile = headerLayout.findViewById(R.id.ivProfile);
        tvUserName.setText(SharedPref.getFirstName());
        txt_username = findViewById(R.id.txt_username);
        String name = SharedPref.getFirstName() + " " + SharedPref.getLastName();
        txt_username.setText(name);
        tvVersionName.setText(SharedPref.getEmail());
        llProfile = headerLayout.findViewById(R.id.llProfile);
        llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(HomePage.this, ProfileActivity.class);
                startActivity(profileIntent);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      /*  int id = item.getItemId();
        if (id == R.id.nav_home) {
            drawer.closeDrawers();
        } else if (id == R.id.nav_logout) {
            Logout();
        } else if (id == R.id.nav_profile) {
            Intent i = new Intent(context, ProfileActivity.class);
            startActivity(i);
            drawer.closeDrawers();
        } else if (id == R.id.Query) {
            Snackbar.make(cordinate_layout, "its Work", Snackbar.LENGTH_SHORT).show();
        }else if (id == R.id.nav_contact_us){

        }else if (id == R.id.nav_share){

        }else if (id == R.id.nav_rate_us){

        }*/

        switch (item.getItemId()) {
            case R.id.nav_home:
                drawer.closeDrawers();
                return true;

            case R.id.nav_logout:
                drawer.closeDrawers();
                Logout();
                return true;

            case R.id.nav_profile:
                Intent i = new Intent(context, ProfileActivity.class);
                startActivity(i);
                drawer.closeDrawers();
                return true;

            case R.id.Query:
                drawer.closeDrawers();
                sendQueryDialog();
                return true;

            case R.id.nav_contact_us:
                drawer.closeDrawers();
                Intent intent = new Intent(context, ContactUsActivity.class);
                startActivity(intent);
                return true;

            case R.id.nav_share:
                drawer.closeDrawers();
                shareApp();
                return true;

            case R.id.nav_rate_us:
                drawer.closeDrawers();
                rateUs();
                return true;

            case R.id.menu_order:
                drawer.closeDrawers();
                Intent inOrder = new Intent(context, OrderActivity.class);
                startActivity(inOrder);
                return true;

            case R.id.menu_enquery:
                drawer.closeDrawers();
                Intent inEnqry = new Intent(context, EnquiryActivity.class);
                startActivity(inEnqry);
                return true;

            case R.id.nav_about_us:
                drawer.closeDrawers();
                Intent aboutIntent = new Intent(context, TermsActivity.class);
                aboutIntent.putExtra("drawer_from", "about_us");
                startActivity(aboutIntent);
                return true;

            case R.id.nav_terms_conditions:
                drawer.closeDrawers();
                Intent termsIntent = new Intent(context, TermsActivity.class);
                termsIntent.putExtra("drawer_from", "terms_and_conditions");
                startActivity(termsIntent);
                return true;

            case R.id.nav_privacy_policy:
                drawer.closeDrawers();
                Intent privacyIntent = new Intent(context, TermsActivity.class);
                privacyIntent.putExtra("drawer_from", "privacy_policy");
                startActivity(privacyIntent);
                return true;

          /*   case R.id.menu_payment:
                drawer.closeDrawers();
                Intent inPAy = new Intent(context, PaymentQueryActivity.class);
                startActivity(inPAy);
                return true;*/

            default:
                drawer.closeDrawers();
                return super.onOptionsItemSelected(item);

        }
        /*else  if(id==R.id.nav_cart){
            Intent i = new Intent(HomePage.this, CartList.class);
            startActivity(i);
        }*/
        //return true;
    }

    private void Logout() {
        new MaterialDialog.Builder(this)
                .iconRes(R.drawable.ic_launcher_background)
                .limitIconToDefaultSize()
                .title("Logout!")
                .contentColor(getResources().getColor(R.color.gray))

                .titleColor(getResources().getColor(R.color.black))
                .backgroundColor(getResources().getColor(R.color.white))
                .content("Do you want to logout?")
                .positiveText("Cancel")
                .negativeText("Yes")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        SharedPref.ClearAll();
                        Intent i = new Intent(HomePage.this, LoginRegisterActivity.class);
                        startActivity(i);
                        finish();

                    }
                })
                .show();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ServiceTabNew();
               /* case 1:
                    DestinationTab destinationTab = new DestinationTab();
                    return destinationTab;*/
            }
            return null;
        }

        @Override
        public int getCount() {
            return 1;
        }

    }

    @Override
    protected void onResume() {
        getUserDetail(getDeviceDetail(), getFcmkey());

        super.onResume();
    }

    private String getFcmkey() {
        MyFirebaseInstanceIDService myfcm = new MyFirebaseInstanceIDService();
        String fcmtoken = myfcm.getFcm();
        Log.e("fcm id", fcmtoken);
        if (fcmtoken.isEmpty()) {
            getFcmkey();
            Log.e("Fcm repeat", "empty : " + fcmtoken);
        }
        return fcmtoken;
        //   DB_Function.SavetextFile(Booth_record.this, fcmtoken);
    }

    private String getDeviceDetail() {
        return Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    private void getUserDetail(final String deviceid, final String fcm) {

        if (new CheckConnectivity().isConnected(context)) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        Log.e("Response : prince ", response);
                        if (response != null) {
                            JSONObject jsData = new JSONObject(response);
                            String status = jsData.getString("status");
                            if (status.equalsIgnoreCase("1")) {
                                saveDetail(jsData);
                                Glide.with(context).load(SharedPref.getprofileURL()).into(ivProfile);
                            } else {
                                String msg = jsData.getString("msg");
                                Tracer.errorLog("Message",msg);
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
                    params.put("action", UtilsUrl.Action_userDetail);
                    params.put("user_id", SharedPref.getUserID());
                    params.put("fcm", fcm);
                    params.put("device_id", deviceid);
                    Log.e("Request Params ", "" + params);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(context, "Check Your connetion", Toast.LENGTH_LONG).show();
        }

    }

    private void saveDetail(JSONObject jsData) {
        try {
            JSONObject data = jsData.getJSONObject("data");

            SharedPref.saveUserTYPE(data.getString("user_type"));
            SharedPref.saveFirstName(data.getString("fname"));
            SharedPref.saveLastName(data.getString("lname"));
            SharedPref.saveMobile1(data.getString("mobile_no"));
            SharedPref.saveEmail(data.getString("email"));
            if (!data.getString("profile_pic").equalsIgnoreCase("")) {
                SharedPref.saveprofileURL(data.getString("profile_pic"));
            }
            SharedPref.savegender(data.getString("gender"));
            SharedPref.saveAddress(data.getString("address"));
            SharedPref.saveCity(data.getString("city"));

            String name = SharedPref.getFirstName() + " " + SharedPref.getLastName();
            txt_username.setText(name);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void shareApp() {
        try {

            String package_name = getApplicationContext().getPackageName();

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            String sAux = "\nLet me recommend you this application\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=" + package_name + "\n\n";
            intent.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(intent, "Share Via"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void rateUs() {

        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent myAppIntent = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppIntent);

        } catch (ActivityNotFoundException e) {
            Snackbar.make(cordinate_layout, "Unable to find..", BaseTransientBottomBar.LENGTH_SHORT);
        }
    }

    private void sendQueryDialog() {

        View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_query_dialog, null, false);
        final AlertDialog alertDiaolg = new AlertDialog.Builder(this).create();
        alertDiaolg.setView(dialogView);
        etName = dialogView.findViewById(R.id.etName);
        etTitle = dialogView.findViewById(R.id.etTitle);
        etMobile = dialogView.findViewById(R.id.etMobile);
        etQuery = dialogView.findViewById(R.id.etQuery);
        mSendQuery = dialogView.findViewById(R.id.btnSendQuery);

        dialogView.findViewById(R.id.btnSendQuery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etName.getText().toString();
                String mobile = etMobile.getText().toString();
                String title = etTitle.getText().toString();
                String description = etQuery.getText().toString();

                validate(name, mobile, title, description);
                alertDiaolg.dismiss();
            }
        });

        alertDiaolg.show();

    }

    private void validate(String name, String mobile, String title, String description) {

        if (name.isEmpty()) {
            etName.setError("Name cannot be blank");
        } else if (mobile.isEmpty()) {
            etMobile.setError("Mobile cannot be empty");
        } else if (title.isEmpty()) {
            etTitle.setError("Enter title please");
        } else if (description.isEmpty()) {
            etQuery.setError("Enter your description please");
        } else if (mobile.length() != 10) {
            etMobile.setError("Length of mobile must be 10");
        } else {
            sendQuery(name, mobile, title, description);
        }

    }

    private void sendQuery(String name, String mobile, String title, String description) {

    }


}
