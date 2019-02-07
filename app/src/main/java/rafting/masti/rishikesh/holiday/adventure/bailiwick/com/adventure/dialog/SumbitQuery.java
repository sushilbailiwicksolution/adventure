package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.dialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Session.SharedPref;

/**
 * Created by Prince on 29-12-2017.
 */

public class SumbitQuery extends Dialog {
    Context mContext;
    public TextView txt_booking_date;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    public EditText edt_message, edt_mobile, edt_email, edt_no_of_adult, edt_name;
    public Button btn_Apply, btn_cancel;

    public SumbitQuery(Activity con) {
        super(con);
        this.mContext = con;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.cst_dialog_submit_query_form);
        createIds();
        setDetail();
        DateClickListner();
        clickListner();
    }

    private void clickListner() {
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void DateClickListner() {

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);


        Calendar newCalendar = Calendar.getInstance();

        toDatePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                txt_booking_date.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        txt_booking_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toDatePickerDialog.show();
            }
        });

    }

    private void setDetail() {
        edt_name.setText(SharedPref.getFirstName() + " " + SharedPref.getLastName());
        edt_mobile.setText(SharedPref.getMobile1());
        edt_email.setText(SharedPref.getEmail());
    }

    private void createIds() {
        txt_booking_date = (TextView) findViewById(R.id.txt_booking_date);
        edt_message = (EditText) findViewById(R.id.edt_message);
        edt_mobile = (EditText) findViewById(R.id.edt_mobile);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_no_of_adult = (EditText) findViewById(R.id.edt_no_of_adult);
        edt_name = (EditText) findViewById(R.id.edt_name);
        btn_Apply = (Button) findViewById(R.id.btn_Apply);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

    }
}
