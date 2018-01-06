package brand.smart_news_app.Activities;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

import java.util.Set;

import brand.smart_news_app.R;
import butterknife.BindView;
import butterknife.ButterKnife;

import static brand.smart_news_app.Tabs_Fragments.Fragments.Fragment_Main.article_Card_ModelList;
import static brand.smart_news_app.Tabs_Fragments.Fragments.Fragment_Main.photo_card_modelList;
import static brand.smart_news_app.Tabs_Fragments.Fragments.Fragment_Main.voice_Card_ModelList;

public class Activity_V_Details extends AppCompatActivity {
    @BindView(R.id.videoView)
    UniversalVideoView mVideoView;
    @BindView(R.id.media_controller)
    UniversalMediaController mMediaController;
    @BindView(R.id.date_id)
    TextView date_view;
    @BindView(R.id.title_id)
    TextView title_view;
    @BindView(R.id.description_id)
    TextView desc_view;
    @BindView(R.id.img_id)
    ImageView img_view;
    @BindView(R.id.video_frame_id)
    FrameLayout video_frame_view ;

    String TAG = "kkkk";
    String VideoURL = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__v__details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        int post_category = intent.getIntExtra("Post_category", 0);
        int post_id = intent.getIntExtra("Post_id", 0);
        int post_position = intent.getIntExtra("Post_position",0);
        Check_Posts_Category(post_category , post_id , post_position);
        Set_Posts_details(post_id);
    }

    private void Set_Posts_details(int post_id) {


    }

    private void Check_Posts_Category(int postCategory, int postId,int postPosition) {
        switch (postCategory) {
            case 0:
                img_view.setVisibility(View.GONE);
                video_frame_view.setVisibility(View.GONE);
                date_view.setText(article_Card_ModelList.get(postPosition).getCreatedAt());
                title_view.setText(article_Card_ModelList.get(postPosition).getTitle());
              //  desc_view.setText(article_Card_ModelList.get(postPosition).getDiscription());
                break;
            case 1:
                img_view.setVisibility(View.VISIBLE);
                video_frame_view.setVisibility(View.VISIBLE);
                date_view.setText(photo_card_modelList.get(postPosition).getCreatedAt());
                title_view.setText(photo_card_modelList.get(postPosition).getTitle());
                String image = photo_card_modelList.get(postPosition).getImage();
                //  desc_view.setText(article_Card_ModelList.get(postPosition).getDiscription());
                Picasso.with(this).load("http://smart-news-sa.com/news/public/uploads/article_images/"+image).into(img_view);

                break;
            case 2:
                img_view.setVisibility(View.VISIBLE);
                video_frame_view.setVisibility(View.VISIBLE);
                date_view.setText(voice_Card_ModelList.get(postPosition).getCreatedAt());
                title_view.setText(voice_Card_ModelList.get(postPosition).getTitle());
                //  desc_view.setText(article_Card_ModelList.get(postPosition).getDiscription());

                break;
        }

    }

    public void Set_Video() {
        mVideoView.setMediaController(mMediaController);
        mVideoView.setVideoURI(Uri.parse(VideoURL));
        mVideoView.setVideoViewCallback(new UniversalVideoView.VideoViewCallback() {
            @Override
            public void onScaleChange(boolean isFullscreen) {
            }

            @Override
            public void onPause(MediaPlayer mediaPlayer) { // Video pause
                Log.d(TAG, "onPause UniversalVideoView callback");
            }

            @Override
            public void onStart(MediaPlayer mediaPlayer) { // Video start/resume to play
                Log.d(TAG, "onStart UniversalVideoView callback");
            }

            @Override
            public void onBufferingStart(MediaPlayer mediaPlayer) {// steam start loading
                Log.d(TAG, "onBufferingStart UniversalVideoView callback");
            }

            @Override
            public void onBufferingEnd(MediaPlayer mediaPlayer) {// steam end loading
                Log.d(TAG, "onBufferingEnd UniversalVideoView callback");
            }
        });

    }
}
