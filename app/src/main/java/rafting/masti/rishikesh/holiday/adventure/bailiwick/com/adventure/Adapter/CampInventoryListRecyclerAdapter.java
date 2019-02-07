package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Modal.CampInventory_Beans;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;

/**
 * Created by Prince on 05-03-2018.
 */

public class CampInventoryListRecyclerAdapter extends RecyclerView.Adapter<CampInventoryListRecyclerAdapter.ViewHolder> {

    Context context;
    List<CampInventory_Beans> campInventoryListBeanList;
    Activity activity;

    private CampInventoryListRecyclerAdapter.ItemClickMasterMenuListInterface itemClickListener;

    public interface ItemClickMasterMenuListInterface {
        void onItemMasterClick(int position);
    }


    public CampInventoryListRecyclerAdapter(Context context, List<CampInventory_Beans> cartListBeanList, CampInventoryListRecyclerAdapter.ItemClickMasterMenuListInterface itemClickRecListInterface) {
        this.context = context;
        this.campInventoryListBeanList = cartListBeanList;
        itemClickListener = itemClickRecListInterface;
    }


    @Override
    public CampInventoryListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_camp_inventory, parent, false);

        CampInventoryListRecyclerAdapter.ViewHolder viewHolder = new CampInventoryListRecyclerAdapter.ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(CampInventoryListRecyclerAdapter.ViewHolder holder, final int position) {

        holder.text_date.setText(campInventoryListBeanList.get(position).getStr_date() + " " + campInventoryListBeanList.get(position).getStr_day());
        holder.text_booked.setText(campInventoryListBeanList.get(position).getStr_booked_room());
        holder.text_available.setText(campInventoryListBeanList.get(position).getStr_available_room());
        holder.text_total.setText(campInventoryListBeanList.get(position).getStr_total_room());


        holder.lnt_box.setOnClickListener(new View.OnClickListener() {
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

        TextView text_date, text_total, text_available, text_booked;
        LinearLayout lnt_box;


        public ViewHolder(View itemView) {
            super(itemView);

            text_date = (TextView) itemView.findViewById(R.id.txt_date);
            text_total = (TextView) itemView.findViewById(R.id.txt_total);
            text_available = (TextView) itemView.findViewById(R.id.txt_available);
            text_booked = (TextView) itemView.findViewById(R.id.txt_booked);
            lnt_box = (LinearLayout) itemView.findViewById(R.id.lnt_box);

        }


    }

}

