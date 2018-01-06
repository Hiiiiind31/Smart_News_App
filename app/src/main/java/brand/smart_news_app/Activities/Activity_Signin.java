package brand.smart_news_app.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.style.Circle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import brand.smart_news_app.PrefManager.PrefManager;
import brand.smart_news_app.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Activity_Signin extends AppCompatActivity {

    @BindView(R.id.L_U_Email_id)
    EditText L_U_Email_view;
    @BindView(R.id.L_U_Password_id)
    EditText L_U_Password_view;
    @BindView(R.id.Signin_id)
    Button Sign_in_view;
    @BindView(R.id.Frame_layout_id3)
    FrameLayout Frame_layout ;
    @BindView(R.id.progressBar3)
    ProgressBar progressBar ;
    PrefManager pref;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__signin);
        ButterKnife.bind(this);
        //progress bar
        Circle doubleBounce = new Circle();
        progressBar.setIndeterminateDrawable(doubleBounce);

        pref = new PrefManager(this);
        Sign_in_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

    }

    private void attemptLogin() {
        // Reset errors.
        L_U_Email_view.setError(null);
        L_U_Password_view.setError(null);
        // Store values at the time of the login attempt.
        String U_email = L_U_Email_view.getText().toString();
        String U_password = L_U_Password_view.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(U_email)) {
            L_U_Email_view.setError(getString(R.string.error_field_required));
            focusView = L_U_Email_view;
            cancel = true;
        } else if (!validate(U_email)) {
            L_U_Email_view.setError(getString(R.string.error_invalid_email));
            focusView = L_U_Email_view;
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(U_password) && !isPasswordValid(U_password)) {
            L_U_Password_view.setError(getString(R.string.error_invalid_password));
            focusView = L_U_Password_view;
            cancel = true;
        }
        if (cancel) {

            focusView.requestFocus();
        } else {
            /// showProgress(true);
            Volley_SignIn(U_email, U_password);
        }
    }

    private void Volley_SignIn(final String u_email, final String u_password) {
       showProgress(true);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://smart-news-sa.com/news/public/api/user/login";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            showProgress(false);
                            JSONObject json = new JSONObject(response);
                            if (json.get("success").equals(0)) {
                                //New user added. Now either start activity to login or your main activity
                                Toast.makeText(Activity_Signin.this, "Login failed", Toast.LENGTH_SHORT).show();
                            }
                            else if(json.get("success").equals(1)){
                                String user_id = json.get("user_id").toString();
                                String name = json.get("name").toString();
                                String email = json.get("email").toString();
                                String profile_image = json.get("profile_image").toString();
                                String phone = json.get("phone").toString();
                                pref.setUserId(user_id);
                                pref.setUserData(name,email,phone);
                                pref.setUser_pro_pic(profile_image);
                                pref.isFirstTime(false);
                                Intent i = new Intent(Activity_Signin.this,Activity_Main.class);
                                startActivity(i);
                            }
                            else if (json.get("success").equals(2)) {
                                Toast.makeText(Activity_Signin.this, "account not activated by admin", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error in sending requst
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("SignIn",error.getMessage());

            }
        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", u_email);
                params.put("password", u_password);
                return params;
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void showProgress(boolean b) {
        if (b){
            Frame_layout.setVisibility(View.VISIBLE);

        }else {
            Frame_layout.setVisibility(View.GONE);
        }

    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 8;
    }

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public void H_reserpassword_Activity(View v) {
        Intent i = new Intent(Activity_Signin.this, Activity_ResetPassword.class);
        startActivity(i);
    }

    public void H_signin(View v) {
        Intent i = new Intent(Activity_Signin.this, Activity_Signup.class);
        startActivity(i);
    }


}
