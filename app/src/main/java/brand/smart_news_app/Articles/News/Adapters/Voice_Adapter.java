package brand.smart_news_app.Articles.News.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.List;

import brand.smart_news_app.Articles.News.Articles_Model.VideoArticle;
import brand.smart_news_app.R;

/**
 * Created by Hind on 12/20/2017.
 */

public class Voice_Adapter extends RecyclerView.Adapter<Voice_Adapter.MyViewHolder> {


    private Context mContext;
    private List<VideoArticle> CardList;

    public Voice_Adapter(Context mContext, List<VideoArticle> CardList) {
        this.mContext = mContext;
        this.CardList = CardList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.voice_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        VideoArticle videoArticle = CardList.get(position);
        holder.title.setText(videoArticle.getTitle());
//        holder.content.setText(videoArticle.getDiscription()+"");
        holder.date.setText(videoArticle.getCreatedAt());
        holder.comment.setText(videoArticle.getComments()+" تعليق");
        holder.share.setText(videoArticle.getShares()+" مشاركة");
        Uri uri = Uri.parse("http://smart-news-sa.com/news/public/uploads/article_videos"+videoArticle.getVideo());
        holder.video.setVideoURI(uri);
    }

    @Override
    public int getItemCount() {
        return CardList.size() ;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date , title, content , comment ,share ;
        public VideoView video ;

        public MyViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.Voice_C_date);
            title = (TextView) view.findViewById(R.id.Voice_card_title);
            video =  (VideoView)view.findViewById(R.id.Voice_card_video);
            comment = (TextView) view.findViewById(R.id.Voice_card_comment);
            share = (TextView) view.findViewById(R.id.Voice_card_share);
        }
    }
}
