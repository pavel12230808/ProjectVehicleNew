package com.projectvehicle.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.projectvehicle.EndPointUrl;
import com.projectvehicle.MainActivity;
import com.projectvehicle.R;
import com.projectvehicle.RetrofitInstance;
import com.projectvehicle.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.projectvehicle.adapters.MyProfileAdapter;
import com.projectvehicle.models.EditProfilePojo;

import java.util.List;


public class MyProfileActivity extends AppCompatActivity {
    TextView tv_fname,tv_email,tv_lname,tv_phone,cd_tv_name,cd_tv_email;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private DrawerLayout dl;
    MyProfileAdapter myProfileAdapter;
    SharedPreferences sharedPreferences;
    String session;
    ProgressDialog progressDialog;
    List<EditProfilePojo> a1;
    CircularImageView image_view;
    CardView card_view,card_view_notif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        card_view=(CardView)findViewById(R.id.card_view);
        card_view.setBackgroundResource(R.drawable.card_view_bg);

        card_view_notif=(CardView)findViewById(R.id.card_view_notif);
        card_view_notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyProfileActivity.this,UserHomeActivity.class);
                startActivity(intent);
            }
        });

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("user_name", "def-val");

        navigationView();
        ActionBar mActionBar=getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);

        image_view=(CircularImageView)findViewById(R.id.image_view);

        tv_fname=(TextView)findViewById(R.id.tv_fname);
        tv_email=(TextView)findViewById(R.id.tv_email);
        tv_lname=(TextView)findViewById(R.id.tv_lname);
        tv_phone=(TextView)findViewById(R.id.tv_phone);

        cd_tv_name=(TextView)findViewById(R.id.cd_tv_name);

        cd_tv_email=(TextView)findViewById(R.id.cd_tv_email);






        progressDialog = new ProgressDialog(MyProfileActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<EditProfilePojo>> call = service.getMyProfile(session);

        call.enqueue(new Callback<List<EditProfilePojo>>() {
            @Override
            public void onResponse(Call<List<EditProfilePojo>> call, Response<List<EditProfilePojo>> response) {

                progressDialog.dismiss();
                a1 = response.body();

                 EditProfilePojo user = a1.get(0);

                tv_fname.setText("  "+user.getFirst_name());

                tv_lname.setText("  "+user.getLast_name());

                cd_tv_name.setText(user.getFirst_name()+" "+user.getLast_name());
                cd_tv_email.setText(session);



                //Glide.with(MyProfileActivity.this).load("http://parttimejobs.site/Jobsearch/"+user.getImg_photo()).into(image_view);


                tv_phone.setText("  "+user.getPhonenumber());

                tv_email.setText("  "+session);

            }

            @Override
            public void onFailure(Call<List<EditProfilePojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MyProfileActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void navigationView(){
        dl = (DrawerLayout)findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);
        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.myprofile:
                        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.view_personal:
                        Intent intent1=new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.view_balance:
                        Intent view_jobs=new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(view_jobs);
                        break;

                    case R.id.view_terms:
                        Intent changepwd=new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(changepwd);
                        break;

                    case R.id.search_challana:
                        Intent searchjobs=new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(searchjobs);
                        break;

                    case R.id.logout:
                        Intent logout=new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(logout);
                        break;

                    default:
                        return true;
                }
                dl.closeDrawer(GravityCompat.START);
                return true;

            }
        });
    }
    @Override
    public void onBackPressed() {
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else {
            dl.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (dl.isDrawerOpen(GravityCompat.START)) {

            dl.closeDrawer(GravityCompat.START);
        }
        else {

            dl.openDrawer(GravityCompat.START);
        }
        switch (id){
            case R.id.edit_profile:
                Intent contact = new Intent(getApplicationContext(), EditYourProfileActivity.class);
                startActivity(contact);
                return true;
            case R.id.change_pwd:
                Intent pwd = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                startActivity(pwd);
                return true;
            case R.id.logout:
                Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(myIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
