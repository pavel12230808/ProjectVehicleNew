package com.projectvehicle.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.projectvehicle.EndPointUrl;
import com.projectvehicle.R;
import com.projectvehicle.ResponseData;
import com.projectvehicle.RetrofitInstance;
import com.projectvehicle.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAuthorityActivity extends AppCompatActivity {
    EditText et_name,et_emailid,et_phone_no,et_uname,et_password;
    Button bt_reg;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_authority);
        getSupportActionBar().setTitle("Edit Authority");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_name=(EditText)findViewById(R.id.et_name);
        et_name.setText(getIntent().getStringExtra("name"));
        et_emailid=(EditText)findViewById(R.id.et_emailid);
        et_emailid.setText(getIntent().getStringExtra("emailid"));
        et_phone_no=(EditText)findViewById(R.id.et_phone_no);
        et_phone_no.setText(getIntent().getStringExtra("phno"));
        et_uname=(EditText)findViewById(R.id.et_uname);
        et_uname.setText(getIntent().getStringExtra("uname"));
        et_uname.setEnabled(false);
        et_password=(EditText)findViewById(R.id.et_password);
        et_password.setText(getIntent().getStringExtra("pwd"));

        bt_reg=(Button)findViewById(R.id.bt_reg);
        bt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_name.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Name Should not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (et_phone_no.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Phone Number Should not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (et_phone_no.getText().toString().length()!=10) {
                    Toast.makeText(getApplicationContext(), "Please enter 10 digit Phone Number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (et_emailid.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email Should not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!et_emailid.getText().toString().matches(emailPattern))
                {
                    Toast.makeText(getApplicationContext(), "Please enter valid emailID.", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (et_password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Password Should not be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                submitdata();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    ProgressDialog pd;
    public  void submitdata()
    {
        pd= new ProgressDialog(EditAuthorityActivity.this);
        pd.setTitle("Please wait,Data is being submit...");
        pd.show();
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<ResponseData> call = apiService.updateAuthority(et_name.getText().toString(),et_emailid.getText().toString(),et_phone_no.getText().toString(),et_password.getText().toString());
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if (response.body().status.equals("true")) {
                    Toast.makeText(EditAuthorityActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    Log.i("msg", "" + response.body().message);
                    finish();
                } else {
                    Toast.makeText(EditAuthorityActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(EditAuthorityActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        });
    }
}