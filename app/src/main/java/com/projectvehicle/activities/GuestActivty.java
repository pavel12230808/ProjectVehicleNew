package com.projectvehicle.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.projectvehicle.EndPointUrl;
import com.projectvehicle.R;
import com.projectvehicle.RetrofitInstance;
import com.projectvehicle.Utils;
import com.projectvehicle.adapters.GetFineDetailsAdapter;
import com.projectvehicle.adapters.GuestDetailsAdapter;
import com.projectvehicle.models.PaymentDuePojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestActivty extends AppCompatActivity {
    EditText et_search;
    ListView list_view;
    ProgressDialog progressDialog;
    List<PaymentDuePojo> a1;
    SharedPreferences sharedPreferences;
    GuestDetailsAdapter guestDetailsAdapter;
    String session;
    Button bt_guest_data;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        getSupportActionBar().setTitle("Guest");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_search=(EditText)findViewById(R.id.et_search);
        list_view=(ListView)findViewById(R.id.list_view);
        bt_guest_data=(Button)findViewById(R.id.bt_guest_data);


        a1= new ArrayList<>();
        bt_guest_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                serverData();

            }
        });
    }
    public void serverData(){
        progressDialog = new ProgressDialog(GuestActivty.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<PaymentDuePojo>> call = service.search(et_search.getText().toString());
        call.enqueue(new Callback<List<PaymentDuePojo>>() {
            @Override
            public void onResponse(Call<List<PaymentDuePojo>> call, Response<List<PaymentDuePojo>> response) {
                //Toast.makeText(GuestActivty.this,"AAA   "+response.body().size(),Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(GuestActivty.this,"No data found",Toast.LENGTH_SHORT).show();
                }if(response.body().size() <=0){
                    Toast.makeText(GuestActivty.this,"No records found",Toast.LENGTH_SHORT).show();
                }
                else {
                    a1 = response.body();
                    //list_view.setAdapter(new GuestDetailsAdapter(a1, GuestActivty.this));
                    guestDetailsAdapter=new GuestDetailsAdapter(a1,GuestActivty.this);
                    list_view.setAdapter(guestDetailsAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<PaymentDuePojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(GuestActivty.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
