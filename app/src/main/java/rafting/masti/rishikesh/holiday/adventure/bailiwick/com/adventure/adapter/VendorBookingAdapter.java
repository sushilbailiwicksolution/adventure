package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model.VendorBookingBeans;


public class VendorBookingAdapter extends RecyclerView.Adapter<VendorBookingAdapter.ViewHolder> {

    Context context;
    private List<VendorBookingBeans> campInventoryListBeanList;

    private ItemClickMasterMenuListInterface itemClickListener;

    public interface ItemClickMasterMenuListInterface {
        void onItemMasterClick(int position);
    }


    public VendorBookingAdapter(Context context, List<VendorBookingBeans> cartListBeanList, ItemClickMasterMenuListInterface itemClickRecListInterface) {
        this.context = context;
        this.campInventoryListBeanList = cartListBeanList;
        itemClickListener = itemClickRecListInterface;
    }


    @Override
    public VendorBookingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_vendor_booking_history, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        holder.textView_cstName.setText(campInventoryListBeanList.get(holder.getAdapterPosition()).getStr_customer_name());
        holder.txt_sports_name.setText(campInventoryListBeanList.get(holder.getAdapterPosition()).getStr_adventure_sport());
        holder.textView_email.setText(campInventoryListBeanList.get(holder.getAdapterPosition()).getStr_customer_email());
        holder.textView_phone.setText(campInventoryListBeanList.get(holder.getAdapterPosition()).getStr_phone());


        if (campInventoryListBeanList.get(holder.getAdapterPosition()).getStr_adventure_sport().equalsIgnoreCase("Rafting")) {
            holder.textView_checkIN.setText(campInventoryListBeanList.get(holder.getAdapterPosition()).getStr_check_in());
            holder.textView_checkOut.setText(campInventoryListBeanList.get(holder.getAdapterPosition()).getStr_check_out());
            holder.textView_adult.setText(campInventoryListBeanList.get(holder.getAdapterPosition()).getStr_no_of_adult());
            holder.textView_children.setText(campInventoryListBeanList.get(holder.getAdapterPosition()).getStr_no_of_children());

            holder.textView_children.setText("");
            holder.txt_checkin_local.setText("Start Time");
            holder.txt_checkout_local.setText("Point");
            holder.txt_adult_local.setText("Booked Seats");
            holder.txt_children_local.setText("");

        } else {
            holder.textView_checkIN.setText(campInventoryListBeanList.get(holder.getAdapterPosition()).getStr_check_in());
            holder.textView_checkOut.setText(campInventoryListBeanList.get(holder.getAdapterPosition()).getStr_check_out());
            holder.textView_adult.setText(campInventoryListBeanList.get(holder.getAdapterPosition()).getStr_no_of_adult());
            holder.textView_children.setText(campInventoryListBeanList.get(holder.getAdapterPosition()).getStr_no_of_children());

            holder.txt_checkin_local.setText("Check In");
            holder.txt_checkout_local.setText("Check Out");
            holder.txt_adult_local.setText("Adult");
            holder.txt_children_local.setText("Children");

        }


        holder.textView_message.setText(campInventoryListBeanList.get(holder.getAdapterPosition()).getStr_customer_message());
        String status = campInventoryListBeanList.get(holder.getAdapterPosition()).getStr_customer_name();


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
                itemClickListener.onItemMasterClick(holder.getAdapterPosition());
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

            textView_cstName = itemView.findViewById(R.id.textView_cstName);
            txt_sports_name = itemView.findViewById(R.id.txt_sports_name);
            textView_email = itemView.findViewById(R.id.textView_email);
            textView_phone = itemView.findViewById(R.id.textView_phone);
            textView_checkIN = itemView.findViewById(R.id.textView_checkIN);
            textView_checkOut = itemView.findViewById(R.id.textView_checkOut);
            textView_adult = itemView.findViewById(R.id.textView_adult);
            textView_children = itemView.findViewById(R.id.textView_children);
            textView_message = itemView.findViewById(R.id.textView_message);
            textView_status = itemView.findViewById(R.id.textView_status);

            txt_checkin_local = itemView.findViewById(R.id.txt_checkin_local);
            txt_checkout_local = itemView.findViewById(R.id.txt_checkout_local);
            txt_adult_local = itemView.findViewById(R.id.txt_adult_local);
            txt_children_local = itemView.findViewById(R.id.txt_children_local);
        }


    }

}



