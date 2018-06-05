package com.example.dima.bsofttask.remote;

import com.example.dima.bsofttask.mvp.model.Comment;
import com.example.dima.bsofttask.mvp.model.ImageResponse;
import com.example.dima.bsofttask.mvp.model.ListComments;
import com.example.dima.bsofttask.mvp.model.ListImages;
import com.example.dima.bsofttask.mvp.model.RegistrationResponse;
import com.example.dima.bsofttask.mvp.model.post.User;
import com.example.dima.bsofttask.mvp.model.post.UserComment;
import com.example.dima.bsofttask.mvp.model.post.UserImage;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Dima on 26.05.2018.
 */

public interface Service {
    @POST("/api/account/signup")
    Call<RegistrationResponse> signUpUser(@Body User user);

    @POST("/api/account/signin")
    Call<RegistrationResponse> signInUser(@Body User user);


    @POST("/api/image")
    Call<ImageResponse> sendImage(@Header("Access-Token") String token, @Body UserImage userImage);

    @GET("/api/image")
    Call<ListImages> getImages(
            @Header("Access-Token") String token,
            @Query("page") int page
    );

    @GET("/api/image/{imageId}/comment")
    Call<ListComments> getListComment
            (@Header("Access-Token") String token,
             @Path("imageId") int imageId,
             @Query("page") int page);

    @POST("/api/image/{imageId}/comment")
    Call<JsonObject> sendComment
            (@Header("Access-Token") String token,
             @Path("imageId") int imageId,
             @Body UserComment userComment
             );

//    @POST("/api/image")
//    Call<JsonObject> getSomething(@Header("Access-Token") String token, @Body UserImage userImage);


}
