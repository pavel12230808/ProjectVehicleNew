package com.projectvehicle.activities;

import android.app.ProgressDialog;
import android.content.Intent;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayFineActivity extends AppCompatActivity {
    EditText et_title,et_desc,et_fee,et_num_plate;
    Button btn_submit;
    ProgressDialog pd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_fine);

        getSupportActionBar().setTitle("Pay Fine");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_title=(EditText)findViewById(R.id.et_title);
        et_desc=(EditText)findViewById(R.id.et_desc);
        et_fee=(EditText)findViewById(R.id.et_fee);
        et_num_plate=(EditText)findViewById(R.id.et_num_plate);
        btn_submit=(Button)findViewById(R.id.btn_submit);

        et_title.setText(getIntent().getStringExtra("Title"));
        et_desc.setText(getIntent().getStringExtra("Description"));
        et_fee.setText(getIntent().getStringExtra("Fee"));
        et_num_plate.setText(getIntent().getStringExtra("Num_Plate"));


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitdata();

            }
        });
    }
    public  void submitdata()
    {
        pd= new ProgressDialog(PayFineActivity.this);
        pd.setTitle("Please wait,Data is being submit...");
        pd.show();
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<ResponseData> call = apiService.payFines(getIntent().getStringExtra("id"));
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if (response.body().status.equals("true")) {
                    Toast.makeText(PayFineActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    Log.i("msg", "" + response.body().message);
                    Intent intent=new Intent(PayFineActivity.this, GetFineDetailsActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(PayFineActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(PayFineActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
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
