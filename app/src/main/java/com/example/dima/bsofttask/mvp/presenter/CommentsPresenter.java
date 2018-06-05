package com.example.dima.bsofttask.mvp.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.dima.bsofttask.common.Common;
import com.example.dima.bsofttask.mvp.model.ListComments;
import com.example.dima.bsofttask.mvp.model.ListImages;
import com.example.dima.bsofttask.mvp.model.post.UserComment;
import com.example.dima.bsofttask.mvp.view.CommentsView;
import com.example.dima.bsofttask.remote.Service;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class CommentsPresenter extends MvpPresenter<CommentsView> {
    private Service service = Common.getRetrofitService();

    public void loadComments(final int photoId, boolean isRefresh) {
        int page = 0;
        if (!isRefresh) {
            service.getListComment(Common.token, photoId, page).enqueue(new Callback<ListComments>() {
                @Override
                public void onResponse(Call<ListComments> call, Response<ListComments> response) {
                    if (response.isSuccessful()) {
                        ListComments list= response.body();
                        getViewState().onLoadResult(list.getComment(),photoId);
                        getViewState().setRefreshing(false);
                    }
                }

                @Override
                public void onFailure(Call<ListComments> call, Throwable t) {

                }
            });
        } else {
            getViewState().setRefreshing(true);
            service.getListComment(Common.token, photoId, page).enqueue(new Callback<ListComments>() {
                @Override
                public void onResponse(Call<ListComments> call, Response<ListComments> response) {
                    if (response.isSuccessful()) {
                        ListComments listComments = response.body();
                        getViewState().setRefreshing(false);
                        getViewState().onLoadResult(listComments.getComment(),photoId);
                    }
                }

                @Override
                public void onFailure(Call<ListComments> call, Throwable t) {

                }
            });
        }
    }

    public void sendComment(int photoId, String comment) {

        UserComment userComment = new UserComment(comment);
        service.sendComment(Common.token, photoId, userComment).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();

                } else {

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("On Failure", t.getMessage());
            }
        });
        loadComments(photoId,true);
    }
}
