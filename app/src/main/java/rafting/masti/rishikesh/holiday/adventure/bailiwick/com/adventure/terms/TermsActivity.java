package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.terms;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import java.util.Objects;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;

public class TermsActivity extends AppCompatActivity {

    Toolbar toolbar;
    FrameLayout frameTerms;
    String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        from = getFromPrevious();
        getUiObject();
        goToFragment(from.trim());

    }

    private String getFromPrevious() {
        return Objects.requireNonNull(getIntent().getExtras()).getString("drawer_from");
    }

    private void getUiObject() {
        toolbar = findViewById(R.id.toolbarTerms);
        setSupportActionBar(toolbar);
        frameTerms = findViewById(R.id.frame_terms);
    }

    private void goToFragment(String coming_from) {
        if (coming_from != null) {
            switch (coming_from) {
                case "about_us":
                    FragmentManager fmAboutUs = getSupportFragmentManager();
                    FragmentTransaction ftAboutUs = fmAboutUs.beginTransaction();
                    AboutUsFragment aboutUsFragment = new AboutUsFragment();
                    ftAboutUs.replace(R.id.frame_terms, aboutUsFragment).commit();
                    break;

                case "terms_and_conditions":
                    FragmentManager fmTermCond = getSupportFragmentManager();
                    FragmentTransaction ftTermCond = fmTermCond.beginTransaction();
                    TermsConditionsFragment termsConditionsFragment = new TermsConditionsFragment();
                    ftTermCond.replace(R.id.frame_terms, termsConditionsFragment).commit();
                    break;

                case "privacy_policy":
                    FragmentManager fmPrivacy = getSupportFragmentManager();
                    FragmentTransaction ftPrivacy = fmPrivacy.beginTransaction();
                    PrivacyPolicyFragment privacyPolicyFragment = new PrivacyPolicyFragment();
                    ftPrivacy.replace(R.id.frame_terms, privacyPolicyFragment).commit();
                    break;
            }
        }
    }

}
