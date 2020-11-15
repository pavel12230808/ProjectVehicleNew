package com.projectvehicle.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.projectvehicle.R;
import com.projectvehicle.models.CarsModel;

import java.util.List;

public class GetAllCarsAdapter  extends BaseAdapter {

    List<CarsModel> getAllCarsProfilePojos;
    Context cnt;
    public GetAllCarsAdapter(List<CarsModel> ar, Context cnt)
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
        img_accept.setVisibility(View.GONE);

        ImageView img_deney=(ImageView)obj2.findViewById(R.id.img_deney);
        img_deney.setVisibility(View.GONE);



        return obj2;
    }
    /*ProgressDialog progressDialog;
    public void serverData(String id){
        progressDialog = new ProgressDialog(cnt);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        ApiService service = RetroClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseData> call = service.deleteTeam(id);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(cnt,"Server issue",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent=new Intent(cnt, AllTeamsActivity.class);
                    cnt.startActivity(intent);
                    Toast.makeText(cnt,"Deleted successfully",Toast.LENGTH_SHORT).show();
                    ((Activity)cnt).finish();

                }
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(cnt, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void alertDioluge(final String id){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(cnt);
        builder1.setTitle("Alert !!!");
        builder1.setMessage("Do you want to Delete the Team.");  //message we want to show the end user
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        serverData(id);
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }*/



}