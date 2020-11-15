package com.projectvehicle.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.projectvehicle.EndPointUrl;
import com.projectvehicle.R;
import com.projectvehicle.ResponseData;
import com.projectvehicle.RetrofitInstance;
import com.projectvehicle.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddComplaintActivity extends AppCompatActivity {

    EditText et_title,et_problem;
    Button btn_complaint;
    SharedPreferences sharedPreferences;
    String session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaint);

        getSupportActionBar().setTitle("Send Complaint");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_title=(EditText)findViewById(R.id.et_title);
        et_problem=(EditText)findViewById(R.id.et_problem);
        btn_complaint=(Button)findViewById(R.id.btn_complaint);

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("uname", "def-val");

        btn_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_title.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Enter title",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(et_problem.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Enter Description",Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    submitdata();
                }

            }
        });
    }

    ProgressDialog pd;
    public  void submitdata()
    {
        pd= new ProgressDialog(AddComplaintActivity.this);
        pd.setTitle("Please wait,Data is being submit...");
        pd.show();
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<ResponseData> call = apiService.addcomplaint(et_title.getText().toString(),et_problem.getText().toString(),session);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if (response.body().status.equals("true")) {
                    Toast.makeText(AddComplaintActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    Log.i("msg", "" + response.body().message);
                    finish();
                } else {
                    Toast.makeText(AddComplaintActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(AddComplaintActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                pd.dismiss();
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
}