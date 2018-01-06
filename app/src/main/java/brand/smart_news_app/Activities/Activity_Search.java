package brand.smart_news_app.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import brand.smart_news_app.R;

public class Activity_Search extends AppCompatActivity implements View.OnClickListener {


    ImageView clear , search ;
    TextView  search_txt , cancel_search ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__search);

        clear = findViewById(R.id.clear_search_text);
        search = findViewById(R.id.search_about);
        cancel_search = findViewById(R.id.cancel_search);
        search_txt = findViewById(R.id.search_text);

        clear.setOnClickListener(this);
        search.setOnClickListener(this);
        cancel_search.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.clear_search_text:
                search_txt.setText("");
                break;
            case R.id.search_about :
                break;
            case R.id.cancel_search:
                finish();
                break;
        }
    }
}
