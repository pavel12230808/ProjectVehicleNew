package com.projectvehicle.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.projectvehicle.EndPointUrl;
import com.projectvehicle.R;
import com.projectvehicle.ResponseData;
import com.projectvehicle.RetrofitInstance;
import com.projectvehicle.activities.AuthorityListActivity;
import com.projectvehicle.activities.EditAuthorityActivity;
import com.projectvehicle.models.AuthorityModel;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AuthorityListAdapter extends BaseAdapter {
    List<AuthorityModel> ar;
    Context cnt;

    public AuthorityListAdapter(List<AuthorityModel> ar, Context cnt) {
        this.ar = ar;
        this.cnt = cnt;
    }

    @Override
    public int getCount() {
        return ar.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int pos, View view, ViewGroup viewGroup) {
        LayoutInflater obj1 = (LayoutInflater) cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View obj2 = obj1.inflate(R.layout.row_authority_list, null);

        ImageView img_profile=(ImageView)obj2.findViewById(R.id.img_profile);
        Glide.with(cnt).load(ar.get(pos).getPhoto()).into(img_profile);


        TextView tv_name=(TextView)obj2.findViewById(R.id.tv_name);
        tv_name.setText("Name:  "+ar.get(pos).getName());

        TextView tv_email=(TextView)obj2.findViewById(R.id.tv_emailid);
        tv_email.setText("EmailId:  "+ar.get(pos).getEmailid());

        TextView tv_phno=(TextView)obj2.findViewById(R.id.tv_phno);
        tv_phno.setText("Phno:  "+ar.get(pos).getPhno());



        TextView tv_pwd=(TextView)obj2.findViewById(R.id.tv_pwd);
        tv_pwd.setText("Password:  "+ar.get(pos).getPwd());

        TextView tv_edit=(TextView)obj2.findViewById(R.id.tv_edit);
        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(cnt, EditAuthorityActivity.class);
                intent.putExtra("name",ar.get(pos).getName());
                intent.putExtra("emailid",ar.get(pos).getEmailid());
                intent.putExtra("phno",ar.get(pos).getPhno());

                intent.putExtra("pwd",ar.get(pos).getPwd());
                cnt.startActivity(intent);
            }
        });
        TextView tv_delete=(TextView)obj2.findViewById(R.id.tv_delete);
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitdata(ar.get(pos).getEmailid());
            }
        });

        Typeface custom_font = Typeface.createFromAsset(cnt.getAssets(), "fonts/Lato-Medium.ttf");
        tv_name.setTypeface(custom_font);
        tv_email.setTypeface(custom_font);
        tv_phno.setTypeface(custom_font);

        tv_pwd.setTypeface(custom_font);
        return obj2;
    }

    ProgressDialog pd;
    public  void submitdata(String uname)
    {
        pd= new ProgressDialog(cnt);
        pd.setTitle("Please wait,Data is being submit...");
        pd.show();
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<ResponseData> call = apiService.deleteAuthority(uname);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
              //  Toast.makeText(AuthorityListAdapter.this, "Deleted Successfully", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(cnt, AuthorityListActivity.class);
                cnt.startActivity(intent);
                Toast.makeText(cnt,"Deleted successfully",Toast.LENGTH_SHORT).show();
                ((Activity)cnt).finish();

            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(cnt,"Server Issue",Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }

}