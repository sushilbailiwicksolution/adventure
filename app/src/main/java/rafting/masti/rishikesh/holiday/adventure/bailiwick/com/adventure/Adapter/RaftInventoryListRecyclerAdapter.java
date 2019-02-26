package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model.RaftingInventoryBeans;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;

/**
 * Created by Prince on 08-03-2018.
 */

public class RaftInventoryListRecyclerAdapter extends RecyclerView.Adapter<RaftInventoryListRecyclerAdapter.ViewHolder> {

    Context context;
    List<RaftingInventoryBeans> campInventoryListBeanList;
    Activity activity;

    private ItemClickMasterMenuListInterface itemClickListener;

    public interface ItemClickMasterMenuListInterface {
        void onItemMasterClick(int position);
    }


    public RaftInventoryListRecyclerAdapter(Context context, List<RaftingInventoryBeans> cartListBeanList, ItemClickMasterMenuListInterface itemClickRecListInterface) {
        this.context = context;
        this.campInventoryListBeanList = cartListBeanList;
        itemClickListener = itemClickRecListInterface;
    }


    @Override
    public RaftInventoryListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_rafting_inventory, parent, false);

        RaftInventoryListRecyclerAdapter.ViewHolder viewHolder = new RaftInventoryListRecyclerAdapter.ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RaftInventoryListRecyclerAdapter.ViewHolder holder, final int position) {

        holder.txt_avail.setText("Avail : " + campInventoryListBeanList.get(position).getStr_seats_avilable());
        holder.txt_starting_point.setText(campInventoryListBeanList.get(position).getStr_start_point());
        holder.txt_timing.setText("Time : " + campInventoryListBeanList.get(position).getStr_time());
        holder.txt_date_from.setText(campInventoryListBeanList.get(position).getStr_selected_date());


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

        TextView txt_avail, txt_starting_point, txt_timing, txt_date_from;
        LinearLayout lnt_box;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_avail = (TextView) itemView.findViewById(R.id.txt_avail);
            txt_starting_point = (TextView) itemView.findViewById(R.id.txt_starting_point);
            txt_timing = (TextView) itemView.findViewById(R.id.txt_timing);
            txt_date_from = (TextView) itemView.findViewById(R.id.txt_date_from);
            lnt_box = (LinearLayout) itemView.findViewById(R.id.lnt_box);

        }


    }

}


