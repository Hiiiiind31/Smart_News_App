package brand.smart_news_app.Adv_Slider;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import brand.smart_news_app.Articles.News.Adapters.Text_Adapter;
import brand.smart_news_app.Articles.News.Articles_Model.Slider;
import brand.smart_news_app.R;

/**
 * Created by Hind on 1/2/2018.
 */

public class Adv_Slider_Adapter extends RecyclerView.Adapter<Adv_Slider_Adapter.MyViewHolder> {
    Context mContext ;
    List<Slider> sliders ;

    public Adv_Slider_Adapter(Context mContext, List<Slider> sliders) {
        this.mContext = mContext;
        this.sliders = sliders;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adv_slider, parent, false);
        return new Adv_Slider_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

       String img =  sliders.get(position).getImage();
        Picasso.with(mContext).load("http://smart-news-sa.com/news/public/uploads/sliders/"+img).into(holder.slide);
    }


    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public int getItemCount() {
        return sliders.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView slide ;

        public MyViewHolder(View view) {
            super(view);
            slide = (ImageView) view.findViewById(R.id.slide_row);
           }
    }

}
