package com.projectvehicle.activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.projectvehicle.EndPointUrl;
import com.projectvehicle.R;
import com.projectvehicle.RetrofitInstance;
import com.projectvehicle.adapters.GetAllPaidFinesAdapter;
import com.projectvehicle.adapters.GetAllPendingFinesAdapter;
import com.projectvehicle.models.PaymentDuePojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllPendingFinesActivity extends AppCompatActivity {
    ListView list_view;
    ProgressDialog progressDialog;
    List<PaymentDuePojo> a1;
    SharedPreferences sharedPreferences;
    String session;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_fine_details);

        getSupportActionBar().setTitle("All Pending Fines");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view=(ListView)findViewById(R.id.list_view);
        a1= new ArrayList<>();
        serverData();
    }
    public void serverData(){
        progressDialog = new ProgressDialog(GetAllPendingFinesActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<PaymentDuePojo>> call = service.getAllPendingFines();
        call.enqueue(new Callback<List<PaymentDuePojo>>() {
            @Override
            public void onResponse(Call<List<PaymentDuePojo>> call, Response<List<PaymentDuePojo>> response) {
                //Toast.makeText(GetAllPendingFinesActivity.this,"AAA   "+response.body().size(),Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(GetAllPendingFinesActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                } else if (response.body().size()==0) {
                    Toast.makeText(GetAllPendingFinesActivity.this,"No Pending Payments",Toast.LENGTH_SHORT).show();

                } else {
                    a1 = response.body();
                    list_view.setAdapter(new GetAllPendingFinesAdapter(a1, GetAllPendingFinesActivity.this));

                }
            }

            @Override
            public void onFailure(Call<List<PaymentDuePojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(GetAllPendingFinesActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
