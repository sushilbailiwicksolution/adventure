package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model.VendorBooking_Beans;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;

/**
 * Created by Prince on 12-03-2018.
 */

public class Vendor_BookingAdapter extends RecyclerView.Adapter<Vendor_BookingAdapter.ViewHolder> {

    Context context;
    List<VendorBooking_Beans> campInventoryListBeanList;
    Activity activity;

    private Vendor_BookingAdapter.ItemClickMasterMenuListInterface itemClickListener;

    public interface ItemClickMasterMenuListInterface {
        void onItemMasterClick(int position);
    }


    public Vendor_BookingAdapter(Context context, List<VendorBooking_Beans> cartListBeanList, Vendor_BookingAdapter.ItemClickMasterMenuListInterface itemClickRecListInterface) {
        this.context = context;
        this.campInventoryListBeanList = cartListBeanList;
        itemClickListener = itemClickRecListInterface;
    }


    @Override
    public Vendor_BookingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_vendor_booking_history, parent, false);

        Vendor_BookingAdapter.ViewHolder viewHolder = new Vendor_BookingAdapter.ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(Vendor_BookingAdapter.ViewHolder holder, final int position) {


        holder.textView_cstName.setText(campInventoryListBeanList.get(position).getStr_customer_name());
        holder.txt_sports_name.setText(campInventoryListBeanList.get(position).getStr_adventure_sport());
        holder.textView_email.setText(campInventoryListBeanList.get(position).getStr_customer_email());
        holder.textView_phone.setText(campInventoryListBeanList.get(position).getStr_phone());


        if (campInventoryListBeanList.get(position).getStr_adventure_sport().equalsIgnoreCase("Rafting")) {
            holder.textView_checkIN.setText(campInventoryListBeanList.get(position).getStr_check_in());
            holder.textView_checkOut.setText(campInventoryListBeanList.get(position).getStr_check_out());
            holder.textView_adult.setText(campInventoryListBeanList.get(position).getStr_no_of_adult());
            holder.textView_children.setText(campInventoryListBeanList.get(position).getStr_no_of_children());

            holder.textView_children.setText("");
            holder.txt_checkin_local.setText("Start Time");
            holder.txt_checkout_local.setText("Point");
            holder.txt_adult_local.setText("Booked Seats");
            holder.txt_children_local.setText("");

        } else {
            holder.textView_checkIN.setText(campInventoryListBeanList.get(position).getStr_check_in());
            holder.textView_checkOut.setText(campInventoryListBeanList.get(position).getStr_check_out());
            holder.textView_adult.setText(campInventoryListBeanList.get(position).getStr_no_of_adult());
            holder.textView_children.setText(campInventoryListBeanList.get(position).getStr_no_of_children());

            holder.txt_checkin_local.setText("Check In");
            holder.txt_checkout_local.setText("Check Out");
            holder.txt_adult_local.setText("Adult");
            holder.txt_children_local.setText("Children");

        }


        holder.textView_message.setText(campInventoryListBeanList.get(position).getStr_customer_message());
        String status = campInventoryListBeanList.get(position).getStr_customer_name();


        if (status.equalsIgnoreCase("0")) {
            holder.textView_status.setText("UnPaid");
            holder.textView_status.setTextColor(ContextCompat.getColor(context, R.color.red));
        } else {
            holder.textView_status.setText("Paid");
            holder.textView_status.setTextColor(ContextCompat.getColor(context, R.color.green));

        }


        holder.textView_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemMasterClick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return campInventoryListBeanList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView_cstName, txt_sports_name, textView_email, textView_phone,
                textView_checkIN, textView_checkOut, textView_adult, textView_children, textView_message, textView_status;

        TextView txt_checkin_local, txt_checkout_local, txt_adult_local, txt_children_local;

        public ViewHolder(View itemView) {
            super(itemView);

            textView_cstName = (TextView) itemView.findViewById(R.id.textView_cstName);
            txt_sports_name = (TextView) itemView.findViewById(R.id.txt_sports_name);
            textView_email = (TextView) itemView.findViewById(R.id.textView_email);
            textView_phone = (TextView) itemView.findViewById(R.id.textView_phone);
            textView_checkIN = (TextView) itemView.findViewById(R.id.textView_checkIN);
            textView_checkOut = (TextView) itemView.findViewById(R.id.textView_checkOut);
            textView_adult = (TextView) itemView.findViewById(R.id.textView_adult);
            textView_children = (TextView) itemView.findViewById(R.id.textView_children);
            textView_message = (TextView) itemView.findViewById(R.id.textView_message);
            textView_status = (TextView) itemView.findViewById(R.id.textView_status);

            txt_checkin_local = (TextView) itemView.findViewById(R.id.txt_checkin_local);
            txt_checkout_local = (TextView) itemView.findViewById(R.id.txt_checkout_local);
            txt_adult_local = (TextView) itemView.findViewById(R.id.txt_adult_local);
            txt_children_local = (TextView) itemView.findViewById(R.id.txt_children_local);
        }


    }

}



