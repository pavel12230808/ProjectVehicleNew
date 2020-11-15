package com.projectvehicle.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.projectvehicle.activities.AdminVerifyActivity;
import com.projectvehicle.models.CarsModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminCarVerifyAdapter  extends BaseAdapter {

    List<CarsModel> getAllCarsProfilePojos;
    Context cnt;
    public AdminCarVerifyAdapter(List<CarsModel> ar, Context cnt)
    {
        this.getAllCarsProfilePojos=ar;
        this.cnt=cnt;
    }
    @Override
    public int getCount() {
        return getAllCarsProfilePojos.size();
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
    public View getView(final int pos, View view, ViewGroup viewGroup)
    {
        LayoutInflater obj1 = (LayoutInflater)cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View obj2=obj1.inflate(R.layout.list_cars_adapters,null);

        ImageView image_view=(ImageView)obj2.findViewById(R.id.image_view);
        Glide.with(cnt).load(getAllCarsProfilePojos.get(pos).getImage()).into(image_view);

        TextView tv_make=(TextView)obj2.findViewById(R.id.tv_make);
        tv_make.setText("Make : "+getAllCarsProfilePojos.get(pos).getMake());

        TextView tv_model=(TextView)obj2.findViewById(R.id.tv_model);
        tv_model.setText("Model : "+getAllCarsProfilePojos.get(pos).getModel());

        TextView tv_color=(TextView)obj2.findViewById(R.id.tv_color);
        tv_color.setText("Color : "+getAllCarsProfilePojos.get(pos).getColor());

        TextView tv_numberplate=(TextView)obj2.findViewById(R.id.tv_numberplate);
        tv_numberplate.setText("Number Plate : "+getAllCarsProfilePojos.get(pos).getNumber_plate());


        TextView tv_status=(TextView)obj2.findViewById(R.id.tv_status);
        tv_status.setText("Current Status : "+getAllCarsProfilePojos.get(pos).getC_status());

        ImageView img_accept=(ImageView)obj2.findViewById(R.id.img_accept);
        img_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverData(getAllCarsProfilePojos.get(pos).getCid(),"Verified");
                //  Toast.makeText(context, ""+a1.get(pos).getPid()+a1.get(pos).getPid(), Toast.LENGTH_SHORT).show();

            }
        });

        ImageView img_deney=(ImageView)obj2.findViewById(R.id.img_deney);

        img_deney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverData(getAllCarsProfilePojos.get(pos).getCid(),"Not Verified");

            }
        });



        return obj2;
    }
    ProgressDialog progressDialog;
    public void serverData(String id,String status){
        progressDialog = new ProgressDialog(cnt);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        //  Toast.makeText(context, ""+id+status, Toast.LENGTH_SHORT).show();
        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<ResponseData> call = service.updatestatus(id,status);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(cnt,"Server issue",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent=new Intent(cnt, AdminVerifyActivity.class);
                    cnt.startActivity(intent);
                    Toast.makeText(cnt,"Status updated successfully",Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(cnt, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}