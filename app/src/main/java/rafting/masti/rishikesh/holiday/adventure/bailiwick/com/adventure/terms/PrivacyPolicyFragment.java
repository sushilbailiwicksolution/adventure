package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.terms;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrivacyPolicyFragment extends Fragment {


    Toolbar toolbar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_privacy_policy, container, false);
        getUiObject(view);
        setToolbar();

        return view;
    }

    private void getUiObject(View fragView){

    }

    private void setToolbar(){
        toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbarTerms);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Privacy Policy");
    }

}
