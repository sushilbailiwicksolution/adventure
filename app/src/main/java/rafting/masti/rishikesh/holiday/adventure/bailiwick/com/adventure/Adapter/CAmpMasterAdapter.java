package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.Modal.CampMasterModel;
import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;

/**
 * Created by Prince on 25-01-2018.
 */

public class CAmpMasterAdapter extends RecyclerView.Adapter<CAmpMasterAdapter.ViewHolder> {

    Context context;
    List<CampMasterModel> cartListBeanList;
    Activity activity;

    private ItemClickMasterMenuListInterface itemClickListener;

    public interface ItemClickMasterMenuListInterface {
        void onItemMasterClick(int position);
    }


    public CAmpMasterAdapter(Context context, List<CampMasterModel> cartListBeanList, ItemClickMasterMenuListInterface itemClickRecListInterface) {
        this.context = context;
        this.cartListBeanList = cartListBeanList;
        itemClickListener = itemClickRecListInterface;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_camp_master, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.title_text.setText(cartListBeanList.get(position).getStr_Camp_type());

//        Log.e("image url ", UtilsUrl.IMAGEBASE_URL + cartListBeanList.get(position).getStr_image_url());
        //      Glide.with(context).load(UtilsUrl.IMAGEBASE_URL + cartListBeanList.get(position).getStr_image_url()).error(R.drawable.camp_banner).into(holder.img_logo);

        holder.crd_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemMasterClick(position);
            }
        });



    }

    @Override
    public int getItemCount() {
        return cartListBeanList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title_text;
        CardView crd_menu;


        public ViewHolder(View itemView) {
            super(itemView);

            title_text = (TextView) itemView.findViewById(R.id.txt_tittle);
            crd_menu = (CardView)itemView.findViewById(R.id.crd_menu);

        }


    }

}

