package com.projectvehicle.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.projectvehicle.EndPointUrl;
import com.projectvehicle.R;
import com.projectvehicle.RetrofitInstance;
import com.projectvehicle.Utils;
import com.projectvehicle.adapters.AdminCarVerifyAdapter;
import com.projectvehicle.adapters.GetAllCarsAdapter;
import com.projectvehicle.models.CarsModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminVerifyActivity extends AppCompatActivity {
    ListView list_view;
    List<CarsModel> getAllCars;
    Button btn_add_cars;
    SharedPreferences sharedPreferences;
    String session;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_verify);
        getSupportActionBar().setTitle("Verify Car Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        list_view=(ListView)findViewById(R.id.list_view);
        getAllCars= new ArrayList<>();
        serverData();

    }

    public void serverData(){
        progressDialog = new ProgressDialog(AdminVerifyActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<CarsModel>> call = service.getcars();

        call.enqueue(new Callback<List<CarsModel>>() {
            @Override
            public void onResponse(Call<List<CarsModel>> call, Response<List<CarsModel>> response) {
                //Toast.makeText(GetAllJobProfileActivity.this, ""+response.body().size(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(AdminVerifyActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    getAllCars = response.body();
                    list_view.setAdapter(new AdminCarVerifyAdapter(getAllCars, AdminVerifyActivity.this));
                }
            }

            @Override
            public void onFailure(Call<List<CarsModel>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AdminVerifyActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
