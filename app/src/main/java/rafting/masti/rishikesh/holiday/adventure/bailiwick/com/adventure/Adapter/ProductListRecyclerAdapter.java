package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.AppUtils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Modal.ProductListBean;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;

/**
 * Created by Prince on 02-Feb-17.
 */

public class ProductListRecyclerAdapter extends RecyclerView.Adapter<ProductListRecyclerAdapter.ViewHolder> {

    Context context;
    List<ProductListBean> productListBeanList;
    Activity activity;

    private ItemClickRecListInterface itemClickListener;

    public interface ItemClickRecListInterface {
        void onItemClick(int position);
    }


    public ProductListRecyclerAdapter(Context context, List<ProductListBean> productListBeanList, ItemClickRecListInterface itemClickRecListInterface) {
        this.context = context;
        this.productListBeanList = productListBeanList;
        itemClickListener = itemClickRecListInterface;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.text_product_name.setText(productListBeanList.get(position).getStr_package_name());
        holder.text_product_name.setVisibility(View.GONE);

        holder.text_final_price.setText(productListBeanList.get(position).getStr_price());
//        holder.text_original_price.setText(productListBeanList.get(position).getStr_product_orignal_price());
        //      holder.text_original_price.setPaintFlags(holder.text_original_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        Log.e("image url ", UtilsUrl.IMAGEBASE_URL + productListBeanList.get(position).getStr_image());
        holder.text_city.setText(" "+productListBeanList.get(position).getStr_city());
        if (!productListBeanList.get(position).isISNight()) {
            holder.txt_isNight.setText("/ Person");

        } else {
            holder.txt_isNight.setText("/ Night");

        }
        Glide.with(context).load(UtilsUrl.IMAGEBASE_URL + productListBeanList.get(position).getStr_image()).error(R.drawable.camp_banner).into(holder.image_product_image);


        holder.card_view_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return productListBeanList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_product_name, text_final_price, text_original_price, txt_isNight,text_city;
        //ImageView wishlist, active_wishlist;
        ImageView image_product_image;
        LinearLayout card_view_product;

        public ViewHolder(View itemView) {
            super(itemView);
            card_view_product = (LinearLayout) itemView.findViewById(R.id.card_view_product);
            image_product_image = (ImageView) itemView.findViewById(R.id.image_product_image);
            text_product_name = (TextView) itemView.findViewById(R.id.text_product_name);
            text_final_price = (TextView) itemView.findViewById(R.id.text_final_price);
            text_original_price = (TextView) itemView.findViewById(R.id.text_original_price);
            txt_isNight = (TextView) itemView.findViewById(R.id.txt_isNight);
            image_product_image = (ImageView) itemView.findViewById(R.id.image_product_image);
            text_city= (TextView) itemView.findViewById(R.id.text_city);
            //       wishlist = (ImageView) itemView.findViewById(R.id.image_wishlist_icon);
            //     active_wishlist = (ImageView) itemView.findViewById(R.id.image_active_wishlist);


        }


    }

}
