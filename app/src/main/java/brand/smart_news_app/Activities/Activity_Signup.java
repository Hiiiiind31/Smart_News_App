package brand.smart_news_app.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.DoubleBounce;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import brand.smart_news_app.PrefManager.PrefManager;
import brand.smart_news_app.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static brand.smart_news_app.Activities.Activity_Signin.VALID_EMAIL_ADDRESS_REGEX;

public class Activity_Signup extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.U_Name_id)
    EditText U_Name_view ;
    @BindView(R.id.U_Phone_id)
    EditText U_Phone_view ;
    @BindView(R.id.U_Email_id)
    EditText U_Email_view ;
    @BindView(R.id.U_Password_id)
    EditText U_Password_view ;
    @BindView(R.id.Signup_id)
    Button Sign_up_view ;
    @BindView(R.id.U_Profile_Pic)
    ImageView U_Profile_Pic_view;
    @BindView(R.id.back_signUp)
    ImageView back_signUp_view;
    @BindView(R.id.Sign_up_scrollView_layout)
    ScrollView Sign_up_scrollView_layout ;
    @BindView(R.id.Frame_layout_id)
    FrameLayout Frame_layout ;
    @BindView(R.id.progressBar)
    ProgressBar progressBar ;
    private int pick_up = 10 ;
    private PrefManager pref;
    private String img_str;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__signup);
        ButterKnife.bind(this);
        pref = new PrefManager(this);
        Sign_up_view.setOnClickListener(this);
        U_Profile_Pic_view.setOnClickListener(this);
        back_signUp_view.setOnClickListener(this);
        //progress bar
        Circle doubleBounce = new Circle();
        progressBar.setIndeterminateDrawable(doubleBounce);

        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.prof_pic);
        setNewProfilePic(bitmap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Signup_id :
                attemptLogin();
                break;
            case R.id.U_Profile_Pic :
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,pick_up);
                break;
            case R.id.back_signUp :
                Intent h = new Intent(Activity_Signup.this, Activity_Signin.class);
                startActivity(h);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         switch(requestCode) {
            case 10 :
                if (resultCode==RESULT_OK){
                    Uri URI = data.getData();
                    String[] projection ={MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(URI,projection,null,null,null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        int column_index = cursor.getColumnIndex(projection[0]);
                        String file_path = cursor.getString(column_index);
                        cursor.close();
                        Bitmap bitmap = BitmapFactory.decodeFile(file_path);
                        setNewProfilePic(bitmap);
                    };
                }
                break ;
            default:
                break;
        }
    }

    private void attemptLogin() {
        // Reset errors.
        U_Name_view.setError(null);
        U_Phone_view.setError(null);
        U_Email_view.setError(null);
        U_Password_view.setError(null);

        // Store values at the time of the login attempt.
        String U_name = U_Name_view.getText().toString();
        String U_phone = U_Phone_view.getText().toString();
        String U_email = U_Email_view.getText().toString();
        String U_password = U_Password_view.getText().toString();

        boolean cancel = false;
        View focusView = null;
        // Check for a valid name, if the user entered one.
        if (TextUtils.isEmpty(U_name)) {
            U_Name_view.setError(getString(R.string.error_field_required));
            focusView = U_Name_view;
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(U_password) && !isPasswordValid(U_password)) {
            U_Password_view.setError(getString(R.string.error_invalid_password));
            focusView = U_Password_view;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(U_email)) {
            U_Email_view.setError(getString(R.string.error_field_required));
            focusView = U_Email_view;
            cancel = true;
        } else if (!validate(U_email)) {
            U_Email_view.setError(getString(R.string.error_invalid_email));
            focusView = U_Email_view;
            cancel = true;
        }
        // Check for a valid phone number, if the user entered one.
        if (TextUtils.isEmpty(U_phone)) {
            U_Phone_view.setError(getString(R.string.error_field_required));
            focusView = U_Phone_view;
            cancel = true;
        } else if (!isPhoneNumberValid(U_phone)) {
            U_Phone_view.setError(getString(R.string.error_invalid_phone));
            focusView = U_Phone_view;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
             showProgress(true);
            Volley_SignUp(U_name, U_email,U_password,U_phone,img_str);


        }
    }

    private void showProgress(boolean b) {
        if (b){
            Frame_layout.setVisibility(View.VISIBLE);
            Sign_up_scrollView_layout.setBackgroundColor(R.color.transparent_color);

        }else {
            Frame_layout.setVisibility(View.GONE);
            Sign_up_scrollView_layout.setBackgroundColor(R.color.colorWhite);
        }

    }

    private void Volley_SignUp(final String u_name, final String u_email, final String u_password, final String u_phone,final String Img_string) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        String url = "http://smart-news-sa.com/news/public/api/user/register";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // check the response from server.
                        try {
                            showProgress(false);
                            JSONObject json = new JSONObject(response);
                            if(json.get("success").equals(0)){
                                Toast.makeText(Activity_Signup.this, ".", Toast.LENGTH_SHORT).show();
                            }else if (json.get("success").equals(2)){
                                Toast.makeText(Activity_Signup.this, "Email exist before.", Toast.LENGTH_SHORT).show();
                            }else if (json.get("success").equals(3)){
                                Toast.makeText(Activity_Signup.this, "Phone exist before.", Toast.LENGTH_SHORT).show();
                            }else if (json.get("success").equals(4)){
                                Toast.makeText(Activity_Signup.this, "Email and phone exist before.", Toast.LENGTH_SHORT).show();
                            }else if (json.get("success").equals(1)){
                                //Wrong credentials according to your server logic. Prompt to re-enter the credentials
                                Toast.makeText(Activity_Signup.this, "SUCCESS.", Toast.LENGTH_SHORT).show();
                                String user_id = json.getString("user_id");
                                String name = json.getString("name");
                                String email = json.getString("email");
                                String phone = json.getString("phone");
                                String image=json.getString("profile_image");
                                pref.setUser_pro_pic(image);
                                pref.setUserId(user_id);
                                pref.setUserData(name,email,phone);
                                pref.isFirstTime(false);
                                Intent i = new Intent(Activity_Signup.this,Activity_Main.class);
                                startActivity(i);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                       Log.d("data",response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error in sending requst
                Toast.makeText(getApplicationContext(),""+ error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("SignUp",error.getMessage());
            }
        }){
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", u_name);
                params.put("email", u_email);
                params.put("phone",u_phone);
                params.put("password",u_password);
                params.put("profile_image",img_str);
                return params;
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 8 ;
    }

    private boolean isPhoneNumberValid (String phone){
        return  phone.startsWith("966")&& phone.length()==13;
    }

    public void H_signinActivity(View v ){
        Intent i = new Intent(Activity_Signup.this,Activity_Signin.class);
        startActivity(i);
    }

    public void setNewProfilePic(Bitmap newProfilePic) {
        //code image to string
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        newProfilePic.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] image=stream.toByteArray();
        img_str = Base64.encodeToString(image, 0);
        //decode string to image
        byte[] imageAsBytes = Base64.decode(img_str.getBytes(), Base64.DEFAULT);
        U_Profile_Pic_view.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes,0, imageAsBytes.length));
        Log.d("img",img_str);
        pref.setUser_pro_pic(img_str);

    }
}
