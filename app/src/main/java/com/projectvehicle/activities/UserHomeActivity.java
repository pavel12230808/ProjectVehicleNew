package com.projectvehicle.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.projectvehicle.EndPointUrl;
import com.projectvehicle.MainActivity;
import com.projectvehicle.R;
import com.projectvehicle.ResponseData;
import com.projectvehicle.RetrofitInstance;
import com.projectvehicle.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserHomeActivity extends AppCompatActivity {
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private DrawerLayout dl;
    CardView cd_my_challan,cd_pending_payments,cd_payment_history,cd_complaints;
    SharedPreferences sharedPreferences;
    String session,token;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        //Toast.makeText(UserHomeActivity.this, token, Toast.LENGTH_SHORT).show();

        navigationView();

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("uname", "def-val");
        //Toast.makeText(this, ""+session, Toast.LENGTH_SHORT).show();
        registrationToken();


        cd_my_challan=(CardView)findViewById(R.id.cd_my_challan);
        cd_pending_payments=(CardView)findViewById(R.id.cd_pending_payments);
        cd_payment_history=(CardView)findViewById(R.id.cd_payment_history);

        cd_complaints=(CardView)findViewById(R.id.cd_complaints);
        cd_complaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserHomeActivity.this, AddComplaintActivity.class);
                startActivity(intent);
            }
        });

        cd_my_challan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserHomeActivity.this, GetFineDetailsActivity.class);
                startActivity(intent);
            }
        });
        cd_pending_payments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserHomeActivity.this, GetPendingFinesActivity.class);
                startActivity(intent);
            }
        });

        cd_payment_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserHomeActivity.this, GetPaidFinesActivity.class);
                startActivity(intent);
            }
        });
    }
    private  void registrationToken(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            //Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                         token = task.getResult().getToken();

                        // Log and toast
                        //String msg = getString(R.string.msg_token_fmt, token);
                        //Log.d(TAG, msg);

                        submitdata();
                    }
                });
    }
    public  void submitdata()
    {

        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<ResponseData> call = apiService.update_fcm_token(token,session);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {

                if (response.body().status.equals("true")) {
                    Toast.makeText(UserHomeActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    Log.i("msg", "" + response.body().message);
                    //finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

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
                        Intent intent=new Intent(getApplicationContext(), EditYourProfileActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.mycar:
                        Intent intentcar=new Intent(getApplicationContext(), MyCarsActivity.class);
                        startActivity(intentcar);
                        break;
                    case R.id.view_personal:
                        Intent intent1=new Intent(getApplicationContext(), ChangePasswordActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.view_balance:
                        Intent view_jobs=new Intent(getApplicationContext(), MainActivity.class);
                       // startActivity(view_jobs);
                        break;

                    case R.id.view_terms:
                        Intent changepwd=new Intent(getApplicationContext(), MainActivity.class);
                       // startActivity(changepwd);
                        break;

                        case R.id.scane_num_plate:
                        Intent scan_num_plate=new Intent(getApplicationContext(), ReadVehicleDetailsActivity.class);
                        startActivity(scan_num_plate);
                        break;

                    case R.id.search_challana:
                        Intent searchjobs=new Intent(getApplicationContext(), GuestActivty.class);
                        startActivity(searchjobs);
                        break;

                    case R.id.logout:
                        Intent logout=new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(logout);
                        finish();
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
            diolouge();
            //super.onBackPressed();
        }
    }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         int id = item.getItemId();
         if (dl.isDrawerOpen(GravityCompat.START)) {
             dl.closeDrawer(GravityCompat.START);
         } else {
             dl.openDrawer(GravityCompat.START);
         }
         return super.onOptionsItemSelected(item);
     }
    public void  diolouge(){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Do you want to exit ?");
            //builder.setMessage("Do you want to exit from app? ");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();


                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();

                }
            });
            builder.show();

    }
}