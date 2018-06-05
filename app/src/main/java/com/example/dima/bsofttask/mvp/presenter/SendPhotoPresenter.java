package com.example.dima.bsofttask.mvp.presenter;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.dima.bsofttask.common.Common;
import com.example.dima.bsofttask.common.ImageLab;
import com.example.dima.bsofttask.mvp.model.ImageResponse;
import com.example.dima.bsofttask.mvp.model.post.UserImage;
import com.example.dima.bsofttask.mvp.view.SendView;
import com.example.dima.bsofttask.remote.Service;
import com.google.gson.JsonObject;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class SendPhotoPresenter extends MvpPresenter<SendView> {
    @SuppressLint("StaticFieldLeak")
    public void toSendPhoto(final UserImage userImage, final ImageLab imageLab) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                imageLab.encodeToStringBase64();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Service service = Common.getRetrofitService();
                userImage.setBase64Image(imageLab.getStringBase64Binary());
                service.sendImage(Common.token, userImage).enqueue(new Callback<ImageResponse>() {
                    @Override
                    public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                        if (response.isSuccessful()) {
                            Toasty.success(imageLab.getContext(), "").show();
                        } else {
                            Toasty.error(imageLab.getContext(), "").show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ImageResponse> call, Throwable t) {
                        Log.e("On Failure", t.getMessage());
                        Toasty.error(imageLab.getContext(), "").show();
                    }
                });
            }
        }.execute();
    }
}

