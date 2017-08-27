package com.eighthour.makers.dreamgacha_android.network;


import com.eighthour.makers.dreamgacha_android.network.reponse.RecordResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;


/**
 * Created by Omjoon on 2017. 7. 30..
 */

public interface ApiService {

    @GET
    Call<String> getStrings();


//	@Multipart
//	@POST( "/api/member/create" )
//	retrofit2.Call<ResponseRegisterJson> goRegister( @Query( "kind" ) int kind, @Query( "token" ) String token,
//													 @Query( "email" ) String email,
//													 @Query( "nickname" ) String nickname,
//													 @Query( "password" ) String password,
//													 @Query( "gender" ) String gender,
//													 @Query( "birthday" ) String birthday,
//													 @retrofit2.http.Part( "description" ) okhttp3.RequestBody description,
//													 @retrofit2.http.Part MultipartBody.Part profileImage,
//													 @Query( "agent" ) String agent,
//													 @Query( "device_id" ) String device_id
//	);

    @Multipart
    @POST("dreams/")
    retrofit2.Call<RecordResponse> saveRecord(
            @retrofit2.http.Part("description") okhttp3.RequestBody description,
            @retrofit2.http.Part MultipartBody.Part recordFileName);
}