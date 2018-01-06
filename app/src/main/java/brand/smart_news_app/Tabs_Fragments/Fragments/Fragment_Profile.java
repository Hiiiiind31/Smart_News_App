package brand.smart_news_app.Tabs_Fragments.Fragments;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import brand.smart_news_app.Activities.Activity_Main;
import brand.smart_news_app.Activities.Activity_Signin;
import brand.smart_news_app.Activities.Activity_Signup;
import brand.smart_news_app.PrefManager.PrefManager;
import brand.smart_news_app.R;
import butterknife.BindView;

import static android.app.Activity.RESULT_OK;


public class Fragment_Profile extends Fragment implements View.OnClickListener {

    private EditText Edit_Name_view, Edit_Email_view, Edit_phone_view;
    private ImageView edit_profile_view, save_profile_view, back_profile_view, profile_pic_view;
    private View view;
    private TextView T_Name_view, T_Email_View, T_Likes_view, T_comments_view, T_share_view;
    private PrefManager pref;
    private String img_str;
    ScrollView pro_scrollview ;
    ProgressBar progressBar ;
    FrameLayout Frame_layout ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment__profile, container, false);
        pref = new PrefManager(getActivity());

        edit_profile_view = (ImageView) view.findViewById(R.id.H_Edit_profile);
        save_profile_view = (ImageView) view.findViewById(R.id.H_save_edit);
        back_profile_view = (ImageView) view.findViewById(R.id.back_profile);
        profile_pic_view = (ImageView) view.findViewById(R.id.profile_pic);

        Edit_Name_view = (EditText) view.findViewById(R.id.edit_name);
        Edit_phone_view = (EditText) view.findViewById(R.id.edit_phone);
        Edit_Email_view = (EditText) view.findViewById(R.id.edit_email);
        // header
        T_Name_view = (TextView) view.findViewById(R.id.T_Name);
        T_Email_View = (TextView) view.findViewById(R.id.T_Email);
        T_Likes_view = (TextView) view.findViewById(R.id.likes_No);
        T_comments_view = (TextView) view.findViewById(R.id.comments_No);
        T_share_view = (TextView) view.findViewById(R.id.share_No);
        //layout
        pro_scrollview= view.findViewById(R.id.Pro_scrollview_layout);
        Frame_layout =view.findViewById(R.id.Frame_layout_id2);
        progressBar = view.findViewById(R.id.progressBar2);
        Circle doubleBounce = new Circle();
        progressBar.setIndeterminateDrawable(doubleBounce);

        SetOnClick();
        Volley_GetUserData(pref.getUser_id());
        return view;
    }

    private void Volley_GetUserData(final String user_id) {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://smart-news-sa.com/news/public/api/user/profile";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            if (json.get("success").equals(0)) {
                                Toast.makeText(getActivity(), " there is no data related to this id", Toast.LENGTH_SHORT).show();
                            } else if (json.get("success").equals(1)) {
                                String user_id = json.get("user_id").toString();
                                String name = json.getString("name");
                                String email = json.getString("email");
                                String profile_image = json.getString("profile_image");
                                String phone = json.getString("phone");
                                String comment = json.getString("comments");
                                String likes = json.getString("likes");
                                String share = json.getString("shares");
                                pref.setUser_pro_pic(profile_image);
                                SetUserData(name, email, profile_image, phone, comment, likes, share);
                                Toast.makeText(getActivity(), " success and get user profile data", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error in sending requst
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                return params;
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void Volley_EditProfile(final String u_name, final String u_phone, final String u_email) {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://smart-news-sa.com/news/public/api/user/edit_profile";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("log", response);
                        try {
                            showProgress(false);
                            JSONObject json = new JSONObject(response);
                            if (json.get("success").equals("")) {
                                Toast.makeText(getActivity(), " failed to update profile ", Toast.LENGTH_SHORT).show();
                            } else if (json.get("success").equals("1")) {
                                Toast.makeText(getActivity(), "profile updated successfully.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //error in sending requst
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("log", "" + error);
            }
        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", pref.getUser_id());
                params.put("name", u_name);
                params.put("phone", u_phone);
                params.put("email", u_email);
                params.put("profile_image", img_str);

                return params;
            }
        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void SetUserData(String name, String email, String profile_image, String phone, String comment, String likes, String share) {
        Edit_Name_view.setText(name);
        Edit_phone_view.setText(phone);
        Edit_Email_view.setText(email);
        T_Name_view.setText(name);
        T_Email_View.setText(email);
        T_Likes_view.setText(likes + "");
        T_comments_view.setText(comment + "");
        T_share_view.setText(share + "");
        Picasso.with(getActivity()).load("http://smart-news-sa.com/news/public/uploads/profile/" + profile_image).into(profile_pic_view);
    }

    private void Save_new_data() {
        String U_name = Edit_Name_view.getText().toString();
        String U_phone = Edit_phone_view.getText().toString();
        String U_email = Edit_Email_view.getText().toString();
        showProgress(true);
        Volley_EditProfile(U_name, U_phone, U_email);
    }

    public void setNewProfilePic(Bitmap newProfilePic) {
        //code image to string
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        newProfilePic.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] image = stream.toByteArray();
        img_str = Base64.encodeToString(image, 0);
        //decode string to image
        byte[] imageAsBytes = Base64.decode(img_str.getBytes(), Base64.DEFAULT);
        profile_pic_view.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        pref.setUser_pro_pic(img_str);
    }

    private void showProgress(boolean b) {
        if (b){
            Frame_layout.setVisibility(View.VISIBLE);
            //pro_scrollview.setBackgroundColor(R.color.transparent_color);

        }else {
            Frame_layout.setVisibility(View.GONE);
           // pro_scrollview.setBackgroundColor(R.color.colorWhite);
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // In fragment class callback
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK) {
                    Uri URI = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(URI, projection, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        int column_index = cursor.getColumnIndex(projection[0]);
                        String file_path = cursor.getString(column_index);
                        cursor.close();
                        Bitmap bitmap = BitmapFactory.decodeFile(file_path);
                        setNewProfilePic(bitmap);
                    };
                }
                break;
            default:
                break;
        }
    }

    private void SetOnClick() {
        //Onclick
        edit_profile_view.setOnClickListener(this);
        save_profile_view.setOnClickListener(this);
        back_profile_view.setOnClickListener(this);
        profile_pic_view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.H_Edit_profile:

                Edit_Name_view.setFocusableInTouchMode(true);
                Edit_phone_view.setFocusableInTouchMode(true);
                Edit_Email_view.setFocusableInTouchMode(true);
                profile_pic_view.setClickable(true);
                save_profile_view.setVisibility(View.VISIBLE);
                edit_profile_view.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "edit", Toast.LENGTH_LONG).show();
                break;

            case R.id.H_save_edit:
                Edit_Name_view.setFocusableInTouchMode(false);
                Edit_Name_view.setFocusable(false);
                Edit_phone_view.setFocusableInTouchMode(false);
                Edit_phone_view.setFocusable(false);
                Edit_Email_view.setFocusableInTouchMode(false);
                Edit_Email_view.setFocusable(false);
                edit_profile_view.setVisibility(View.VISIBLE);
                save_profile_view.setVisibility(View.GONE);
                profile_pic_view.setClickable(false);
                Toast.makeText(getActivity(), "save", Toast.LENGTH_LONG).show();
                Save_new_data();

                break;
            case R.id.back_profile:
                Intent i = new Intent(getActivity(), Activity_Main.class);
                startActivity(i);
                break;
            case R.id.profile_pic:
                Intent d = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(d, 100);
                break;
        }
    }

}
