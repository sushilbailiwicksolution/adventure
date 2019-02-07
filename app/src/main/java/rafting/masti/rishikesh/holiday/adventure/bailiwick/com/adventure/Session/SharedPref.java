package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Session;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.App.AppController1;

/**
 * Created by claritus on 2/3/16.
 */
public class SharedPref {
    public static String MODE_TYPE = "Adventure";
    private static final String firstName = "firstname";
    private static final String isLogin = "isLogin";
    private static final String USERID = "userid";
    private static final String USERTYPE = "USERTYPE";
    private static final String LASTNAME = "LASTNAME";
    private static final String PROFILE_PIC = "PROFILE_PIC";
    private static final String MOBILE1 = "MOBILE1";
    private static final String EMAIL = "email";

    private static final String TEMPEMAIL = "temp_email";
    private static final String TEMPfirstName = "temp_firstname";
    private static final String TEMPMOBILE1 = "temp_MOBILE1";
    private static final String TEMPJSON = "tempjson";

    private static final String Address = "address";
    private static final String Gender = "gender";
    private static final String CITY = "city";

    static SharedPreferences prefs;

    public static SharedPreferences getInstance() {
        if (prefs == null) {
            prefs = PreferenceManager.getDefaultSharedPreferences(AppController1.getInstance());
        }
        return prefs;
    }

    // Temp value for rafting
    public static String getTEMPfirstName() {
        return getInstance().getString(TEMPfirstName, "");
    }

    public static void saveTempfirstName(String string) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(TEMPfirstName, string);
        editor.apply();
    }

    ///
    public static String getTempemail() {
        return getInstance().getString(TEMPEMAIL, "");
    }

    public static void saveTempemail(String string) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(TEMPEMAIL, string);
        editor.apply();
    }

    ///
    public static String getTempmobile1() {
        return getInstance().getString(TEMPMOBILE1, "");
    }

    public static void saveTempmobile1(String string) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(TEMPMOBILE1, string);
        editor.apply();
    }

    public static String getTempJSON() {
        return getInstance().getString(TEMPJSON, "");
    }

    public static void saveTempJSON(String string) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(TEMPJSON, string);
        editor.apply();
    }

    public static String getAddress() {
        return getInstance().getString(Address, "");
    }

    public static void saveAddress(String string) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(Address, string);
        editor.apply();
    }

    public static String getGender() {

        return getInstance().getString(Gender, "");
    }

    public static void savegender(String string) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(Gender, string);
        editor.apply();
    }

    public static String getCity() {

        return getInstance().getString(CITY, "");
    }

    public static void saveCity(String string) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(CITY, string);
        editor.apply();
    }

    public static String getEmail() {

        return getInstance().getString(EMAIL, "");
    }

    public static void saveEmail(String string) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(EMAIL, string);
        editor.apply();
    }

    public static String getMobile1() {

        return getInstance().getString(MOBILE1, "");
    }

    public static void saveMobile1(String string) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(MOBILE1, string);
        editor.apply();
    }

    public static String getprofileURL() {

        return getInstance().getString(PROFILE_PIC, "");
    }

    public static void saveprofileURL(String string) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(PROFILE_PIC, string);
        editor.apply();
    }

    public static String getLastName() {

        return getInstance().getString(LASTNAME, "");
    }

    public static void saveLastName(String string) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(LASTNAME, string);
        editor.apply();
    }


    public static String getUserTYPE() {

        return getInstance().getString(USERTYPE, "");
    }

    public static void saveUserTYPE(String string) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(USERTYPE, string);
        editor.apply();
    }

    public static String getUserID() {

        return getInstance().getString(USERID, "");
    }

    public static void saveUserID(String string) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(USERID, string);
        editor.apply();
    }

    public static String getFirstName() {

        return getInstance().getString(firstName, "");
    }

    public static void saveFirstName(String string) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(firstName, string);
        editor.apply();
    }

    public static Boolean getIsLogin() {

        return getInstance().getBoolean(isLogin, false);

    }

    public static void saveLogin(boolean Login) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putBoolean(isLogin, Login);
        editor.apply();
    }

    public static void ClearAll() {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.clear().commit();
    }


}