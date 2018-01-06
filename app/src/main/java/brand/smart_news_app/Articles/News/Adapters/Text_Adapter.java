package brand.smart_news_app.Articles.News.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import brand.smart_news_app.Articles.News.Articles_Model.TextArticle;
import brand.smart_news_app.R;

/**
 * Created by Hind on 12/20/2017.
 */

public class Text_Adapter extends RecyclerView.Adapter<Text_Adapter.MyViewHolder> {


    private Context mContext;
    private List<TextArticle> CardList;

    public Text_Adapter(Context mContext, List<TextArticle> CardList) {
        this.mContext = mContext;
        this.CardList = CardList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TextArticle textArticle = CardList.get(position);
        holder.title.setText(textArticle.getTitle());
        holder.content.setText(textArticle.getDiscription());
        holder.date.setText(textArticle.getCreatedAt()+"");
        holder.comment.setText(textArticle.getComments()+" تعليق");
        holder.share.setText(textArticle.getShares()+" مشاركة");

    }

    @Override
    public int getItemCount() {
        return CardList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date , title, content , comment ,share ;

        public MyViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.C_date);
            title = (TextView) view.findViewById(R.id.C_title);
            content = (TextView) view.findViewById(R.id.C_content);
            comment = (TextView) view.findViewById(R.id.C_comment);
            share = (TextView) view.findViewById(R.id.C_share);
        }
    }
}
