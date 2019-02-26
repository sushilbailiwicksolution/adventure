package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Objects;
import java.util.regex.Pattern;

public class Commons {

    public static boolean isValidEmail(String inputEmail){
        String expression = "[A-Za-z0-9._%+-]{3,}@[a-zA-Z]{3,}([.]{1}[a-zA-Z]{2,}|[.]{1}[a-zA-Z]{2,}[.]{1}[a-zA-Z]{2,})";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(inputEmail).matches();
    }

    public static boolean isValidMobile(String inputMobile){
        String expression = "^[6-9][0-9]{9}$";
        Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);
        return pattern.matcher(inputMobile).matches();
    }
/* public static void hideSoftKeyboard(Activity activity, View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(inputMethodManager).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }*/


}
