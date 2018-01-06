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
import java.util.regex.Pattern;
import brand.smart_news_app.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Activity_Call_us extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.Name_callUs)
    EditText Name_callUs_view;
    @BindView(R.id.Email_callUs)
    EditText Email_callUs_view;
    @BindView(R.id.Message_callUs)
    EditText Message_callUs_view;
    @BindView(R.id.callUs_btn)
    Button callUs_btn_view;
    @BindView(R.id.back_callus)
    ImageView back_callus_view;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__call_us);
        ButterKnife.bind(this);
        callUs_btn_view.setOnClickListener(this);
        back_callus_view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.callUs_btn:
                Call_US();
                break;
            case R.id.back_callus:
                Intent i = new Intent(Activity_Call_us.this, Activity_Main.class);
                startActivity(i);
                break;
        }
    }

    private void Call_US() {
        // Reset errors.
        Name_callUs_view.setError(null);
        Email_callUs_view.setError(null);
        // Store values at the time of the login attempt.
        String U_name = Name_callUs_view.getText().toString();
        String U_email = Email_callUs_view.getText().toString();
        String U_message = Message_callUs_view.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid NAME .
        if (TextUtils.isEmpty(U_name)) {
            Name_callUs_view.setError(getString(R.string.error_field_required));
            focusView = Name_callUs_view;
            cancel = true;
        }
        // Check for a valid  EMAIL address.
        if (TextUtils.isEmpty(U_email)) {
            Email_callUs_view.setError(getString(R.string.error_field_required));
            focusView = Email_callUs_view;
            cancel = true;
        } else if (!validate(U_email)) {
            Email_callUs_view.setError(getString(R.string.error_invalid_email));
            focusView = Email_callUs_view;
            cancel = true;
        }
        // Check for a valid Message.
        if (TextUtils.isEmpty(U_message)) {
            Name_callUs_view.setError(getString(R.string.error_field_required));
            focusView = Name_callUs_view;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't
            focusView.requestFocus();
        } else {
            /// showProgress(true);
            //Toast.makeText(Activity_Call_us.this,"Done",Toast.LENGTH_LONG).show();
            Volley_call_us(U_name,U_email,U_message);
        }
    }

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }
    private void Volley_call_us(final String name, final String email, final String msg) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        String url = "http://smart-news-sa.com/news/public/api/user/contact_us";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // check the response from server.
                try {
                    JSONObject json = new JSONObject(response);
                    if(json.get("success").equals(0)){
                        Toast.makeText(Activity_Call_us.this, "Failed to send Message", Toast.LENGTH_SHORT).show();
                    }else if (json.get("success").equals(1)){
                        Toast.makeText(Activity_Call_us.this, "Message sent successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Activity_Call_us.this, Activity_Main.class);
                        startActivity(i);
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("CallUs",""+error.getMessage());

            }
        }){
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("msg", msg);

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
