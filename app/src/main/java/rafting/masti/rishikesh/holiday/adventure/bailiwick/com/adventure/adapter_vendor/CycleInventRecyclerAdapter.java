package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.adapter_vendor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model_vendor.CycleInventoryBeans;


public class CycleInventRecyclerAdapter extends RecyclerView.Adapter<CycleInventRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<CycleInventoryBeans> campInventoryListBeanList;

    private CycleInventRecyclerAdapter.ItemClickMasterMenuListInterface itemClickListener;

    public interface ItemClickMasterMenuListInterface {
        void onItemMasterClick(int position);
    }


    public CycleInventRecyclerAdapter(Context context, List<CycleInventoryBeans> cartListBeanList, CycleInventRecyclerAdapter.ItemClickMasterMenuListInterface itemClickRecListInterface) {
        this.context = context;
        this.campInventoryListBeanList = cartListBeanList;
        itemClickListener = itemClickRecListInterface;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_camp_inventory, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        String dayDate = campInventoryListBeanList.get(holder.getAdapterPosition()).getStr_date() + " " + campInventoryListBeanList.get(holder.getAdapterPosition()).getStr_day();

        holder.text_date.setText(dayDate);
        holder.text_booked.setText(campInventoryListBeanList.get(holder.getAdapterPosition()).getStr_booked_room());
        holder.text_available.setText(campInventoryListBeanList.get(holder.getAdapterPosition()).getStr_available_room());
        holder.text_total.setText(campInventoryListBeanList.get(holder.getAdapterPosition()).getStr_total_room());
        holder.txt_Type_text.setText("Cycle Status");

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

        TextView text_date, text_total, text_available, text_booked, txt_Type_text;
        LinearLayout lnt_box;

        public ViewHolder(View itemView) {
            super(itemView);

            text_date = itemView.findViewById(R.id.txt_date);
            text_total = itemView.findViewById(R.id.txt_total);
            text_available = itemView.findViewById(R.id.txt_available);
            text_booked = itemView.findViewById(R.id.txt_booked);
            lnt_box = itemView.findViewById(R.id.lnt_box);
            txt_Type_text = itemView.findViewById(R.id.txt_Type_text);

        }
    }

}

