package brand.smart_news_app.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import brand.smart_news_app.R;
import butterknife.BindView;
import butterknife.ButterKnife;

import static brand.smart_news_app.Activities.Activity_Signin.VALID_EMAIL_ADDRESS_REGEX;

public class Activity_ResetPassword extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.Email_f_Set_pass_)
    EditText Email_f_Set_pass_view;
    @BindView(R.id.Send_code)
    Button Send_code_view;
    @BindView(R.id.back_resetPass)
    ImageView back_resetPass_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__reset_password);
        ButterKnife.bind(this);
        Send_code_view.setOnClickListener(this);
        back_resetPass_view.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Send_code:
                check_validation();
                break;
            case R.id.back_resetPass:
                Intent i = new Intent(Activity_ResetPassword.this, Activity_Signin.class);
                startActivity(i);
                break;
        }
    }

    private void check_validation() {
        // Reset errors.
        Email_f_Set_pass_view.setError(null);

        // Store values at the time of the login attempt.
        String U_email = Email_f_Set_pass_view.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(U_email)) {
            Email_f_Set_pass_view.setError(getString(R.string.error_field_required));
            focusView = Email_f_Set_pass_view;
            cancel = true;
        } else if (!validate(U_email)) {
            Email_f_Set_pass_view.setError(getString(R.string.error_invalid_email));
            focusView = Email_f_Set_pass_view;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            /// showProgress(true);
            Volley_reset_password(U_email);

        }
    }
    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
    private void Volley_reset_password(final String email) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        String url = "http://smart-news-sa.com/news/public/api/user/forget_password";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // check the response from server.
                try {
                    JSONObject json = new JSONObject(response);
                    if(json.get("success").equals(0)){
                        Toast.makeText(Activity_ResetPassword.this, "Cant find email", Toast.LENGTH_SHORT).show();
                    }else if (json.get("success").equals(1)){
                        Toast.makeText(Activity_ResetPassword.this, " activation code sends successfully check your email", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Activity_ResetPassword.this, Activity_Receive_Code.class);
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
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("reset",error.getMessage());

            }
        }){
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


}
