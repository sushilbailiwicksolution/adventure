package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.biking;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Activties.CartList;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Activties.HomePage;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Adapter.BikeRecyclerAdapter;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.database.DbOperation;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.interfaces.BikeBookInterface;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model.BikeBookBean;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Commons;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Itags;

public class BikingConfirmationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, BikeBookInterface {

    EditText edtName, edtEmail, edtMobile;
    TextView fromDate, toDate, txtPrice, bikeCount, totalPrice, tvDaysCount;
    Spinner spinnerBikeType;
    RecyclerView rvBikeType;
    Button btnBookBike;
    String action, selectedNoOfBike, stateId, stateName, serviceId, title;
    List<BikeBookBean> bikeBookBeanList;
    BikeRecyclerAdapter bikeRecyclerAdapter;
    Toolbar toolbarBikingCnf;
    DatePickerDialog fromDateDialog, toDateDialog;
    SimpleDateFormat dateFormatter;
    LinearLayout llPriceLayout,llFinalPrice;
    List<String> bikeList;
    int bike_count, total_price,no_of_days;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.biking_layout);
        bikeBookBeanList = new ArrayList<>();
        bikeList = new ArrayList<>();
        getDataFromPrevious();
        getUiObject();
       // addBikeType();
        setToolBar();
        setDateListener();
        //bikeSpinner();
    }

    private void bikeSpinner() {
        bikeList.add("Yamaha");
        bikeList.add("Pulsar");
        bikeList.add("Hero");
        bikeList.add("Honda");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, bikeList);
        spinnerBikeType.setAdapter(arrayAdapter);
        addBikeType();
    }

    private void setDateListener() {
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Calendar newCalendar = Calendar.getInstance();

        fromDateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                Log.e("date ", "" + year + " " + (monthOfYear + 1) + " " + dayOfMonth);
                fromDate.setText(dateFormatter.format(newDate.getTime()));
                String lastDate;
                try {
                    DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String inputDateStr = fromDate.getText().toString();

                    Log.e("Input Date => ", inputDateStr);
                    Date date_check_in = inputFormat.parse(inputDateStr);
                    lastDate = outputFormat.format(date_check_in);
                    Log.e("Last Date => ", lastDate);
                    fromDate.setText(inputDateStr);

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
                toDate.setText(dateFormatter.format(newDate.getTime()));
                String lastDate = "";
                try {
                    DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                    //date check in
                    String inputDateStr = fromDate.getText().toString();
                    Date date_check_in = inputFormat.parse(inputDateStr);
                    //date check out
                    String outputDateStr = toDate.getText().toString();
                    Date date_check_out = inputFormat.parse(outputDateStr);

                    lastDate = outputFormat.format(date_check_in);
                    toDate.setText(outputDateStr);

                    if (!fromDate.getText().toString().equalsIgnoreCase("")) {
                        if (!toDate.getText().toString().equalsIgnoreCase("")) {
                            //getNoOfBikes(fromDate.getText().toString(), toDate.getText().toString());
                            if (date_check_in.before(date_check_out)) {
                                bikeSpinner();
                                llPriceLayout.setVisibility(View.VISIBLE);
                                spinnerBikeType.setVisibility(View.VISIBLE);
                                llFinalPrice.setVisibility(View.VISIBLE);
                                rvBikeType.setVisibility(View.VISIBLE);
                                getNoOfDays(fromDate.getText().toString(), toDate.getText().toString());
                            } else {
                                Toast.makeText(BikingConfirmationActivity.this, "Check-in Date should before Check-Out", Toast.LENGTH_SHORT).show();
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
                tvDaysCount.setText(String.valueOf(convert));
                /*String price = String.valueOf(convert * Integer.parseInt(selectedNoOfBike.trim()) * Integer.parseInt(txtPrice.getText().toString().trim()));
                totalPrice.setText(price);*/
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

    private void getDataFromPrevious() {
        action = Objects.requireNonNull(getIntent().getExtras()).getString("action");
        stateId = Objects.requireNonNull(getIntent().getExtras()).getString("state_id");
        stateName = Objects.requireNonNull(getIntent().getExtras()).getString("state_name");
        serviceId = Objects.requireNonNull(getIntent().getExtras()).getString("service_id");
        title = Objects.requireNonNull(getIntent().getExtras()).getString("title");
        Log.e("Action => ", action);
    }

    private void setToolBar() {
        setSupportActionBar(toolbarBikingCnf);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbarBikingCnf.setTitle("Bike Booking");
        toolbarBikingCnf.setNavigationIcon(getResources().getDrawable(R.drawable.clear_black));
        toolbarBikingCnf.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getUiObject() {
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtMobile = findViewById(R.id.edtMobile);
        fromDate = findViewById(R.id.tvFromDate);
        toDate = findViewById(R.id.tvToDate);
        txtPrice = findViewById(R.id.txtPrice);
        spinnerBikeType = findViewById(R.id.spinnerBikeType);
        bikeCount = findViewById(R.id.bikeCount);
        totalPrice = findViewById(R.id.totalPrice);
        rvBikeType = findViewById(R.id.rvBikeType);
        btnBookBike = findViewById(R.id.btnBookBike);
        toolbarBikingCnf = findViewById(R.id.toolbar_main);
        tvDaysCount = findViewById(R.id.tvDaysCount);
        llPriceLayout = findViewById(R.id.llPriceLayout);
        llFinalPrice = findViewById(R.id.llFinalPrice);

        setListener();

        String name = SharedPref.getFirstName() + " " + SharedPref.getLastName();
        edtName.setText(name);
        edtName.setEnabled(false);
        edtEmail.setText(SharedPref.getEmail());
        edtEmail.setEnabled(false);
        edtMobile.setText(SharedPref.getMobile1());
        edtMobile.setEnabled(false);

    }

    private void addBikeType() {

        bikeRecyclerAdapter = new BikeRecyclerAdapter(this, bikeBookBeanList, this);
        rvBikeType.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvBikeType.setItemAnimator(new DefaultItemAnimator());
        rvBikeType.setAdapter(bikeRecyclerAdapter);
        rvBikeType.setRecycledViewPool(new RecyclerView.RecycledViewPool());
    }

    private void setListener() {
        spinnerBikeType.setOnItemSelectedListener(this);
        fromDate.setOnClickListener(this);
        toDate.setOnClickListener(this);
        btnBookBike.setOnClickListener(this);
    }

    private void validateInput(String action) {

        String name = edtName.getText().toString();
        String no_of_days = getNoOfDays(fromDate.getText().toString().trim(), toDate.getText().toString().trim());
        String email = edtEmail.getText().toString();
        String mobile = edtMobile.getText().toString();
        String lastAmt = totalPrice.getText().toString();

        if (name.equalsIgnoreCase("")) {
            edtName.setError("Name is Required");
        } else if (fromDate.getText().toString().equalsIgnoreCase("From")) {
            Toast.makeText(this, "Please select from Date", Toast.LENGTH_SHORT).show();
        } else if (toDate.getText().toString().equalsIgnoreCase("To")) {
            Toast.makeText(this, "Please select to Date", Toast.LENGTH_SHORT).show();
        } else if (email.equalsIgnoreCase("")) {
            edtEmail.setError("Please Fill Email Id");
        } else if (!Commons.isValidEmail(email)) {
            edtEmail.setError("Invalid Email");
        } else if (mobile.equalsIgnoreCase("")) {
            edtMobile.setError("Please Fill Mobile No");
        } else if (!Commons.isValidMobile(mobile)) {
            edtMobile.setError("Invalid Mobile");
        } else {
            DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            Date curr_date = Calendar.getInstance().getTime();
            String bookDate = inputFormat.format(curr_date);

            Date date_check_in = null, date_check_out = null;
            boolean isSuccess = false;

            String inputDateStr = fromDate.getText().toString();
            String outputDateStr = toDate.getText().toString();
            try {
                date_check_in = inputFormat.parse(inputDateStr);
                date_check_out = inputFormat.parse(outputDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            int entryExist = DbOperation.getRaftCount(this, serviceId);

            if (entryExist > 0) {
                DbOperation.deleteCartItem(this, serviceId);
                Log.e("cycle count", entryExist + " id deleted");
            }

            if (Objects.requireNonNull(date_check_in).before(date_check_out)) {
                isSuccess = DbOperation.insertCampaningCart(BikingConfirmationActivity.this,
                        serviceId, title, "biking", fromDate.getText().toString(), toDate.getText().toString(), no_of_days,
                        "", "", "", bookDate, bikeCount.getText().toString(), txtPrice.getText().toString(),
                        lastAmt, "", Itags.BIKING, "",
                        name, mobile, "", email);
            }else{
                Toast.makeText(this, "Check-in Date should before Check-Out", Toast.LENGTH_SHORT).show();
            }

            if (isSuccess) {
                Toast.makeText(BikingConfirmationActivity.this, "Saved in Database", Toast.LENGTH_SHORT).show();
                //DB_Function.ExportDatabasee(getActivity(), Database_Utils.DB_NAME);
                //int cartCount = DbOperation.getRaftCount(CyclingActivity.this, service_id);
                int cartCount = Objects.requireNonNull(DbOperation.getCartList(BikingConfirmationActivity.this)).size();

                Log.e("Cart Count => ", "" + cartCount);
                SharedPref.setCartCount(String.valueOf(cartCount));
                SharedPref.saveMobile1(mobile);
                if (action.equalsIgnoreCase("book_now")) {
                    Intent i = new Intent(BikingConfirmationActivity.this, CartList.class);
                    startActivity(i);
                    finish();
                } else if (action.equalsIgnoreCase("cart")) {
                    Intent homeIntent = new Intent(BikingConfirmationActivity.this, HomePage.class);
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedBike = (String) parent.getSelectedItem();
        Log.e("Selected Bike => ", selectedBike);
        boolean isSame = false;
        if (bikeBookBeanList.size() > 0) {
            Log.e("Spinner => ", "inside if");
            for (BikeBookBean bikeBookBean : bikeBookBeanList) {
                if (bikeBookBean.getBikeName().equalsIgnoreCase(selectedBike)) {
                    isSame = true;
                    Toast.makeText(this, "Select Another Bike", Toast.LENGTH_SHORT).show();
                }
            }
            if (!isSame) {
                Log.e("Bike name => ", (String) parent.getSelectedItem());
                bikeBookBeanList.add(new BikeBookBean("2", (String) parent.getSelectedItem(), "300", "10", "1", "0"));
                bikeRecyclerAdapter.notifyDataSetChanged();

                bike_count = 0;
                total_price = 0;
                for (BikeBookBean bikeBookBean : bikeBookBeanList) {
                    Log.e("Bike quantity => ", bikeBookBean.getQuantity());
                    Log.e("Bike price => ", bikeBookBean.getBikePrice());
                    total_price += Integer.parseInt(bikeBookBean.getQuantity()) * Integer.parseInt(bikeBookBean.getBikePrice());
                    bike_count += Integer.parseInt(bikeBookBean.getQuantity());
                    bikeBookBean.setTotalPrice(String.valueOf(total_price));
                    total_price = Integer.parseInt(bikeBookBean.getTotalPrice());
                }
                bikeCount.setText(String.valueOf(bike_count));
                no_of_days = Integer.parseInt(tvDaysCount.getText().toString());
                total_price = total_price*no_of_days;
                totalPrice.setText(String.valueOf(total_price));
            }
        } else {
            Log.e("Spinner => ", "inside else");
            bikeBookBeanList.add(new BikeBookBean("1", selectedBike, "200", "10", "1", "0"));
            bikeRecyclerAdapter.notifyDataSetChanged();
            bike_count = 1;
            int total_price = bike_count * 200;
            bikeCount.setText(String.valueOf(bike_count));

            no_of_days = Integer.parseInt(tvDaysCount.getText().toString());
            total_price = total_price*no_of_days;
            totalPrice.setText(String.valueOf(total_price));
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBookBike:
                validateInput(action);
                break;

            case R.id.tvFromDate:
                fromDateDialog.show();
                break;

            case R.id.tvToDate:
                toDateDialog.show();
                break;
        }
    }

   /* private int totalBikeCount(int position,String quantity,String price){

    }*/

    @Override
    public void incrementClick(int position, String quantity, String price) {
        Log.e("position => ", "" + position);
        Log.e("quantity => ", "" + quantity);
        Log.e("price => ", "" + price);
        bike_count = 0;
        total_price = 0;
        for (BikeBookBean bikeBookBean : bikeBookBeanList) {
            bike_count += Integer.parseInt(bikeBookBean.getQuantity());
            total_price += Integer.parseInt(bikeBookBean.getTotalPrice());
        }

        bikeCount.setText(String.valueOf(bike_count));
        no_of_days = Integer.parseInt(tvDaysCount.getText().toString());
        total_price = total_price*no_of_days;
        totalPrice.setText(String.valueOf(total_price));
    }

    @Override
    public void decrementClick(int position, String quantity, String price) {

        Log.e("position => ", "" + position);
        Log.e("quantity => ", "" + quantity);
        Log.e("price => ", "" + price);

        bike_count = 0;
        total_price = 0;
        for (BikeBookBean bikeBookBean : bikeBookBeanList) {
            bike_count += Integer.parseInt(bikeBookBean.getQuantity());
            total_price += Integer.parseInt(bikeBookBean.getTotalPrice());
        }

        bikeCount.setText(String.valueOf(bike_count));
        no_of_days = Integer.parseInt(tvDaysCount.getText().toString());
        total_price = total_price*no_of_days;
        totalPrice.setText(String.valueOf(total_price));
    }

    @Override
    public void addNewRow(int position) {
        bikeRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteRow(int position) {

        if (bikeBookBeanList.size() > 1) {
            bikeBookBeanList.remove(position);
            bikeRecyclerAdapter = new BikeRecyclerAdapter(this, bikeBookBeanList, this);
            rvBikeType.setAdapter(bikeRecyclerAdapter);
        }

        bike_count = 0;
        total_price = 0;
        for (BikeBookBean bikeBookBean : bikeBookBeanList) {
            bike_count += Integer.parseInt(bikeBookBean.getQuantity());
            total_price += Integer.parseInt(bikeBookBean.getTotalPrice());
        }

        bikeCount.setText(String.valueOf(bike_count));
        no_of_days = Integer.parseInt(tvDaysCount.getText().toString());
        total_price = total_price*no_of_days;
        totalPrice.setText(String.valueOf(total_price));

    }
}
