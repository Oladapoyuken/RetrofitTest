package com.example.retrofittest;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIsInterface {
    @FormUrlEncoded
    @POST("save_user.php")
    Call<SaveDataModel> sendData(
        @Field("NAME") String name,
        @Field("ROLL") int roll
    );

    @FormUrlEncoded
    @POST("get_user.php")
    Call<GetDataModel> getData(
            @Field("ROLL") int roll
    );

    @FormUrlEncoded
    @POST("save_image.php")
    Call<SaveImageModel> sendImage(
            @Field("EN_IMAGE") String encoded_img
    );
}
