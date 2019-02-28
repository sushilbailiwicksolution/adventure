package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model.RaftingInventoryBeans;


public class RaftInventRecyclerAdapter extends RecyclerView.Adapter<RaftInventRecyclerAdapter.ViewHolder> {

    Context context;
    private List<RaftingInventoryBeans> campInventoryListBeanList;

    private ItemClickMasterMenuListInterface itemClickListener;

    public interface ItemClickMasterMenuListInterface {
        void onItemMasterClick(int position);
    }


    public RaftInventRecyclerAdapter(Context context, List<RaftingInventoryBeans> cartListBeanList, ItemClickMasterMenuListInterface itemClickRecListInterface) {
        this.context = context;
        this.campInventoryListBeanList = cartListBeanList;
        itemClickListener = itemClickRecListInterface;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_rafting_inventory, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder,int position) {

        String avail = "Avail : " + campInventoryListBeanList.get(holder.getAdapterPosition()).getStr_seats_avilable();
        holder.txt_avail.setText(avail);

        holder.txt_starting_point.setText(campInventoryListBeanList.get(holder.getAdapterPosition()).getStr_start_point());

        String time = "Time : " + campInventoryListBeanList.get(holder.getAdapterPosition()).getStr_time();
        holder.txt_timing.setText(time);

        holder.txt_date_from.setText(campInventoryListBeanList.get(holder.getAdapterPosition()).getStr_selected_date());


        holder.lnt_box.setOnClickListener(new View.OnClickListener() {
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

        TextView txt_avail, txt_starting_point, txt_timing, txt_date_from;
        LinearLayout lnt_box;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_avail = itemView.findViewById(R.id.txt_avail);
            txt_starting_point = itemView.findViewById(R.id.txt_starting_point);
            txt_timing = itemView.findViewById(R.id.txt_timing);
            txt_date_from = itemView.findViewById(R.id.txt_date_from);
            lnt_box = itemView.findViewById(R.id.lnt_box);

        }


    }

}


