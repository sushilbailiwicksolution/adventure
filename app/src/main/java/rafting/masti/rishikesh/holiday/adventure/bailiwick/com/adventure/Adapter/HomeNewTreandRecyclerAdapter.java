package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.model.newArrivalModal;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;


public class HomeNewTreandRecyclerAdapter extends RecyclerView.Adapter<HomeNewTreandRecyclerAdapter.ViewHolder> {

    Context context;
    List<newArrivalModal> homeProductsArrayList;
    //GoToProductDetails goToProductDetails;
    ProductIterface productClick;

    public interface ProductIterface {
        void getDetail(String catname, int position);
    }

    public interface RecyclerViewClickListener {

        void onClick(View view, int position);
    }

    public HomeNewTreandRecyclerAdapter(Context context, List<newArrivalModal> homeProductsArrayList, ProductIterface productClick) {

        this.context = context;
        this.homeProductsArrayList = homeProductsArrayList;
        this.productClick = productClick;
        Log.e("List Size", "" + homeProductsArrayList.size());

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home_new_arrival, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.text_product_name.setText(homeProductsArrayList.get(position).getStr_item_name());

        //   holder.text_original_price.setPaintFlags(holder.text_original_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.cat_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productClick.getDetail(homeProductsArrayList.get(position).getStr_item_name(), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeProductsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView cat_image;
        TextView text_product_name, text_final_price, text_original_price, text_discount;
        LinearLayout item_home_ll;
        private RecyclerViewClickListener mListener;

        public ViewHolder(View itemView) {
            super(itemView);
            item_home_ll = (LinearLayout) itemView.findViewById(R.id.item_home_ll);
            cat_image = (ImageView) itemView.findViewById(R.id.cat_image);

            text_product_name = (TextView) itemView.findViewById(R.id.text_product_name);
            text_final_price = (TextView) itemView.findViewById(R.id.text_final_price);
            //    text_original_price = (TextView) itemView.findViewById(R.id.text_original_price);
            text_discount = (TextView) itemView.findViewById(R.id.text_discount);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }

}
