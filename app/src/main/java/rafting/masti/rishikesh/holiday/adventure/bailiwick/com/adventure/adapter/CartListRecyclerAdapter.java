package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.apputils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model.CartListModel;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.utils.Itags;

/**
 * Created by Prince on 23-01-2018.
 */

public class CartListRecyclerAdapter extends RecyclerView.Adapter<CartListRecyclerAdapter.ViewHolder> {

    Context context;
    private List<CartListModel> cartListBeanList;

    private ItemClickRecListInterface itemClickListener;

    public interface ItemClickRecListInterface {
        void onItemClick(int position);
    }

    public CartListRecyclerAdapter(Context context, List<CartListModel> cartListBeanList, ItemClickRecListInterface itemClickRecListInterface) {
        this.context = context;
        this.cartListBeanList = cartListBeanList;
        itemClickListener = itemClickRecListInterface;
        Log.e("my size", "Size : " + cartListBeanList.size());
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        if (cartListBeanList.get(holder.getAdapterPosition()).getIsRafting().equalsIgnoreCase(Itags.CAMPING)) {

            holder.title_text.setText(cartListBeanList.get(holder.getAdapterPosition()).getTittle());
            holder.txt_check_out_text.setText("Check In");
            holder.txt_check_in_text.setText("Check Out");
            holder.txt_check_in.setText(cartListBeanList.get(holder.getAdapterPosition()).getStr_check_in());
            holder.txt_check_out.setText(cartListBeanList.get(holder.getAdapterPosition()).getStr_check_out());
            holder.txt_no_of_night.setText("No. of Night  " + cartListBeanList.get(holder.getAdapterPosition()).getStr_no_nights());
            holder.txt_adult_child.setText("Adult : " + cartListBeanList.get(holder.getAdapterPosition()).getStr_adult() + " Child : " + cartListBeanList.get(position).getStr_child());
            holder.txt_qty_text.setText("Nights");
            holder.txt_qty.setText(cartListBeanList.get(holder.getAdapterPosition()).getStr__qty());
            holder.txt_price.setText(cartListBeanList.get(holder.getAdapterPosition()).getPrice());
            holder.txt_amount.setText("Amount to Pay  " + cartListBeanList.get(holder.getAdapterPosition()).getTotal());
            if (!cartListBeanList.get(holder.getAdapterPosition()).getStr_message().equalsIgnoreCase("")){
                holder.txt_desc.setVisibility(View.VISIBLE);
                holder.txt_desc.setText("Message : " + cartListBeanList.get(holder.getAdapterPosition()).getStr_message());
            }else{
                holder.txt_desc.setVisibility(View.GONE);
            }


        } else if (cartListBeanList.get(holder.getAdapterPosition()).getIsRafting().equalsIgnoreCase(Itags.RAFTING)) {

            holder.txt_qty_text.setText("QTY");
            holder.title_text.setText(cartListBeanList.get(holder.getAdapterPosition()).getTittle());
            holder.txt_check_out_text.setText("Booking Date");
            holder.txt_check_in_text.setText("Timing");
            holder.txt_check_in.setText(cartListBeanList.get(holder.getAdapterPosition()).getStr_child());
            holder.txt_check_out.setText(cartListBeanList.get(holder.getAdapterPosition()).getStr_booking_date());
            holder.txt_no_of_night.setText("Starting Point : " + cartListBeanList.get(holder.getAdapterPosition()).getStr_adult());
            holder.txt_adult_child.setText("Service : " + "Rafting");
            holder.txt_qty.setText(cartListBeanList.get(holder.getAdapterPosition()).getStr__qty());
            holder.txt_price.setText(cartListBeanList.get(holder.getAdapterPosition()).getPrice());
            holder.txt_amount.setText("Amount to Pay  " + cartListBeanList.get(holder.getAdapterPosition()).getTotal());
            if (!cartListBeanList.get(holder.getAdapterPosition()).getStr_message().equalsIgnoreCase("")){
                holder.txt_desc.setVisibility(View.VISIBLE);
                holder.txt_desc.setText("Message : " + cartListBeanList.get(holder.getAdapterPosition()).getStr_message());
            }else{
                holder.txt_desc.setVisibility(View.GONE);
            }


        } else if (cartListBeanList.get(holder.getAdapterPosition()).getIsRafting().equalsIgnoreCase(Itags.CYCLING)){

            holder.title_text.setText(cartListBeanList.get(holder.getAdapterPosition()).getTittle());
            holder.txt_check_out_text.setText("From");
            holder.txt_check_in_text.setText("To");
            holder.txt_check_in.setText(cartListBeanList.get(holder.getAdapterPosition()).getStr_check_in());
            holder.txt_check_out.setText(cartListBeanList.get(holder.getAdapterPosition()).getStr_check_out());
            holder.txt_no_of_night.setText("No. of Days  " + cartListBeanList.get(holder.getAdapterPosition()).getStr_no_nights());
            holder.txt_qty_text.setText("No. of Cycles");
            holder.txt_adult_child.setText("Service : " + "Cycling");
            holder.txt_qty.setText(cartListBeanList.get(holder.getAdapterPosition()).getStr__qty());
            holder.txt_price.setText(cartListBeanList.get(holder.getAdapterPosition()).getPrice());
            holder.txt_amount.setText("Amount to Pay  " + cartListBeanList.get(holder.getAdapterPosition()).getTotal());
            if (!cartListBeanList.get(holder.getAdapterPosition()).getStr_message().equalsIgnoreCase("")){
                holder.txt_desc.setVisibility(View.VISIBLE);
                holder.txt_desc.setText("Message : " + cartListBeanList.get(holder.getAdapterPosition()).getStr_message());
            }else{
                holder.txt_desc.setVisibility(View.GONE);
            }


        }else if (cartListBeanList.get(holder.getAdapterPosition()).getIsRafting().equalsIgnoreCase(Itags.BUNGY_JUMPING)){

            holder.txt_qty_text.setText("QTY");
            holder.title_text.setText(cartListBeanList.get(holder.getAdapterPosition()).getTittle());
            holder.txt_check_out_text.setText("Booked On");
            holder.txt_check_in_text.setText("Booking Date");
            holder.txt_check_in.setText(cartListBeanList.get(holder.getAdapterPosition()).getStr_check_in());
            holder.txt_check_out.setText(cartListBeanList.get(holder.getAdapterPosition()).getStr_booking_date());
            holder.txt_no_of_night.setText("Starting Point : " + cartListBeanList.get(holder.getAdapterPosition()).getStr_starting_point());
            holder.txt_adult_child.setText("Service : " + "Bungee Jumping");
            holder.txt_qty.setText(cartListBeanList.get(holder.getAdapterPosition()).getStr_adult());
            holder.txt_price.setText(cartListBeanList.get(holder.getAdapterPosition()).getPrice());
            holder.txt_amount.setText("Amount to Pay  " + cartListBeanList.get(holder.getAdapterPosition()).getTotal());
            if (!cartListBeanList.get(holder.getAdapterPosition()).getStr_message().equalsIgnoreCase("")){
                holder.txt_desc.setVisibility(View.VISIBLE);
                holder.txt_desc.setText("Message : " + cartListBeanList.get(holder.getAdapterPosition()).getStr_message());
            }else{
                holder.txt_desc.setVisibility(View.GONE);
            }


        }else if (cartListBeanList.get(holder.getAdapterPosition()).getIsRafting().equalsIgnoreCase(Itags.BIKING)){

            holder.title_text.setText(cartListBeanList.get(holder.getAdapterPosition()).getTittle());
            holder.txt_check_out_text.setText("From");
            holder.txt_check_in_text.setText("To");
            holder.txt_check_in.setText(cartListBeanList.get(holder.getAdapterPosition()).getStr_check_in());
            holder.txt_check_out.setText(cartListBeanList.get(holder.getAdapterPosition()).getStr_check_out());
            holder.txt_no_of_night.setText("No. of Days  " + cartListBeanList.get(holder.getAdapterPosition()).getStr_no_nights());
            holder.txt_qty_text.setText("No. of Bikes");
            holder.txt_adult_child.setText("Service : " + "Biking");
            holder.txt_qty.setText(cartListBeanList.get(holder.getAdapterPosition()).getStr__qty());
            holder.txt_price.setText(cartListBeanList.get(holder.getAdapterPosition()).getPrice());
            holder.txt_amount.setText("Amount to Pay  " + cartListBeanList.get(holder.getAdapterPosition()).getTotal());
            if (!cartListBeanList.get(holder.getAdapterPosition()).getStr_message().equalsIgnoreCase("")){
                holder.txt_desc.setVisibility(View.VISIBLE);
                holder.txt_desc.setText("Message : " + cartListBeanList.get(holder.getAdapterPosition()).getStr_message());
            }else{
                holder.txt_desc.setVisibility(View.GONE);
            }


        } else {
            Log.e("Some Invalid Entry", "Some Invalid Entry");
        }

        Glide.with(context).load(UtilsUrl.IMAGEBASE_URL + cartListBeanList.get(position).getImageurl()).error(R.drawable.camp_banner).into(holder.img_logo);

        holder.img_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartListBeanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title_text, txt_check_in, txt_check_out, txt_no_of_night, txt_adult_child, txt_qty, txt_price,
                txt_amount, txt_desc, txt_qty_text, txt_check_out_text, txt_check_in_text;
        ImageView img_logo, img_remove;

        public ViewHolder(View itemView) {
            super(itemView);
            img_logo = itemView.findViewById(R.id.img_logo);
            img_remove = itemView.findViewById(R.id.img_remove);

            title_text = itemView.findViewById(R.id.title_text);
            txt_check_in = itemView.findViewById(R.id.txt_check_in);
            txt_check_out = itemView.findViewById(R.id.txt_check_out);
            txt_no_of_night = itemView.findViewById(R.id.txt_no_of_night);
            txt_adult_child = itemView.findViewById(R.id.txt_adult_child);
            txt_qty = itemView.findViewById(R.id.txt_qty);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_amount = itemView.findViewById(R.id.txt_amount);
            txt_desc = itemView.findViewById(R.id.txt_desc);
            txt_qty_text = itemView.findViewById(R.id.txt_qty_text);
            txt_check_out_text = itemView.findViewById(R.id.txt_check_out_text);
            txt_check_in_text = itemView.findViewById(R.id.txt_check_in_text);
//            ivEditCart = itemView.findViewById(R.id.ivEditCart);

        }


    }

}
