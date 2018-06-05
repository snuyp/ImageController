package com.example.dima.bsofttask.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.dima.bsofttask.common.Common;
import com.example.dima.bsofttask.mvp.model.Image;
import com.example.dima.bsofttask.mvp.model.ListImages;
import com.example.dima.bsofttask.mvp.view.LoadImagesView;
import com.example.dima.bsofttask.remote.Service;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class LoadImagesPresenter extends MvpPresenter<LoadImagesView> {
   // private List<Image> listImage = new ArrayList<>();
    private Service service = Common.getRetrofitService();
    private int page = 0;

    public void loadImages(boolean isRefresh) {
        if (!isRefresh) {
            service.getImages(Common.token, page).enqueue(new Callback<ListImages>() {
                @Override
                public void onResponse(Call<ListImages> call, Response<ListImages> response) {
                    if (response.isSuccessful()) {
                        getViewState().onLoadResult(response.body().getListImage());
                        getViewState().setRefreshing(false);
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<ListImages> call, Throwable t) {

                }
            });
        } else {
            getViewState().setRefreshing(true);
            service.getImages(Common.token, page).enqueue(new Callback<ListImages>() {
                @Override
                public void onResponse(Call<ListImages> call, Response<ListImages> response) {
                    if (response.isSuccessful()) {
                        getViewState().onLoadResult(response.body().getListImage());
                        getViewState().setRefreshing(false);

                    } else {

                    }
                }

                @Override
                public void onFailure(Call<ListImages> call, Throwable t) {

                }
            });
        }
    }
}
