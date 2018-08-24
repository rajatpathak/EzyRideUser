package appentus.ezyrideuser.apiConnect;

import appentus.ezyrideuser.Model.BookRideModel;
import appentus.ezyrideuser.Model.FavPlaceModel;
import appentus.ezyrideuser.Model.LoginModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {


    //Login method
    @FormUrlEncoded
    @POST("api/rider_login")
    Call<LoginModel> login(
            @Field("mobile") String mobile,
            @Field("device_token") String device_token,
            @Field("device_type") String device_type
    );
    //OTP VERIFICATION method
    @FormUrlEncoded
    @POST("api/rider_verify_otp")
    Call<LoginModel> otpVerification(
            @Field("otp") String otp,
            @Field("rider_id") String rider_id
    );

    //OTP VERIFICATION method
    @FormUrlEncoded
    @POST("api/update_rider")
    Call<LoginModel> updatedName(
            @Field("rider_id") String rider_id,
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("rider_mobile") String rider_mobile
            );



    //OTP VERIFICATION method
    @FormUrlEncoded
    @POST("api/booking_vehicle")
    Call<BookRideModel> bookVehicle(
            @Field("pick_location") String pick_location,
            @Field("pick_lat") String pick_lat,
            @Field("pick_long") String pick_long,
            @Field("drop_location") String drop_location,
            @Field("drop_lat") String drop_lat,
            @Field("drop_long") String drop_long,
            @Field("user_id") String user_id,
            @Field("vehicle_id") String vehicle_id,
            @Field("booking_date") String booking_date,
            @Field("pick_time") String pick_time
            );

    //OTP VERIFICATION method
    @FormUrlEncoded
    @POST("api/add_fav_location")
    Call<BookRideModel> addFavLoc(
            @Field("rider_id") String rider_id,
            @Field("location") String location,
            @Field("lat") String lat,
            @Field("lang") String lang
    );

  //Add Fav place
    @FormUrlEncoded
    @POST("api/get_all_location")
    Call<FavPlaceModel> getAllFav(
            @Field("rider_id") String rider_id
    );

  //Remove Fav place
    @FormUrlEncoded
    @POST("api/delete_fav_location")
    Call<FavPlaceModel> removeFavLoc(
            @Field("fav_id") String fav_id
    );

}
