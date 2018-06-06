package com.example.dima.bsofttask.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.dima.bsofttask.common.Common;
import com.example.dima.bsofttask.mvp.model.Image;
import com.example.dima.bsofttask.mvp.model.ListImages;
import com.example.dima.bsofttask.mvp.view.LoadImagesView;
import com.example.dima.bsofttask.remote.Service;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class LoadImagesPresenter extends MvpPresenter<LoadImagesView> {
    // private List<Image> listImage = new ArrayList<>();
    private Service service = Common.getRetrofitService();
    private int page = 0;

    public void loadImages(boolean isRefreshed) {
        if (!isRefreshed) {
            String cacheImage = Paper.book().read("cacheImage");
            if (cacheImage != null && !cacheImage.isEmpty() && !cacheImage.equals("null")) {
                ListImages listImages = new Gson().fromJson(cacheImage, ListImages.class);
                getViewState().onLoadResult(listImages.getListImage());

            } else {
                service.getImages(Common.token, page).enqueue(new Callback<ListImages>() {
                    @Override
                    public void onResponse(Call<ListImages> call, Response<ListImages> response) {
                        if (response.isSuccessful()) {
                            getViewState().onLoadResult(response.body().getListImage());
                            getViewState().setRefreshing(false);

                            getViewState().success();
                            //Save to cache
                            Paper.book().write("cacheImage", new Gson().toJson(response.body()));

                        } else {
                            getViewState().error();
                        }
                    }

                    @Override
                    public void onFailure(Call<ListImages> call, Throwable t) {
                        getViewState().error();
                    }
                });
            }
        } else {
            getViewState().setRefreshing(true);
            service.getImages(Common.token, page).enqueue(new Callback<ListImages>() {
                @Override
                public void onResponse(Call<ListImages> call, Response<ListImages> response) {
                    if (response.isSuccessful()) {
                        Paper.book().write("cacheImage", new Gson().toJson(response.body()));
                        getViewState().onLoadResult(response.body().getListImage());
                        getViewState().setRefreshing(false);
                            getViewState().success();
                    } else {
                        getViewState().error();
                    }
                }

                @Override
                public void onFailure(Call<ListImages> call, Throwable t) {
                        getViewState().error();
                }
            });
        }
    }
}
