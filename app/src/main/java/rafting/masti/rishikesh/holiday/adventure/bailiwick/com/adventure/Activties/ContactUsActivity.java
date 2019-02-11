package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Activties;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Session.SharedPref;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener {

   // private EditText etName, etEmail, etMobile, etQuery;
  //  private Button btnSendQuery;
    private TextView mCallForOffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        getUiObject();
    }

    private void getUiObject() {
      /*  etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etMobile = findViewById(R.id.etMobile);
        etQuery = findViewById(R.id.etQuery);
        btnSendQuery = findViewById(R.id.btnSendQuery);*/
        mCallForOffer = findViewById(R.id.tv_call);

     //   btnSendQuery.setOnClickListener(this);
        mCallForOffer.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
          /*  case R.id.btnSendQuery:
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String mobile = etMobile.getText().toString();
                String query = etQuery.getText().toString();

                if (name.isEmpty()) {
                    etName.setError("Please Fill Your Name");
                } else if (email.isEmpty()) {
                    etEmail.setError("Please Fill Your Email");
                } else if (mobile.isEmpty()) {
                    etMobile.setError("Please Fill Your Mobile");
                } else if (query.isEmpty()) {
                    etQuery.setError("Please Fill Your Query");
                } else if (mobile.length() != 10) {
                    etMobile.setError("Number of digits must be 10");
                } else {
                    sendQuery(name, email, mobile, query);
                }

                break;*/

            case R.id.tv_call:
                String number = mCallForOffer.getText().toString();

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+number));

                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{android.Manifest.permission.CALL_PHONE},   //request specific permission from user
                            10);
                    return;
                }else{
                    try {
                        startActivity(callIntent);
                    }catch (ActivityNotFoundException e){
                        Toast.makeText(getApplicationContext(),"Activity is not founded", Toast.LENGTH_SHORT).show();
                    }
                }

                break;

        }
    }

    private void sendQuery(String name, String email, String mobile, String query) {
        Intent mailIntent = new Intent(Intent.ACTION_SEND);
        mailIntent.setData(Uri.parse("mailto:adventureservice@gmail.com"));
        mailIntent.setType("text/plain");
        mailIntent.putExtra(Intent.EXTRA_SUBJECT,"Query "+ SharedPref.getFirstName());
        mailIntent.putExtra(Intent.EXTRA_EMAIL,"adventureservice@gmail.com");
        String message = "Name : "+name+"\n"+"Email : "+email+"\n"+"Mobile : "+mobile+"\n"+"Query : "+query;
        mailIntent.putExtra(Intent.EXTRA_TEXT,message);
        startActivity(Intent.createChooser(mailIntent, "Choose an Email client :"));
    }
}
