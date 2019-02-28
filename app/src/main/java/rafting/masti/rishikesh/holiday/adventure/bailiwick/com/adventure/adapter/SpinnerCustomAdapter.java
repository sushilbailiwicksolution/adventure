package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model.StateData;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;

public class SpinnerCustomAdapter extends ArrayAdapter<StateData> {

    private LayoutInflater layoutInflater;

    public SpinnerCustomAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<StateData> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(convertView,position);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(convertView,position);
    }

    private View getView(View convertView,int position){
        StateData stateData = getItem(position);
        SpinViewHolder spinViewHolder;
        View rowView = convertView;
        if (rowView == null){
            spinViewHolder = new SpinViewHolder();
            layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = Objects.requireNonNull(layoutInflater).inflate(R.layout.spinner_item,null,false);
            spinViewHolder.txtTitle = rowView.findViewById(R.id.spinTitle);
            rowView.setTag(spinViewHolder);
        }else{
            spinViewHolder = (SpinViewHolder) rowView.getTag();
        }
        spinViewHolder.txtTitle.setText(Objects.requireNonNull(stateData).getStateName());

        return rowView;
    }

    private class SpinViewHolder{
        TextView txtTitle;
    }

}
