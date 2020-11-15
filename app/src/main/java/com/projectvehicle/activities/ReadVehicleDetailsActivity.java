package com.projectvehicle.activities;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.projectvehicle.EndPointUrl;
import com.projectvehicle.NumberPlateRecActivity;
import com.projectvehicle.R;
import com.projectvehicle.ResponseData;
import com.projectvehicle.RetrofitInstance;
import com.projectvehicle.Utils;
import com.projectvehicle.models.CarsModel;
import com.projectvehicle.models.EditProfilePojo;
import com.projectvehicle.models.StationDetailsPOJO;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.security.AccessController.getContext;

public class
ReadVehicleDetailsActivity extends AppCompatActivity  implements EasyPermissions.PermissionCallbacks{
    public static int REQ=1000;
    int PICK_IMAGE_REQUEST = 10;
    EditText et_title,et_description,et_fee,et_vehicle_num;
    Button bt_scan,bt_submit,bt_check,btn_season_img;
    String num_plate;
    Spinner spin_station_details;
    List<StationDetailsPOJO> a1;
    private static final String TAG = ReadVehicleDetailsActivity.class.getSimpleName();
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private static final String SERVER_PATH = "http://cegepclm.com/";
    private Uri uri;
    SharedPreferences sharedPreferences;
    String session;
    List<CarsModel> a2;
    EditText et_make,et_model,et_year,et_numberplate;
    String[] station_details;
    String station,lag,lat;
    RadioButton rbManual,rbScan;
    RadioGroup radioType;
    String cid,time;
    TextView tv_time;
    long start;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_vehicle_details);
        getStationDetails();


        tv_time=(TextView)findViewById(R.id.tv_time);

        getSupportActionBar().setTitle("Scan Number Plate");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        session = sharedPreferences.getString("uname", "def-val");
        et_make = (EditText) findViewById(R.id.et_make);
        et_model = (EditText) findViewById(R.id.et_model);
        et_year = (EditText) findViewById(R.id.et_color);
        et_title=(EditText)findViewById(R.id.et_title);
        et_description=(EditText)findViewById(R.id.et_description);
        et_fee=(EditText)findViewById(R.id.et_fee);
        et_vehicle_num=(EditText)findViewById(R.id.et_vehicle_num);
        spin_station_details=(Spinner)findViewById(R.id.spin_station_details);

        radioType=(RadioGroup)findViewById(R.id.radioType);
        rbManual=(RadioButton)findViewById(R.id.rbManual);
        rbScan=(RadioButton)findViewById(R.id.rbScan);
        rbManual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    bt_check.setVisibility(View.VISIBLE);
                    bt_scan.setVisibility(View.GONE);
                }else{
                    bt_check.setVisibility(View.GONE);
                    bt_scan.setVisibility(View.VISIBLE);
                }
            }
        });

        rbScan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    bt_check.setVisibility(View.GONE);
                    bt_scan.setVisibility(View.VISIBLE);
                }else{
                    bt_check.setVisibility(View.VISIBLE);
                    bt_scan.setVisibility(View.GONE);
                }
            }
        });


        bt_check=(Button)findViewById(R.id.bt_check);
        bt_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getCarDetails(et_vehicle_num.getText().toString());
               /* if (a2!= null){


                }
                else{
                    uploadImageToServer();
                }*/

            }
        });

        Button  bt_upload=(Button)findViewById(R.id.bt_upload);
        bt_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });

        bt_scan=(Button)findViewById(R.id.bt_scan);
        btn_season_img=(Button)findViewById(R.id.btn_season_img);
        bt_submit=(Button)findViewById(R.id.bt_submit);
        btn_season_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                openGalleryIntent.setType("image/*");
                startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
            }
        });
        long runTime = System.currentTimeMillis() - start;
        long seconds = runTime / 1000;
        time =""+seconds % 60;
        bt_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                start = System.currentTimeMillis();

                startActivityForResult(new Intent(getApplicationContext(),NumberPlateRecActivity.class),REQ);

                tv_time.setVisibility(View.VISIBLE);
                tv_time.setText("Processing Time : "+time+"secs");

            }
        });




        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spin_station_details.getSelectedItem().equals("Select Station")){
                    Toast.makeText(ReadVehicleDetailsActivity.this, "Please Select Station", Toast.LENGTH_SHORT).show();
                } else  {
                    int pos = spin_station_details.getSelectedItemPosition();
                    station=a1.get(pos).getStation_name();
                    lag=a1.get(pos).getLng();
                    lat=a1.get(pos).getLat();

                }
                //submitdata();

                if(cid==null){
                    uploadImageToServer("12");
                }
                else {
                    uploadImageToServer(cid);
                }


            }
        });


    }
    //,,,,,lat,lag
    private void uploadImageToServer(final String  cid){
        pd=new ProgressDialog(ReadVehicleDetailsActivity.this);
        pd.setTitle("Loading");
        pd.show();
        Map<String, String> map = new HashMap<>();
        map.put("title",et_title.getText().toString());
        map.put("description",et_description.getText().toString());
        map.put("fee",et_fee.getText().toString());
        map.put("number_plate",et_vehicle_num.getText().toString());
        map.put("sname",station);
        map.put("lat",lat);
        map.put("lng",lag);
        map.put("cid",cid);
        map.put("aname",session);
        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_PATH)

                .addConverterFactory(GsonConverterFactory.create())
                .build();
        EndPointUrl uploadImage = retrofit.create(EndPointUrl.class);
        Call<ResponseData> fileUpload = uploadImage.add_user_fine_details1(fileToUpload, map);
        fileUpload.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                Toast.makeText(ReadVehicleDetailsActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                startActivity(new Intent(ReadVehicleDetailsActivity.this,MunicipalHomeActivity.class));
                finish();
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(ReadVehicleDetailsActivity.this, "Error :"+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ){
            //num_plate=data.getStringExtra("pno");
            et_vehicle_num.setText(data.getStringExtra("pno"));
           // Toast.makeText(getApplicationContext(),data.getStringExtra("pno"), Toast.LENGTH_LONG).show();
            getCarDetails(data.getStringExtra("pno").trim());
        }

        if(requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK){
            uri = data.getData();
            if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                String filePath = getRealPathFromURIPath(uri, ReadVehicleDetailsActivity.this);
                file = new File(filePath);

            }else{
                EasyPermissions.requestPermissions(this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }


            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

                Uri selectedImg = data.getData();
                Bitmap b;
                try {
                    b = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImg);
                    if (b != null) {
                        InputImage image = InputImage.fromBitmap(b, 0);
                        TextRecognizer recognizer = TextRecognition.getClient();
                        Task<Text> result =
                                recognizer.process(image)
                                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                                            @Override
                                            public void onSuccess(Text visionText) {
                                                for (Text.TextBlock block : visionText.getTextBlocks()) {
                                                    Rect boundingBox = block.getBoundingBox();
                                                    Point[] cornerPoints = block.getCornerPoints();
                                                    String text = block.getText();
                                                    et_vehicle_num.setText(text);
                                                    for (Text.Line line : block.getLines()) {
                                                        // ...
                                                        Toast.makeText(ReadVehicleDetailsActivity.this,line.getText(),Toast.LENGTH_LONG).show();
                                                        for (Text.Element element : line.getElements()) {

                                                        }
                                                    }
                                                }
                                            }
                                        })
                                        .addOnFailureListener(
                                                new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        // Task failed with an exception
                                                        // ...
                                                    }
                                                });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }


    ProgressDialog pd;

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, ReadVehicleDetailsActivity.this);
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK){
//            uri = data.getData();
//            if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                String filePath = getRealPathFromURIPath(uri, ReadVehicleDetailsActivity.this);
//                file = new File(filePath);
//
//            }else{
//                EasyPermissions.requestPermissions(this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
//            }
//        }
//    }
//    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
//        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
//        if (cursor == null) {
//            return contentURI.getPath();
//        } else {
//            cursor.moveToFirst();
//            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//            return cursor.getString(idx);
//        }
//    }
//    File file;
//    @Override
//    public void onPermissionsGranted(int requestCode, List<String> perms) {
//        if(uri != null){
//            String filePath = getRealPathFromURIPath(uri, ReadVehicleDetailsActivity.this);
//            file = new File(filePath);
//            // uploadImageToServer();
//        }
//    }
//    @Override
//    public void onPermissionsDenied(int requestCode, List<String> perms) {
//        Log.d(TAG, "Permission has been denied");
//
//    }
    public  void submitdata()
    {
        pd= new ProgressDialog(ReadVehicleDetailsActivity.this);
        pd.setTitle("Please wait,Data is being submit...");
        pd.show();
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<ResponseData> call = apiService.add_user_fine_details(et_title.getText().toString(),et_description.getText().toString(),et_fee.getText().toString(),et_vehicle_num.getText().toString(),station,lat,lag);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                if (response.body().status.equals("true")) {
                    Toast.makeText(ReadVehicleDetailsActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    Log.i("msg", "" + response.body().message);
                    Intent i=new Intent(getApplicationContext(),MunicipalHomeActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(ReadVehicleDetailsActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Toast.makeText(ReadVehicleDetailsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        });
    }
    private void getStationDetails() {
        EndPointUrl apiService = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<StationDetailsPOJO>> call = apiService.getStationDetails();
        call.enqueue(new Callback<List<StationDetailsPOJO>>() {
            @Override
            public void onResponse(Call<List<StationDetailsPOJO>> call, Response<List<StationDetailsPOJO>> response) {
                if (response.body() != null) {
                    if(response.body().size()>0) {
                        a1 = response.body();
                        Log.d("TAG", "Response = " + a1);
                        //Toast.makeText(getContext(), a1.size() + " ", Toast.LENGTH_LONG).show();
                        station_details = new String[a1.size() + 1];
                        station_details[0] = "Select Station";
                        for (int i = 0; i < a1.size(); i++) {
                            station_details[i + 1] = a1.get(i).getStation_name();
                        }
                        ArrayAdapter aa = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, station_details);
                        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spin_station_details.setAdapter(aa);
                        spin_station_details.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                                if (pos > 0) {
                                    //Toast.makeText(ReadVehicleDetailsActivity.this, station_details[pos], Toast.LENGTH_LONG).show();
                                    //Toast.makeText(getApplicationContext(), mstatus[pos], Toast.LENGTH_LONG).show();

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                    }}

            }
            @Override
            public void onFailure(Call<List<StationDetailsPOJO>> call, Throwable t) {
                Log.d("TAG", "Response = " + t.toString());
            }
        });
    }


    ProgressDialog progressDialog;
    private void getCarDetails(final  String plate) {


        progressDialog = new ProgressDialog(ReadVehicleDetailsActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<CarsModel>> call = service.checkCarDetails(plate);

        call.enqueue(new Callback<List<CarsModel>>() {
            @Override
            public void onResponse(Call<List<CarsModel>> call, Response<List<CarsModel>> response) {

                progressDialog.dismiss();
                if(response.body()!= null) {
                    a2 = response.body();
                    if(a2==null){
                        Toast.makeText(getApplicationContext(),"No   data found",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(a2.size()<=0){
                        Toast.makeText(getApplicationContext(),"No Car data found",Toast.LENGTH_SHORT).show();
                        return;
                    }
                        CarsModel car = a2.get(0);
                    cid = car.getCid();


                    et_make.setText("  " + car.getMake());

                    et_model.setText("  " + car.getModel());


                    et_year.setText("  " + car.getColor());
                    // tv_phone.setText("  "+car.getPhonenumber());


                    /*et_make.setEnabled(false);
                    et_make.setFocusable(false);
                    et_make.setFocusableInTouchMode(false);
                    et_make.setClickable(false);

                    et_model.setEnabled(false);
                    et_model.setFocusable(false);
                    et_model.setFocusableInTouchMode(false);
                    et_model.setClickable(false);

                    et_year.setEnabled(false);
                    et_year.setFocusable(false);
                    et_year.setFocusableInTouchMode(false);
                    et_year.setClickable(false);*/
                }
                else {
                    Toast.makeText(ReadVehicleDetailsActivity.this,"No Records Found",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<CarsModel>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ReadVehicleDetailsActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, ReadVehicleDetailsActivity.this);
    }
    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
    File file;
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if(uri != null){
            String filePath = getRealPathFromURIPath(uri, ReadVehicleDetailsActivity.this);
            file = new File(filePath);
            // uploadImageToServer();
        }
    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");
    }
}
