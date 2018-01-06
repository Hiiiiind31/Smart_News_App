package brand.smart_news_app.Tabs_Fragments.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import brand.smart_news_app.Adv_Slider.Adv_Slider_Adapter;
import brand.smart_news_app.Activities.Activity_V_Details;
import brand.smart_news_app.Articles.News.Adapters.Text_Adapter;
import brand.smart_news_app.Articles.News.Articles_Model.ImageArticle;
import brand.smart_news_app.Articles.News.Articles_Model.Slider;
import brand.smart_news_app.Articles.News.Articles_Model.TextArticle;
import brand.smart_news_app.Articles.News.Articles_Model.VideoArticle;
import brand.smart_news_app.Articles.News.Adapters.Photo_Adapter;
import brand.smart_news_app.Articles.News.Adapters.Voice_Adapter;
import brand.smart_news_app.R;
import brand.smart_news_app.RecyclerItemClickListener;

public class Fragment_Main extends Fragment {

    private Button All_News_view, Article_view, Voice_News_view, Photo_News_view;
    private RecyclerView recyclerView1, recyclerView2, recyclerView3 ,Adv_recycleView;
    public static List<TextArticle> article_Card_ModelList;
    public static List<ImageArticle> photo_card_modelList;
    public static List<VideoArticle> voice_Card_ModelList;
    private List<Slider>  Adv_slider_modelList ;
    private Text_Adapter A_adapter;
    private Photo_Adapter P_adapter;
    private Voice_Adapter V_adapter;
    private Adv_Slider_Adapter Adv_adapter ;


    private void all_elements(View inflate) {
        recyclerView1 = (RecyclerView) inflate.findViewById(R.id.recycler_view1);
        recyclerView2 = (RecyclerView) inflate.findViewById(R.id.recycler_view2);
        recyclerView3 = (RecyclerView) inflate.findViewById(R.id.recycler_view3);
        Adv_recycleView = (RecyclerView) inflate.findViewById(R.id.adv_slider);

        All_News_view = inflate.findViewById(R.id.All_News);
        Article_view = inflate.findViewById(R.id.Article_News);
        Voice_News_view = inflate.findViewById(R.id.Voiced_News);
        Photo_News_view = inflate.findViewById(R.id.Photo_News);

        article_Card_ModelList = new ArrayList<>();
        photo_card_modelList = new ArrayList<>();
        voice_Card_ModelList = new ArrayList<>();
        Adv_slider_modelList = new ArrayList<>();
    }

