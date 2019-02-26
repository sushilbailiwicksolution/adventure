package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.terms;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;

public class TermsActivity extends AppCompatActivity {

    Toolbar toolbar;
    FrameLayout frameTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        getUiObject();
        goToFragment();

    }

    private void getUiObject(){
        toolbar = findViewById(R.id.toolbar_main);
        frameTerms = findViewById(R.id.frame_terms);
    }

    private void goToFragment(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        AboutUsFragment aboutUsFragment = new AboutUsFragment();
        ft.replace(R.id.frame_terms,aboutUsFragment).commit();
    }

}
