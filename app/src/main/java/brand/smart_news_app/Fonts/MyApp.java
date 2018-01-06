package brand.smart_news_app.Fonts;

import android.app.Application;

import brand.smart_news_app.Fonts.TypefaceUtil;

/**
 * Created by Hind on 12/13/2017.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "Cairo_SemiBold.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
    }

}
