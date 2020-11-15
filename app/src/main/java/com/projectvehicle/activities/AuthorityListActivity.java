package com.projectvehicle.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.projectvehicle.EndPointUrl;
import com.projectvehicle.R;
import com.projectvehicle.RetrofitInstance;
import com.projectvehicle.adapters.AuthorityListAdapter;
import com.projectvehicle.models.AuthorityModel;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorityListActivity extends AppCompatActivity {
    List<AuthorityModel> auList;
    ListView lv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority_list);
        lv=(ListView)findViewById(R.id.lv);
        getSupportActionBar().setTitle("Authority List");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getAuthorityList();
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

    ProgressDialog progressDialog;
    private void getAuthorityList(){
        progressDialog = new ProgressDialog(AuthorityListActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<AuthorityModel>> call = service.getAuthorityList();
        call.enqueue(new Callback<List<AuthorityModel>>() {
            @Override
            public void onResponse(Call<List<AuthorityModel>> call, Response<List<AuthorityModel>> response) {
                progressDialog.dismiss();
                auList = response.body();
                //Toast.makeText(getApplicationContext(),auList.get(1).getName(),Toast.LENGTH_LONG).show();
                if(auList!=null)
                lv.setAdapter(new AuthorityListAdapter(auList,AuthorityListActivity.this));
            }
            @Override
            public void onFailure(Call<List<AuthorityModel>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AuthorityListActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
