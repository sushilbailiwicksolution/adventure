package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.interfaces.BikeBookInterface;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model.BikeBookBean;

public class BikeRecyclerAdapter extends RecyclerView.Adapter<BikeRecyclerAdapter.ViewHolder> {

    Context context;
    private List<BikeBookBean> bikeBookBeanList;
    private BikeBookInterface bikeBookInterface;

    public BikeRecyclerAdapter(Context context, List<BikeBookBean> bikeBookBeanList, BikeBookInterface bikeBookInterface) {
        this.context = context;
        this.bikeBookBeanList = bikeBookBeanList;
        this.bikeBookInterface = bikeBookInterface;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_bike_type,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final BikeBookBean bikeBookBean = bikeBookBeanList.get(holder.getAdapterPosition());

        Log.e("In bike RecAd => ",bikeBookBean.getQuantity()+" "+bikeBookBean.getBikePrice());

        if (bikeBookBeanList.size() > 1){
            holder.ivRemoveItem.setVisibility(View.VISIBLE);
        }else{
            holder.ivRemoveItem.setVisibility(View.GONE);
        }

        if(bikeBookBean.getBikeName() != null){
            holder.tvBikeName.setText(bikeBookBean.getBikeName());
        }

        if (bikeBookBean.getBikePrice() != null){
            holder.tvBikePrice.setText(bikeBookBean.getBikePrice());
        }

        if (bikeBookBean.getQuantity() != null){
            holder.tvQuanity.setText(bikeBookBean.getQuantity());
        }

        Log.e("Inside Adapter quan",""+Integer.parseInt(bikeBookBeanList.get(holder.getAdapterPosition()).getQuantity()));
        Log.e("Inside Adapter price",""+Integer.parseInt(bikeBookBean.getBikePrice()));
        int totalPrice = Integer.parseInt(bikeBookBeanList.get(holder.getAdapterPosition()).getQuantity())
                *Integer.parseInt(bikeBookBean.getBikePrice());
        bikeBookBeanList.get(holder.getAdapterPosition()).setTotalPrice(String.valueOf(totalPrice));

        holder.btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int max  = Integer.parseInt(bikeBookBean.getBikeMaxQuantity());
                int quantity = Integer.parseInt(holder.tvQuanity.getText().toString());
                if (quantity < max){
                    quantity += 1;
                    holder.tvQuanity.setText(String.valueOf(quantity));
                    bikeBookBeanList.get(holder.getAdapterPosition()).setQuantity(String.valueOf(quantity));
                    int totalPrice = quantity*Integer.parseInt(bikeBookBean.getBikePrice());
                    bikeBookBeanList.get(holder.getAdapterPosition()).setTotalPrice(String.valueOf(totalPrice));
                    bikeBookInterface.incrementClick(holder.getAdapterPosition(),String.valueOf(quantity),bikeBookBean.getBikePrice());
                }else{
                    Toast.makeText(context, "Sorry No More Bikes", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.btnDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(holder.tvQuanity.getText().toString());
                if (quantity >= 2){
                    quantity -= 1;
                    holder.tvQuanity.setText(String.valueOf(quantity));
                    bikeBookBeanList.get(holder.getAdapterPosition()).setQuantity(String.valueOf(quantity));
                    int totalPrice = quantity*Integer.parseInt(bikeBookBean.getBikePrice());
                    bikeBookBeanList.get(holder.getAdapterPosition()).setTotalPrice(String.valueOf(totalPrice));
                    bikeBookInterface.decrementClick(holder.getAdapterPosition(),String.valueOf(quantity),bikeBookBean.getBikePrice());
                }else {
                    Toast.makeText(context, "At least one bike is required", Toast.LENGTH_SHORT).show();
                }
            }
        });

       /* holder.tvAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bikeBookInterface.addNewRow(holder.getAdapterPosition());
            }
        });*/

        holder.ivRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bikeBookInterface.deleteRow(holder.getAdapterPosition());
                if (bikeBookBeanList.size() == 1){
                    holder.ivRemoveItem.setVisibility(View.GONE);
                }
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return bikeBookBeanList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvBikePrice,tvQuanity/*,tvAddMore*/;
        Button btnIncrement,btnDecrement;
        ImageView ivRemoveItem;
        TextView tvBikeName;

        public ViewHolder(View itemView) {
            super(itemView);
            getUiObject(itemView);
        }

        private void getUiObject(View view){
            tvBikeName = view.findViewById(R.id.tvBikeName);
            tvBikePrice = view.findViewById(R.id.tvBikePrice);
            tvQuanity = view.findViewById(R.id.tvQuanity);
            btnIncrement = view.findViewById(R.id.btnIncrement);
            btnDecrement = view.findViewById(R.id.btnDecrement);
         /*   tvAddMore = view.findViewById(R.id.tvAddMore);*/
            ivRemoveItem = view.findViewById(R.id.ivRemoveItem);
        }
    }

}
