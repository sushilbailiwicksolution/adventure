package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.apputils.UtilsUrl;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model.Servicebeans;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;

/**
 * Created by Prince on 04-01-2018.
 */

public class ServiceMasterRecyclerAdapter extends RecyclerView.Adapter<ServiceMasterRecyclerAdapter.ViewHolder> {

    Context context;
    List<Servicebeans> productListBeanList;
    Activity activity;

    private ItemClickRecListInterface itemClickListener;

    public interface ItemClickRecListInterface {
        void onItemClick(int position);
    }


    public ServiceMasterRecyclerAdapter(Context context, List<Servicebeans> productListBeanList, ServiceMasterRecyclerAdapter.ItemClickRecListInterface itemClickRecListInterface) {
        this.context = context;
        this.productListBeanList = productListBeanList;
        itemClickListener = itemClickRecListInterface;
    }


    @Override
    public ServiceMasterRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cst_service_list, parent, false);

        ServiceMasterRecyclerAdapter.ViewHolder viewHolder = new ServiceMasterRecyclerAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ServiceMasterRecyclerAdapter.ViewHolder holder, final int position) {


        Log.e("position value :","prince : "+position);
        Log.e("Legnth","list legnth : "+productListBeanList.size());

        holder.txt_tittle.setText(productListBeanList.get(position).getStr_name());
        Log.e("image url ", UtilsUrl.IMAGEBASE_URL + productListBeanList.get(position).getStr_sserviceImage());

        Glide.with(context).load(UtilsUrl.IMAGEBASE_URL + productListBeanList.get(position).getStr_sserviceImage()).error(R.drawable.camp_banner).into(holder.img_service);

     /*   holder.text_final_price.setText(productListBeanList.get(position).getStr_product_final_price());
        holder.text_original_price.setText(productListBeanList.get(position).getStr_product_orignal_price());
        holder.text_original_price.setPaintFlags(holder.text_original_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
*/
        holder.crd_camp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(position);
            }
        });
       holder. btn_explore.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               itemClickListener.onItemClick(position);
           }
       });

    }

    @Override
    public int getItemCount() {
        return productListBeanList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView crd_camp;
        TextView txt_tittle;
        ImageView img_service;
Button btn_explore;


        public ViewHolder(View itemView) {
            super(itemView);
            crd_camp = (CardView) itemView.findViewById(R.id.crd_camp);
            img_service = (ImageView) itemView.findViewById(R.id.img_service);
            txt_tittle = (TextView) itemView.findViewById(R.id.txt_tittle);
            btn_explore= (Button) itemView.findViewById(R.id.btn_explore);
            //       wishlist = (ImageView) itemView.findViewById(R.id.image_wishlist_icon);
            //     active_wishlist = (ImageView) itemView.findViewById(R.id.image_active_wishlist);


        }


    }

}

