package com.projectvehicle.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.projectvehicle.EndPointUrl;
import com.projectvehicle.R;
import com.projectvehicle.RetrofitInstance;
import com.projectvehicle.adapters.AdminCarVerifyAdapter;
import com.projectvehicle.adapters.AdminComplaintsAdapter;
import com.projectvehicle.models.CarsModel;
import com.projectvehicle.models.ComplaintModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminComplaintsActivity extends AppCompatActivity {
    ListView list_view;
    List<ComplaintModel> getAllComplaints;

    SharedPreferences sharedPreferences;
    String session;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_complaints);
        getSupportActionBar().setTitle("Complaints");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        list_view=(ListView)findViewById(R.id.list_view);
        getAllComplaints= new ArrayList<>();
        serverData();

    }

    public void serverData(){
        progressDialog = new ProgressDialog(AdminComplaintsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<ComplaintModel>> call = service.getcomplaints();

        call.enqueue(new Callback<List<ComplaintModel>>() {
            @Override
            public void onResponse(Call<List<ComplaintModel>> call, Response<List<ComplaintModel>> response) {
                //Toast.makeText(GetAllJobProfileActivity.this, ""+response.body().size(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(AdminComplaintsActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    getAllComplaints = response.body();
                    list_view.setAdapter(new AdminComplaintsAdapter(getAllComplaints, AdminComplaintsActivity.this));
                }
            }

            @Override
            public void onFailure(Call<List<ComplaintModel>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AdminComplaintsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
