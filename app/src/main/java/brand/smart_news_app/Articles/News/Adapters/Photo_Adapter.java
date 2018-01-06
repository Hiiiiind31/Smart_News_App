package brand.smart_news_app.Articles.News.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import brand.smart_news_app.Articles.News.Articles_Model.ImageArticle;
import brand.smart_news_app.R;

/**
 * Created by Hind on 12/20/2017.
 */

public class Photo_Adapter extends RecyclerView.Adapter<Photo_Adapter.MyViewHolder> {


    private Context mContext;
    List<ImageArticle> CardList;

    public Photo_Adapter(Context mContext, List<ImageArticle> CardList) {
        this.mContext = mContext;
        this.CardList = CardList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ImageArticle imageArticle = CardList.get(position);
        holder.title.setText(imageArticle.getTitle());
//        holder.content.setText(imageArticle.getDiscription()+"");
        holder.date.setText(imageArticle.getCreatedAt()+"");
        holder.comment.setText(imageArticle.getComments()+" تعليق");
        holder.share.setText(imageArticle.getShares()+" مشاركة");
        String image = imageArticle.getImage();
        Picasso.with(mContext).load("http://smart-news-sa.com/news/public/uploads/article_images/"+image).into(holder.photo);



    }

    @Override
    public int getItemCount() {
        return CardList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date , title, content , comment ,share ;
        public ImageView photo ;

        public MyViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.Photo_C_date);
            title = (TextView) view.findViewById(R.id.Photo_card_title);
            photo = (ImageView) view.findViewById(R.id.Photo_card_photo);
            comment = (TextView) view.findViewById(R.id.Photo_card_comment);
            share = (TextView) view.findViewById(R.id.Photo_card_share);
        }
    }
}
