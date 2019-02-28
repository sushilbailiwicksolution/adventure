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
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model_vendor.BungeeInventBeans;

public class BungeeInventRecyclerAdapter extends RecyclerView.Adapter<BungeeInventRecyclerAdapter.ViewHolder> {

    private ItemClickInterface itemClickListener;

    public interface ItemClickInterface {
        void onItemClick(int position);
    }

    private Context context;
    private List<BungeeInventBeans> bungeeInventList;

    public BungeeInventRecyclerAdapter(ItemClickInterface itemClickListener, Context context, List<BungeeInventBeans> bungeeInventList) {
        this.itemClickListener = itemClickListener;
        this.context = context;
        this.bungeeInventList = bungeeInventList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_bungee_invent,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        BungeeInventBeans bungeeInventBeans = bungeeInventList.get(holder.getAdapterPosition());

        String avail = "Seats : " + bungeeInventBeans.getAvailableSeats();
        holder.tvAvailableSeats.setText(avail);

        holder.tvStartingPoint.setText(bungeeInventBeans.getStartPoint());

        String time = "Time : " + bungeeInventBeans.getTime();
        holder.tvTiming.setText(time);

        holder.tvDate.setText(bungeeInventBeans.getSelectedDate());

        holder.llBungeeInvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return bungeeInventList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llBungeeInvent;
        TextView tvStartingPoint,tvAvailableSeats,tvTiming,tvDate;

        ViewHolder(View itemView){
            super(itemView);
            getUiObject(itemView);
        }

        private void getUiObject(View view){
            llBungeeInvent = view.findViewById(R.id.llBungeeInvent);
            tvStartingPoint = view.findViewById(R.id.tvStartingPoint);
            tvAvailableSeats = view.findViewById(R.id.tvAvailableSeats);
            tvTiming = view.findViewById(R.id.tvTiming);
            tvDate = view.findViewById(R.id.tvDate);
        }

    }

}
