package com.projectvehicle.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.projectvehicle.EndPointUrl;
import com.projectvehicle.R;
import com.projectvehicle.ResponseData;
import com.projectvehicle.RetrofitInstance;
import com.projectvehicle.Utils;
import com.projectvehicle.models.AuthorityModel;
import com.projectvehicle.models.EditProfilePojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MunicipalEditProfileActivity extends AppCompatActivity {
    EditText et_name,et_emailid,et_phone_no,et_uname,et_password;
    Button btnupdate;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    SharedPreferences sharedPreferences;
    String session;
    ProgressDialog progressDialog;
    List<AuthorityModel> auList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority_editprofile);

        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnupdate=(Button)findViewById(R.id.btnupdade);
        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");
        et_name=(EditText)findViewById(R.id.et_name);
        et_emailid=(EditText)findViewById(R.id.et_emailid);
        et_phone_no=(EditText)findViewById(R.id.et_phone_no);
        et_password=(EditText)findViewById(R.id.et_password);



        progressDialog = new ProgressDialog(MunicipalEditProfileActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();


        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<AuthorityModel>> call = service.getAuthorityProfile(session);

        call.enqueue(new Callback<List<AuthorityModel>>() {
            @Override
            public void onResponse(Call<List<AuthorityModel>> call, Response<List<AuthorityModel>> response) {

                progressDialog.dismiss();
                auList = response.body();

                AuthorityModel user = auList.get(0);

                et_name.setText(user.getName());

                et_emailid.setText(user.getEmailid());

                et_phone_no.setText(user.getPhno());
               // cd_tv_email.setText(session);

                et_password.setText(user.getPwd());


            }

            @Override
            public void onFailure(Call<List<AuthorityModel>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MunicipalEditProfileActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        btnupdate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (et_name.getText().toString().isEmpty()) {
                   Toast.makeText(getApplicationContext(), "Name Should not be Empty", Toast.LENGTH_SHORT).show();
                   return;
               }

               else if (et_phone_no.getText().toString().isEmpty()) {
                   Toast.makeText(getApplicationContext(), "Phone Number Should not be Empty", Toast.LENGTH_SHORT).show();
                   return;
               }

               else if (et_phone_no.getText().toString().length()!=10) {
                   Toast.makeText(getApplicationContext(), "Please enter 10 digit Phone Number.", Toast.LENGTH_SHORT).show();
                   return;
               }



               else if (et_password.getText().toString().isEmpty()) {
                   Toast.makeText(getApplicationContext(), "Password Should not be Empty", Toast.LENGTH_SHORT).show();
                   return;
               }
               else
               {
                   submitdata();
               }
              // submitdata();
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
        pd= new ProgressDialog(MunicipalEditProfileActivity.this);
        pd.setTitle("Please wait,Data is being submit...");
        pd.show();
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<ResponseData> call = apiService.updateauthoritydetails(et_name.getText().toString(),session,et_phone_no.getText().toString(),et_password.getText().toString());
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if (response.body().status.equals("true")) {
                    Toast.makeText(MunicipalEditProfileActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    Log.i("msg", "" + response.body().message);
                    finish();
                } else {
                    Toast.makeText(MunicipalEditProfileActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(MunicipalEditProfileActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        });
    }
}