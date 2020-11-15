package com.projectvehicle;

import com.projectvehicle.models.AuthorityModel;
import com.projectvehicle.models.CarsModel;
import com.projectvehicle.models.ComplaintModel;
import com.projectvehicle.models.EditProfilePojo;
import com.projectvehicle.models.PaymentDuePojo;
import com.projectvehicle.models.StationDetailsPOJO;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface EndPointUrl {

    @GET("SmartTowingSystem/user_registration.php")
    Call<ResponseData> userRegistration(@Query("first_name") String first_name,
                                        @Query("last_name") String last_name,
                                        @Query("phonenumber") String phonenumber,
                                        @Query("emailid") String emailid,
                                        @Query("pwd") String pwd);


    @GET("SmartTowingSystem/authority_registration.php")
    Call<ResponseData> authorityRegistration(@Query("name") String name,
                                             @Query("emailid") String emailid,
                                             @Query("phno") String phno,
                                             @Query("pwd") String pwd);

    @GET("SmartTowingSystem/update_authority.php")
    Call<ResponseData> updateAuthority(@Query("name") String name,
                                       @Query("emailid") String emailid,
                                       @Query("phno") String phno,
                                       @Query("pwd") String pwd);



    @GET("SmartTowingSystem/updateauthoritydetails.php")
    Call<ResponseData> updateauthoritydetails(@Query("name") String name,
                                              @Query("emailid") String emailid,
                                              @Query("phno") String phno,
                                              @Query("pwd") String pwd);





    @GET("SmartTowingSystem/user_login.php")
    Call<ResponseData> userLogin(
            @Query("emailid") String emailid,
            @Query("pwd") String pwd
    );

    @GET("/SmartTowingSystem/authority_login.php?")
    Call<ResponseData> authority_login(
            @Query("username") String username,
            @Query("pwd") String pwd
    );

    @GET("SmartTowingSystem/admin_login.php?")
    Call<ResponseData> admin_login(
            @Query("username") String username,
            @Query("pwd") String pwd
    );

    @Multipart
    @POST("SmartTowingSystem/user_registration.php")
    Call<ResponseData> user_registration(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap

    );


    @GET("SmartTowingSystem/change_pwd.php")
    Call<ResponseData> change_password(
            @Query("emailid") String emailid,
            @Query("pwd") String pwd
    );



    @GET("SmartTowingSystem/update_user_profile.php")
    Call<ResponseData> update_user_profile(

            @Query("first_name") String fname,
            @Query("last_name") String lname,
            @Query("phonenumber") String phone,
            @Query("emailid") String email,
            @Query("pwd") String pwd
    );

    @GET("SmartTowingSystem/forgotPassword.php")
    Call<ResponseData> forgotPassword
            (

                    @Query("emailid") String emailid
            );


    @Multipart
    @POST("SmartTowingSystem/test.php?")
    Call<ResponseData> test(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap

    );
    @GET("SmartTowingSystem/getUserProfile.php")
    Call<List<EditProfilePojo>> getMyProfile(@Query("uname") String email);



    @GET("SmartTowingSystem/getAuthorityProfile.php")
    Call<List<AuthorityModel>> getAuthorityProfile(@Query("uname") String email);


    @Multipart
    @POST("SmartTowingSystem/addcar.php?")
    Call<ResponseData> addcar(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap

    );

    @Multipart
    @POST("SmartTowingSystem/authority_registration.php?")
    Call<ResponseData> addAuthority(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap

    );


    @GET("/SmartTowingSystem/getmycars.php?")
    Call<List<CarsModel>> getmycars(@Query("uname") String uname);





    @GET("/SmartTowingSystem/checkCarDetails.php?")
    Call<List<CarsModel>> checkCarDetails(@Query("plate") String plate);

    @GET("/SmartTowingSystem/getcars.php?")
    Call<List<CarsModel>> getcars();


    @GET("/SmartTowingSystem/getcomplaints.php?")
    Call<List<ComplaintModel>> getcomplaints();


    @GET("/SmartTowingSystem/updatecarstatus.php")
    Call<ResponseData> updatestatus(
            @Query("id") String id,
            @Query("status") String status

    );

    @Headers("Cache-Control: max-age=1")
    @GET("SmartTowingSystem/getAuthoritiesList.php")
    Call<List<AuthorityModel>> getAuthorityList();

    @GET("SmartTowingSystem/deleteAuthorities.php")
    Call<ResponseData> deleteAuthority
            (@Query("uname") String emailid
            );

    @GET("SmartTowingSystem/add_user_fine_details.php")
    Call<ResponseData> add_user_fine_details(

            @Query("title") String title,
            @Query("description") String description,
            @Query("fee") String fee,
            @Query("number_plate") String number_plate,
            @Query("sname") String sname,
            @Query("lat") String lat,
            @Query("lng") String lng
    );

    @Multipart
    @POST("SmartTowingSystem/add_user_fine_details.php")
    Call<ResponseData> add_user_fine_details1(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap
    );




    @GET("SmartTowingSystem/addcomplaint.php")
    Call<ResponseData> addcomplaint(

            @Query("title") String title,
            @Query("problem") String problem,
            @Query("email") String email

    );


    @GET("SmartTowingSystem/getFineDetails.php?")
    Call<List<PaymentDuePojo>> getFineDetails(
            @Query("number_plate") String number_plate
    );

    @GET("SmartTowingSystem/search.php?")
    Call<List<PaymentDuePojo>> search(
            @Query("number_plate") String number_plate
    );



    @GET("SmartTowingSystem/getPendingFines.php?")
    Call<List<PaymentDuePojo>> getPendingFines(
            @Query("number_plate") String number_plate
    );

    @GET("SmartTowingSystem/getPaidFines.php?")
    Call<List<PaymentDuePojo>> getPaidFines(
            @Query("number_plate") String number_plate
    );

    @GET("SmartTowingSystem/getAllPaidFines.php?")
    Call<List<PaymentDuePojo>> getAllPaidFines();

    @GET("SmartTowingSystem/getAllPendingFines.php?")
    Call<List<PaymentDuePojo>> getAllPendingFines();

    @GET("SmartTowingSystem/getMyFines.php?")
    Call<List<PaymentDuePojo>> getMyFines(
            @Query("aname") String aname
    );



    @GET("SmartTowingSystem/payFines.php?")
    Call<ResponseData> payFines(
            @Query("id") String id
    );

//    @GET("/SmartTowingSystem/update_fcm_token.php?")
//    Call<ResponseData> update_fcm_token(
//            @Query("fcm_token") String fcm_token,
//            @Query("number_plate") String number_plate);

    @GET("/SmartTowingSystem/getStationDetails.php?")
    Call<List<StationDetailsPOJO>> getStationDetails();

    @GET("/SmartTowingSystem/update_fcm_token.php?")
    Call<ResponseData> update_fcm_token(
            @Query("fcm_token") String fcm_token,
            @Query("uname") String uname);
}