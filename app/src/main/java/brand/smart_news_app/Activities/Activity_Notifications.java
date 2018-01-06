package brand.smart_news_app.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import brand.smart_news_app.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Activity_Notifications extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.back_notification)
    ImageView back_notification_view ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__notifications);
        ButterKnife.bind(this);
        back_notification_view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_notification:
                Intent i = new Intent(Activity_Notifications.this, Activity_Main.class);
                startActivity(i);
                break;

        }
    }
}
