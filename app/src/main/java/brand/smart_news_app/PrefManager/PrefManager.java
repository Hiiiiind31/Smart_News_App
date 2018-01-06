package brand.smart_news_app.PrefManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

import brand.smart_news_app.R;

/**
 * Created by Hind on 12/18/2017.
 */

public class PrefManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context con ;

    // shared pref mode
    private int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "SmartNewsApp";
    private static final String user_name = "user_name";
    private static final String user_id = "user_id";
    private static final String user_email = "user_email";
    private static final String user_phone = "user_phone";
    private static final String user_pro_pic = "user_Profile_pic";
    private static final String like = "like";
    private static final String Share = "share";
    private static final String Comment = "comment";
    private static final String first = "first";


    public PrefManager(Context context) {
        Context _context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void isFirstTime(Boolean t){
        editor.putBoolean(first,t);
        editor.commit();
    }
    public boolean checkIsFirstTime (){
       return pref.getBoolean(first,true);
    }

    public void setUserData (String name , String email,String phone){
        editor.putString(user_name, name);
        editor.putString(user_email, email);
        editor.putString(user_phone, phone);
        editor.commit();
    }
    public void setUserId (String id){
         editor.putString(user_id, id);
        editor.commit();
    }
    public void setLikes_No (int i){
        editor.putInt(like, i);
        editor.commit();
    }
    public void setShares_No (int i){
        editor.putInt(Share, i);
        editor.commit();
    }
    public void setComments_No (int i){
        editor.putInt(Comment, i);
        editor.commit();
    }
    public void setUser_pro_pic (String pic){
        editor.putString(user_pro_pic, pic);
        editor.commit();
    }

    public String getUser_id() {
        return pref.getString(user_id,"");
    }
    public String getUser_name() {
        return pref.getString(user_name,"عبد الله مصطي");
    }
    public String getUser_email() {
        return pref.getString(user_email,"abd@gmail.com");
    }
    public String getUser_phone() {return pref.getString(user_phone, "01000233");}
    public String getUser_pro_pic(){

        return pref.getString(user_pro_pic,"");}

    public int getComments_No() {return pref.getInt(Comment,0);}
    public int getShares_No() {return pref.getInt(Share,0);}
    public int getLikes_No() {return pref.getInt(like,0);}

}
