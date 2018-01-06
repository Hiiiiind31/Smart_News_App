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

import brand.smart_news_app.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Activity_Change_Password extends AppCompatActivity  implements View.OnClickListener{

    @BindView(R.id.change_pass_1)
    EditText change_pass_1_view ;
    @BindView(R.id.change_pass_2)
    EditText change_pass_2_view ;
    @BindView(R.id.Save_new_pass)
    Button Save_new_pass_view ;
    @BindView(R.id.back_changePass)
    ImageView back_changePass_view ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__change__password);
        ButterKnife.bind(this);
        Save_new_pass_view.setOnClickListener(this);
        back_changePass_view.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Save_new_pass:
                SaveNewPassword();
                break;
            case R.id.back_changePass:
                Intent i = new Intent(Activity_Change_Password.this, Activity_ResetPassword.class);
                startActivity(i);
                break;
        }
    }

    private void SaveNewPassword() {
        // Reset errors.
        change_pass_1_view.setError(null);
        change_pass_2_view.setError(null);
        // Store values at the time of the login attempt.
        String password1 = change_pass_1_view.getText().toString();
        String password2 = change_pass_2_view.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password 1, if the user entered one.
        if (!TextUtils.isEmpty(password1) && !isPasswordValid(password1)) {
            change_pass_1_view.setError(getString(R.string.error_invalid_password));
            focusView = change_pass_1_view;
            cancel = true;
        }
        // Check for a valid password 2, if the user entered one.
        if (!TextUtils.isEmpty(password2) && !isPasswordValid(password2)) {
            change_pass_2_view.setError(getString(R.string.error_invalid_password));
            focusView = change_pass_2_view;
            cancel = true;
        }
        if (!password1.equals(password2)){
            change_pass_2_view.setError(getString(R.string.password_not_match));
            focusView = change_pass_2_view;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't a
            // ttempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            /// showProgress(true);
            Volley_change_password(password1);
        }
    }
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 8 ;
    }

    private void Volley_change_password(final String password) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        String url = "http://smart-news-sa.com/news/public/api/user/change_password";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // check the response from server.
                try {
                    JSONObject json = new JSONObject(response);
                    if(json.get("success").equals(0)){
                        Toast.makeText(Activity_Change_Password.this, "password mismatched", Toast.LENGTH_SHORT).show();
                    }else if (json.get("success").equals(1)){
                        //Wrong credentials according to your server logic. Prompt to re-enter the credentials
                        Toast.makeText(Activity_Change_Password.this, "password updated Successfully.", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Activity_Change_Password.this, Activity_Main.class);
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
            }
        }){
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("password", password);
                params.put("cpassword", password);
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
