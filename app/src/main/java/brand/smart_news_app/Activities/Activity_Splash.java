package brand.smart_news_app.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import brand.smart_news_app.PrefManager.PrefManager;
import brand.smart_news_app.R;

public class Activity_Splash extends AppCompatActivity {
    PrefManager prefManager ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__splash);
        prefManager = new PrefManager(this);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if (prefManager.checkIsFirstTime()){
                    Intent i = new Intent(Activity_Splash.this, Activity_Signin.class);
                    startActivity(i);

                }else {
                    Intent i = new Intent(Activity_Splash.this, Activity_Main.class);
                    startActivity(i);
                }
                // close this activity
                finish();
            }
        }, 1000);
    }
    }