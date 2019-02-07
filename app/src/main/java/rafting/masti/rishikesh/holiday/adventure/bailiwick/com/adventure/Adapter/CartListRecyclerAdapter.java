package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Adapter;

import android.app.Activity;
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

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.AppUtils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Modal.CartListModel;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Utils.Itags;

/**
 * Created by Prince on 23-01-2018.
 */

public class CartListRecyclerAdapter extends RecyclerView.Adapter<CartListRecyclerAdapter.ViewHolder> {

    Context context;
    List<CartListModel> cartListBeanList;
    Activity activity;

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

        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        if (cartListBeanList.get(position).getIsRafting().equalsIgnoreCase(Itags.CAMPING)) {

            holder.title_text.setText(cartListBeanList.get(position).getTittle());
            holder.txt_check_out_text.setText("Check In");
            holder.txt_check_in_text.setText("Check Out");

            holder.txt_check_in.setText(cartListBeanList.get(position).getStr_check_in());
            holder.txt_check_out.setText(cartListBeanList.get(position).getStr_check_out());
            holder.txt_no_of_night.setText("No. of Night  " + cartListBeanList.get(position).getStr_no_nights());
            holder.txt_adult_child.setText("Adult : " + cartListBeanList.get(position).getStr_adult() + " Child : " + cartListBeanList.get(position).getStr_child());
            holder.txt_qty_text.setText("Nights");
            holder.txt_qty.setText(cartListBeanList.get(position).getStr__qty());
            holder.txt_price.setText(cartListBeanList.get(position).getPrice());
            holder.txt_amount.setText("Paid Amount  " + cartListBeanList.get(position).getTotal());
            holder.txt_desc.setText("Message : " + cartListBeanList.get(position).getStr_message());
        } else if (cartListBeanList.get(position).getIsRafting().equalsIgnoreCase(Itags.RAFTING)) {
            holder.txt_qty_text.setText("QTY");

            holder.title_text.setText(cartListBeanList.get(position).getTittle());
            holder.txt_check_out_text.setText("Booking Date");
            holder.txt_check_in_text.setText("Timing");

            holder.txt_check_in.setText(cartListBeanList.get(position).getStr_child());
            holder.txt_check_out.setText(cartListBeanList.get(position).getStr_booking_date());

            holder.txt_no_of_night.setText("Starting Point : " + cartListBeanList.get(position).getStr_adult());
            holder.txt_adult_child.setText("Service : " + "Rafting");

            holder.txt_qty.setText(cartListBeanList.get(position).getStr__qty());
            holder.txt_price.setText(cartListBeanList.get(position).getPrice());
            holder.txt_amount.setText("Paid Amount  " + cartListBeanList.get(position).getTotal());
            holder.txt_desc.setText("Message : " + cartListBeanList.get(position).getStr_message());


        } else {
            Log.e("Some Invalid Entry", "Some Invalid Entry");
        }

        Glide.with(context).load(UtilsUrl.IMAGEBASE_URL + cartListBeanList.get(position).getImageurl()).error(R.drawable.camp_banner).into(holder.img_logo);


        holder.img_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return cartListBeanList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title_text, txt_check_in, txt_check_out, txt_no_of_night, txt_adult_child, txt_qty, txt_price, txt_amount, txt_desc, txt_qty_text, txt_check_out_text, txt_check_in_text;
        ImageView img_logo, img_remove;

        public ViewHolder(View itemView) {
            super(itemView);
            img_logo = (ImageView) itemView.findViewById(R.id.img_logo);
            img_remove = (ImageView) itemView.findViewById(R.id.img_remove);

            title_text = (TextView) itemView.findViewById(R.id.title_text);
            txt_check_in = (TextView) itemView.findViewById(R.id.txt_check_in);
            txt_check_out = (TextView) itemView.findViewById(R.id.txt_check_out);
            txt_no_of_night = (TextView) itemView.findViewById(R.id.txt_no_of_night);
            txt_adult_child = (TextView) itemView.findViewById(R.id.txt_adult_child);
            txt_qty = (TextView) itemView.findViewById(R.id.txt_qty);
            txt_price = (TextView) itemView.findViewById(R.id.txt_price);
            txt_amount = (TextView) itemView.findViewById(R.id.txt_amount);
            txt_desc = (TextView) itemView.findViewById(R.id.txt_desc);
            txt_qty_text = (TextView) itemView.findViewById(R.id.txt_qty_text);
            txt_check_out_text = (TextView) itemView.findViewById(R.id.txt_check_out_text);
            txt_check_in_text = (TextView) itemView.findViewById(R.id.txt_check_in_text);

        }


    }

}