    private void Onclick() {
        recyclerView1.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getActivity(), position + "", Toast.LENGTH_LONG).show();
                        Intent i= new Intent(getActivity(),Activity_V_Details.class);
                        i.putExtra("Post_category",0);
                        i.putExtra("Post_position",0);
                        i.putExtra("Post_id",article_Card_ModelList.get(position).getId());
                        startActivity(i);
                    }
                })
        );
        recyclerView2.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getActivity(), position + "", Toast.LENGTH_LONG).show();
                        Intent i= new Intent(getActivity(),Activity_V_Details.class);
                        i.putExtra("Post_category",1);
                        i.putExtra("Post_id",photo_card_modelList.get(position).getId());
                        startActivity(i);
                    }
                })
        );
        recyclerView3.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getActivity(), position + "", Toast.LENGTH_LONG).show();
                        Intent i= new Intent(getActivity(),Activity_V_Details.class);
                        i.putExtra("Post_category",2);
                        i.putExtra("Post_id",voice_Card_ModelList.get(position).getId());
                        startActivity(i);
                    }
                })
        );

        All_News_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set_All_News_List();
            }
        });
        Article_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView1.setVisibility(View.VISIBLE);
                recyclerView2.setVisibility(View.GONE);
                recyclerView3.setVisibility(View.GONE);
                setVerticalLayout(recyclerView1);
                recyclerView1.setAdapter(A_adapter);
                //prepareArticleCards();
            }
        });
        Photo_News_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView2.setVisibility(View.VISIBLE);
                recyclerView1.setVisibility(View.GONE);
                recyclerView3.setVisibility(View.GONE);
                setVerticalLayout(recyclerView2);
                recyclerView2.setAdapter(P_adapter);
                //preparePhotoCards();

            }
        });
        Voice_News_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView3.setVisibility(View.VISIBLE);
                recyclerView1.setVisibility(View.GONE);
                recyclerView2.setVisibility(View.GONE);
                setVerticalLayout(recyclerView3);
                recyclerView3.setAdapter(V_adapter);
                //prepareVoiceCards();
            }
        });

    }

    private void setVerticalLayout(RecyclerView recycler1) {
        LinearLayoutManager V_layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycler1.setLayoutManager(V_layoutManager);
        recycler1.setItemAnimator(new DefaultItemAnimator());
    }

    private void setHorizontalLayout(RecyclerView recycler1) {
        LinearLayoutManager H_layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recycler1.setLayoutManager(H_layoutManager);
        recycler1.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_fragment__main, container, false);
        all_elements(inflate);
        Set_All_News_List();
        Onclick();
        Volley_all_news();
        return inflate;
    }

    public void Volley_all_news() {

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://smart-news-sa.com/news/public/api/user/all_articles";


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // check the response from server.
                try {
                    JSONObject json = new JSONObject(response);
                     JSONArray Adv_slider = json.getJSONArray("slider");
                    JSONArray text_articles = json.getJSONArray("text_articles");
                    JSONArray image_articles = json.getJSONArray("image_articles");
                    JSONArray video_articles = json.getJSONArray("video_articles");

                    for (int i = 0; i < Adv_slider.length(); i++) {
                        JSONObject jsonObject = Adv_slider.getJSONObject(i);
                        Slider slider = new Slider();
                        slider.setId(jsonObject.getInt("id"));
                        slider.setImage(jsonObject.getString("image"));
                        Adv_slider_modelList.add(slider);
                    }
                    for (int i = 0; i < text_articles.length(); i++) {
                        JSONObject jsonObject = text_articles.getJSONObject(i);

                        TextArticle Text_Article = new TextArticle();
                        Text_Article.setId(jsonObject.getInt("id"));
                        Text_Article.setTitle(jsonObject.getString("title"));
                        Text_Article.setDiscription(jsonObject.getString("discription"));
                        Text_Article.setCreatedAt(jsonObject.getString("created_at"));
                        Text_Article.setLikes(jsonObject.getInt("likes"));
                        Text_Article.setComments(jsonObject.getInt("comments"));
                        Text_Article.setShares(jsonObject.getInt("shares"));

                        article_Card_ModelList.add(Text_Article);
                    }
                    for (int i = 0; i < image_articles.length(); i++) {
                        JSONObject jsonObject = image_articles.getJSONObject(i);
                        ImageArticle Image_Article = new ImageArticle();
                        Image_Article.setId(jsonObject.getInt("id"));
                        Image_Article.setTitle(jsonObject.getString("title"));
                        Image_Article.setDiscription(jsonObject.getString("discription"));
                        Image_Article.setCreatedAt(jsonObject.getString("created_at"));
                        Image_Article.setImage(jsonObject.getString("image"));
                        Image_Article.setLikes(jsonObject.getInt("likes"));
                        Image_Article.setComments(jsonObject.getInt("comments"));
                        Image_Article.setShares(jsonObject.getInt("shares"));
                        photo_card_modelList.add(Image_Article);
                    }
                    for (int i = 0; i < video_articles.length(); i++) {
                        JSONObject jsonObject = video_articles.getJSONObject(i);
                        VideoArticle Video_Article = new VideoArticle();
                        Video_Article.setId(jsonObject.getInt("id"));
                        Video_Article.setTitle(jsonObject.getString("title"));
                        Video_Article.setDiscription(jsonObject.getString("discription"));
                        Video_Article.setCreatedAt(jsonObject.getString("created_at"));
                        Video_Article.setVideo(jsonObject.getString("video"));
                        Video_Article.setLikes(jsonObject.getInt("likes"));
                        Video_Article.setComments(jsonObject.getInt("comments"));
                        Video_Article.setShares(jsonObject.getInt("shares"));
                        voice_Card_ModelList.add(Video_Article);
                    }
                    Adv_adapter = new Adv_Slider_Adapter(getActivity(),Adv_slider_modelList);
                    Adv_recycleView.setAdapter(Adv_adapter);
                    A_adapter = new Text_Adapter(getActivity(), article_Card_ModelList);
                    recyclerView1.setAdapter(A_adapter);
                    P_adapter = new Photo_Adapter(getActivity(), photo_card_modelList);
                    recyclerView2.setAdapter(P_adapter);
                    V_adapter = new Voice_Adapter(getActivity(), voice_Card_ModelList);
                    recyclerView3.setAdapter(V_adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("data", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error in sending requst
                Toast.makeText(getContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("SignUp", error.getMessage());
            }
        });
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void Set_All_News_List() {
        recyclerView1.setVisibility(View.VISIBLE);
        recyclerView2.setVisibility(View.VISIBLE);
        recyclerView3.setVisibility(View.VISIBLE);

        setHorizontalLayout(recyclerView1);
        recyclerView1.setAdapter(A_adapter);
        //prepareArticleCards();

        setHorizontalLayout(recyclerView2);
        recyclerView2.setAdapter(P_adapter);
        //preparePhotoCards();

        setHorizontalLayout(recyclerView3);
        recyclerView3.setAdapter(V_adapter);
        //prepareVoiceCards();

    }

}
