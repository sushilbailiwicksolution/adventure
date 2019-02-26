package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Activties;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.App.AppController1;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.apputils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Firebase.MyFirebaseInstanceIDService;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.CheckConnectivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.support.RootActivity;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Commons;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Const;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Itags;

/**
 * Created by Prince on 22-11-2017.
 */

public class ProfileActivity extends RootActivity {

    private EditText edit_text_first_name_signup, edit_text_last_name_signup, edit_text_email_signup,
            edit_text_mobile_signup, edit_Address;
    private Button btn_save_profile;
    private ImageView img_profile;
    // Image Setup
    Bitmap bitmap;
    String myBase64Image;
    Uri mCropImageUri;

    private Context context;
    private String TAG;
    ProgressDialog prg;
    private String USER_ID;

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        context = ProfileActivity.this;
        TAG = this.getClass().toString();

        createIDs();
        setData();
        clickListner();
    }

    private void clickListner() {
        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile_pic_update();
            }
        });
        btn_save_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fName, lName, email, mobile, address;

                fName = edit_text_first_name_signup.getText().toString().trim();
                lName = edit_text_last_name_signup.getText().toString().trim();
                email = edit_text_email_signup.getText().toString().trim();
                mobile = edit_text_mobile_signup.getText().toString().trim();
                address = edit_Address.getText().toString().trim();

                if (fName.equalsIgnoreCase("")) {
                    edit_text_first_name_signup.setError("Required");
                } else if (lName.equalsIgnoreCase("")) {
                    edit_text_last_name_signup.setError("Required");
                } else if (email.equalsIgnoreCase("")) {
                    edit_text_email_signup.setError("Required");
                } else if (!emailValidator(email)) {
                    edit_text_email_signup.setError("Invalid Email");
                } else if (email.equalsIgnoreCase("")) {
                    edit_text_mobile_signup.setError("Required");
                } else if (!Commons.isValidMobile(mobile)) {
                    edit_text_mobile_signup.setError("Invalid Mobile Number");
                } else if (address.equalsIgnoreCase("")) {
                    edit_Address.setError("Address Field is Required");
                } else {
                    updateUserDetail(fName, lName, email, mobile, address, USER_ID);
                }
            }
        });
    }

    private void updateUserDetail(final String fName, final String lName, String email, final String mobile, final String address, String user_id) {

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
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                                getUserDetail(getDeviceDetail(), getFcmkey());
                            } else {
                                String msg = jsData.getString("msg");
                            }
                        } else {
                            Toast.makeText(context, "Invalid Response !!!", Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        prg.dismiss();
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

                    params.put(Const.KEY_ACTION, UtilsUrl.Action_UpdateProfile);
                    params.put(Const.KEY_USER_ID, SharedPref.getUserID());
                    params.put(Const.KEY_FNAME, fName);
                    params.put(Const.KEY_LNAME, lName);
                    params.put(Const.KEY_MOBILE, mobile);
                    params.put(Const.KEY_ADDRESS, address);

                    Log.e("Param Response ", "" + params);
                    return params;
                }
            };
            AppController1.getInstance().addToRequestQueue(stringRequest);
        } else {
            Toast.makeText(context, "Check Your connetion", Toast.LENGTH_LONG).show();
        }

    }

    private void setData() {

        USER_ID = SharedPref.getUserID();
        edit_text_first_name_signup.setText(SharedPref.getFirstName());
        edit_text_last_name_signup.setText(SharedPref.getLastName());
        edit_text_email_signup.setText(SharedPref.getEmail());
        edit_text_mobile_signup.setText(SharedPref.getMobile1());
        edit_Address.setText(SharedPref.getAddress());
        String profile_url = SharedPref.getprofileURL();
        Log.e("Image Url", profile_url);

        if (!profile_url.equalsIgnoreCase("")) {
            Glide.with(context).load(UtilsUrl.IMAGEBASE_URL + SharedPref.getprofileURL()).error(R.drawable.icon_profile_9).into(img_profile);
            Log.e("Full Path : - ", UtilsUrl.IMAGEBASE_URL + SharedPref.getprofileURL());
            // Glide.with(context).load("http://indianadventurepackages.com/profile_pic/profile_pic_15369101297390750.jpg").error(R.drawable.icon_profile_9).into(img_profile);
        }
    }

    private void createIDs() {
        prg = new ProgressDialog(context);
        edit_text_first_name_signup = findViewById(R.id.edit_text_first_name_signup);
        edit_text_last_name_signup = findViewById(R.id.edit_text_last_name_signup);
        edit_text_email_signup = findViewById(R.id.edit_text_email_signup);
        edit_text_mobile_signup = findViewById(R.id.edit_text_mobile_signup);
        edit_Address = findViewById(R.id.edit_Address);
        btn_save_profile = findViewById(R.id.btn_save_profile);
        img_profile = findViewById(R.id.img_profile);

    }

    private void getUserDetail(final String deviceid, final String fcm) {

        if (new CheckConnectivity().isConnected(context)) {

            prg.setMessage("Please Wait....");
            prg.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    prg.dismiss();
                    try {
                        Log.e("Response : prince ", response);
                        if (response != null) {
                            JSONObject jsData = new JSONObject(response);
                            String status = jsData.getString("status");
                            if (status.equalsIgnoreCase("1")) {
                                saveDetails(jsData);
                            } else {
                                String msg = jsData.getString("msg");
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
                    params.put(Const.KEY_ACTION, UtilsUrl.Action_userDetail);
                    params.put(Const.KEY_USER_ID, SharedPref.getUserID());
                    params.put(Const.KEY_FCM, fcm);
                    params.put(Const.KEY_DEVICE_ID, deviceid);
                    Log.e("Param Response ", "" + params);
                    return params;
                }
            };
            AppController1.getInstance().addToRequestQueue(stringRequest);

        } else {
            Toast.makeText(context, "Check Your connetion", Toast.LENGTH_LONG).show();
        }

    }

    private void saveDetails(JSONObject jsData) {
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
            setData();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void profile_pic_update() {
        CropImage.startPickImageActivity(ProfileActivity.this);
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri).setGuidelines(CropImageView.Guidelines.ON).setMultiTouchEnabled(true).start(this);
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    // Log.e("bitmap",bitmap.toString()+"");
                    myBase64Image = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);
                    // Log.e("bitmapString",myBase64Image.toString()+"");
                    img_profile.setImageBitmap(bitmap);
                    //isImage_loaded = true;
                    uploadImage(new SharedPref().getUserID());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // mImageView.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e("Crop Error", error.getMessage() + "");
            }
        }
    }


    private void uploadImage(final String user_id) {
        Log.e("i m heree", "userid=> " + user_id);
        if (new CheckConnectivity().isConnected(context)) {

            prg.setMessage("Upload Image...");
            prg.show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, UtilsUrl.BASE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Display the first 500 characters of the response string.
                    try {
                        prg.dismiss();
                        Log.e("Response : prince ", response);
                        //  Manage_data_login(response);
                        JSONObject jsdata = new JSONObject(response);
                        String msg = jsdata.getString("msg");
                        String status = jsdata.getString("status");
                        if (status.equalsIgnoreCase("1")) {
                            getUserDetail(getDeviceDetail(), getFcmkey());
                        } else {
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Pic upload failed please try again later", Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    prg.dismiss();
                    Log.e("Error :", error.toString());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> header = new HashMap<>();
                    header.put(Itags.Header,Const.APP_TOKEN);
                    // params.put("Accept-Language", "fr")
                    return header;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put(Const.KEY_USER_ID, user_id);
                    params.put(Const.KEY_PIC, "" + myBase64Image);
                    params.put(Const.KEY_ACTION, UtilsUrl.Action_UpdateProfile_PIC);
                    //   params.put("pic", "data:image/jpeg;base64," + myBase64Image);
                    Log.e("user id ", "" + user_id);
                    Log.e("action ", "" + UtilsUrl.Action_UpdateProfile_PIC);
                    Log.e("pic", "" + myBase64Image);

                    Log.e("Param Response ", "" + params);
                    return params;
                }
            };
            AppController1.getInstance().addToRequestQueue(stringRequest);
        }else{
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
        String deviceID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        return deviceID;
    }

}
