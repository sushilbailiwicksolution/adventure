package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.loginRegister;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;


/**
 * Created by Prince on 23-11-2017.
 */

public class DestinationTab extends Fragment {

    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.destination_tab, container, false);


        return rootView;
    }

}
