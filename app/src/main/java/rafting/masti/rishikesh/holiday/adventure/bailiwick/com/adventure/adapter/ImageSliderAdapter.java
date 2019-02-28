package rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import rafting.masti.rishikesh.holiday.adventure.bailiwick.com.adventure.R;

public class ImageSliderAdapter extends PagerAdapter {

    private ArrayList<Integer> product_images;
    private LayoutInflater layoutInflater;
    Context context;

    public ImageSliderAdapter(ArrayList<Integer> product_images, Context context) {
        this.product_images = product_images;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return product_images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View imageLayout = layoutInflater.inflate(R.layout.product_slider_images,container,false);
        ImageView imageView = imageLayout.findViewById(R.id.image);
        imageView.setImageResource(product_images.get(position));
        container.addView(imageLayout,0);
        return imageLayout;
    }
}
