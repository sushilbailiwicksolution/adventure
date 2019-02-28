package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.activities;

import android.content.Intent;
import android.os.Handler;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.activity_vendor.ActivityVendorDashboard;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.session.SharedPref;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Itags;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.loginRegister.LoginRegisterActivity;


public class Splash extends AwesomeSplash {

    private static int SPLASH_TIME_OUT = 3000;


    @Override
    public void initSplash(ConfigSplash configSplash) {

        configSplash.setBackgroundColor(R.color.black);
        configSplash.setAnimCircularRevealDuration(1000);
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);
        configSplash.setRevealFlagX(Flags.REVEAL_BOTTOM);
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM);

        configSplash.setLogoSplash(R.drawable.logo);
        configSplash.setAnimLogoSplashDuration(1500);
        configSplash.setAnimLogoSplashTechnique(Techniques.Bounce);

/* configSplash.setPathSplash(SyncStateContract.Constants.ACCOUNT_NAME);
configSplash.setOriginalHeight(400);
configSplash.setOriginalWidth(400);
configSplash.setAnimPathStrokeDrawingDuration(1000);
configSplash.setPathSplashStrokeSize(3);
configSplash.setPathSplashStrokeColor(R.color.grey);
configSplash.setAnimPathFillingDuration(1000);
configSplash.setPathSplashFillColor(R.color.wheat);*/


        configSplash.setTitleSplash("Adventure Life");

        configSplash.setTitleTextColor(R.color.yellow);
        configSplash.setTitleTextSize(40f);
        configSplash.setAnimTitleDuration(1500);
        configSplash.setAnimTitleTechnique(Techniques.BounceInDown);

    }

    @Override
    public void animationsFinished() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SharedPref.getIsLogin()) {

                    if (SharedPref.getUserTYPE().equalsIgnoreCase(Itags.VENDOR)) {
                        Intent i = new Intent(Splash.this, ActivityVendorDashboard.class);
                        startActivity(i);
                        finish();
                    } else {
                        Intent intent = new Intent(Splash.this, HomePage.class);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    Intent intent = new Intent(Splash.this, LoginRegisterActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);

    }

}

