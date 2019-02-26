package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.enquiryforms;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import org.angmarch.views.NiceSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;

/**
 * Created by Prince on 28-11-2017.
 */

public class EnquiryForm1 extends Dialog {
    NiceSpinner nice_spinner_timing, nice_spinner_seat_requried, nice_spinner_starting_point;
    private EditText fromDateEtxt;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    Activity mContext;

    public EnquiryForm1(Activity con) {
        super(con);
        this.mContext = con;
        //  requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.form1);

        createIds();
            SetDateListner();

    }

    private void SetDateListner() {
        fromDateEtxt.setInputType(InputType.TYPE_NULL);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


        Calendar newCalendar = Calendar.getInstance();

        toDatePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        fromDateEtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toDatePickerDialog.show();
            }
        });
    }

    private void createIds() {
        fromDateEtxt = (EditText) findViewById(R.id.fromDateEtxt);
        nice_spinner_timing = (NiceSpinner) findViewById(R.id.nice_spinner_timing);
        nice_spinner_starting_point = (NiceSpinner) findViewById(R.id.nice_spinner_starting_point);
        nice_spinner_seat_requried = (NiceSpinner) findViewById(R.id.nice_spinner_seat_requried);
        List<String> dataset = new LinkedList<>(Arrays.asList("Select Time", "08:00", "08:00", "08:00", "08:00", "08:00"));
        List<String> datasetDestination = new LinkedList<>(Arrays.asList("Select Starting Point", "ShivPuri", "Brahmpuri", "Merinei Drive", "Kaudiyala", "08:00"));
        ArrayList<String> Seats = new ArrayList<>();
        Seats.add("Select Seats");
        for (int i = 0; i < 10; i++) {
            Seats.add("" + i);
        }
        nice_spinner_seat_requried.attachDataSource(Seats);
        nice_spinner_starting_point.attachDataSource(datasetDestination);
        nice_spinner_timing.attachDataSource(dataset);
    }

}
