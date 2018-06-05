package com.example.dima.bsofttask.common;

import com.example.dima.bsofttask.remote.Service;
import com.example.dima.bsofttask.mvp.model.RegistrationResponse;
import com.example.dima.bsofttask.remote.RetrofitClient;

/**
 * Created by Dima on 26.05.2018.
 */

public class Common {
    private static String BASE_URL = "http://junior.balinasoft.com";

    public static final int REQUEST_IMAGE_CAPTURE = 99;
    public static final String ARG_PHOTO_ID = "photo_id";
    public static final int PERMISSIONS_REQUEST_CODE = 98;

    public static String token = "token" ;

    public static Service getRetrofitService()
    {
        return RetrofitClient.getClient(BASE_URL).create(Service.class);
    }
}
