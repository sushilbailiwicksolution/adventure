package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Rafting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Rafting.RaftingFragment.Rafting_Frag_Cointainer;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Rafting.RaftingFragment.Rafting_Frag_personal;


/**
 * Created by Prince on 22-01-2018.
 */

public class RaftingActivity extends AppCompatActivity {

    SectionsPagerAdapter mSectionsPagerAdapter;
    public static CustomViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rafting_activity);

        setPager();


    }

    private void setPager() {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (CustomViewPager) findViewById(R.id.container);
        mViewPager.setSwipeable(false);
        mViewPager.setAdapter(mSectionsPagerAdapter);


    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Rafting_Frag_personal personal = new Rafting_Frag_personal();
                    return personal;
                case 1:
                    Rafting_Frag_Cointainer destinationTab = new Rafting_Frag_Cointainer();
                    return destinationTab;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

//        return super.onTouchEvent(event);
        return true;
    }

}
