package brand.smart_news_app.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import brand.smart_news_app.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class Activity_Receive_Code extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.code_1)
    EditText code_1_view;
    @BindView(R.id.code_2)
    EditText code_2_view;
    @BindView(R.id.code_3)
    EditText code_3_view;
    @BindView(R.id.code_4)
    EditText code_4_view;
    @BindView(R.id.code_5)
    EditText code_5_view;
    @BindView(R.id.check_code)
    Button check_code_view;
    @BindView(R.id.back_recCode)
    ImageView back_recCode_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__receive_code);
        ButterKnife.bind(this);
        Check_Code();
        check_code_view.setOnClickListener(this);
        back_recCode_view.setOnClickListener(this);
    }

    private void Check_Code() {

        code_1_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (code_1_view.getText().length() == 1) {
                    code_2_view.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        code_2_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (code_2_view.getText().length() == 1) {
                    code_3_view.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        code_3_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (code_3_view.getText().length() == 1) {
                    code_4_view.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        code_4_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (code_4_view.getText().length() == 1) {
                    code_5_view.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        code_5_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (code_1_view.getText().length() == 1) {
                    Toast.makeText(Activity_Receive_Code.this, "done", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_code:
                Intent i = new Intent(Activity_Receive_Code.this, Activity_Change_Password.class);
                startActivity(i);
                break;
            case R.id.Send_code:
                Intent j = new Intent(Activity_Receive_Code.this, Activity_ResetPassword.class);
                startActivity(j);
                break;
            case R.id.back_recCode :
                Intent u = new Intent(Activity_Receive_Code.this, Activity_ResetPassword.class);
                startActivity(u);
                break;
        }
    }
}
