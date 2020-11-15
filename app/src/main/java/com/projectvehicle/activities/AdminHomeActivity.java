package com.projectvehicle.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.projectvehicle.R;

public class AdminHomeActivity extends AppCompatActivity {
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private DrawerLayout dl;
    CardView cd_paid_fines,cd_pending_fines,card_addauthority,card_viewauthority,card_verify_cars,card_feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        navigationView();

        card_addauthority=(CardView)findViewById(R.id.card_addauthority);
        card_addauthority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminHomeActivity.this, AddAuthorityActivity.class);
                startActivity(intent);
            }
        });

        card_viewauthority=(CardView)findViewById(R.id.card_viewauthority);
        card_viewauthority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminHomeActivity.this, AuthorityListActivity.class);
                startActivity(intent);
            }
        });
        cd_paid_fines=(CardView)findViewById(R.id.cd_paid_fines);
        cd_paid_fines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminHomeActivity.this, GetAllPaidFinesActivity.class);
                startActivity(intent);
            }
        });

        cd_pending_fines=(CardView)findViewById(R.id.cd_pending_fines);
        cd_pending_fines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminHomeActivity.this, GetAllPendingFinesActivity.class);
                startActivity(intent);
            }
        });

        card_verify_cars=(CardView)findViewById(R.id.card_verify_cars);
        card_verify_cars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminHomeActivity.this, AdminVerifyActivity.class);
                startActivity(intent);
            }
        });

        card_feedback=(CardView)findViewById(R.id.card_feedback);
        card_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminHomeActivity.this, AdminComplaintsActivity.class);
                startActivity(intent);
            }
        });


    }

    private void navigationView() {
        dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        dl.addDrawerListener(t);
        t.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nv = (NavigationView) findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.myprofile:
                        Intent intent = new Intent(getApplicationContext(), AdminEditProfileActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.add_authority:
                        Intent intent1 = new Intent(getApplicationContext(), AddAuthorityActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.authority_list:
                        Intent auList = new Intent(getApplicationContext(), AuthorityListActivity.class);
                        startActivity(auList);
                        break;


                    case R.id.logout:
                        Intent logout = new Intent(getApplicationContext(), LoginActivity.class);
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
            //super.onBackPressed();
            diolouge();
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