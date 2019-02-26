package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;

/**
 * Created by Prince on 29-01-2018.
 */

public class CampFacilityAdapter extends RecyclerView.Adapter<CampFacilityAdapter.ViewHolder> {

    Context context;
    List<String> cartListBeanList;
    Activity activity;



    public CampFacilityAdapter(Context context, List<String> cartListBeanList) {
        this.context = context;
        this.cartListBeanList = cartListBeanList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cst_list_facilities, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.title_text.setText(cartListBeanList.get(position).toString());

//        Log.e("image url ", UtilsUrl.IMAGEBASE_URL + cartListBeanList.get(position).getStr_image_url());
        //      Glide.with(context).load(UtilsUrl.IMAGEBASE_URL + cartListBeanList.get(position).getStr_image_url()).error(R.drawable.camp_banner).into(holder.img_logo);




    }

    @Override
    public int getItemCount() {
        return cartListBeanList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title_text;
      //  CardView crd_menu;


        public ViewHolder(View itemView) {
            super(itemView);

            title_text = (TextView) itemView.findViewById(R.id.txt_facilites);
         //   crd_menu = (CardView)itemView.findViewById(R.id.crd_menu);

        }


    }

}


