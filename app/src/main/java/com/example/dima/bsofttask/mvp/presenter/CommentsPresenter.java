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
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class CommentsPresenter extends MvpPresenter<CommentsView> {
    private Service service = Common.getRetrofitService();

    public void loadComments(final int photoId, boolean isRefreshed) {
        int page = 0;
        final String keyComment = photoId + "comment";
        if (!isRefreshed) {
            final String cacheComment = Paper.book().read(keyComment);
            if (cacheComment != null && !cacheComment.isEmpty() && !cacheComment.equals("null")) {
                ListComments listComments = new Gson().fromJson(cacheComment, ListComments.class);
                getViewState().onLoadResult(listComments.getComment(),photoId);
            }
            else {
                service.getListComment(Common.token, photoId, page).enqueue(new Callback<ListComments>() {
                    @Override
                    public void onResponse(Call<ListComments> call, Response<ListComments> response) {
                        if (response.isSuccessful()) {
                            ListComments list = response.body();

                            //Save to cache
                            Paper.book().write(keyComment, new Gson().toJson(response.body()));

                            getViewState().onLoadResult(list.getComment(), photoId);
                            getViewState().setRefreshing(false);
                            getViewState().success();
                        }
                        else
                        {
                            getViewState().error();
                        }
                    }

                    @Override
                    public void onFailure(Call<ListComments> call, Throwable t) {
                        getViewState().error();
                    }
                });
            }
        } else {
            getViewState().setRefreshing(true);
            service.getListComment(Common.token, photoId, page).enqueue(new Callback<ListComments>() {
                @Override
                public void onResponse(Call<ListComments> call, Response<ListComments> response) {
                    if (response.isSuccessful()) {
                        ListComments listComments = response.body();

                        //Save to cache
                        Paper.book().write(keyComment, new Gson().toJson(response.body()));

                        getViewState().setRefreshing(false);
                        getViewState().onLoadResult(listComments.getComment(),photoId);
                        getViewState().success();
                    }
                    else
                    {
                        getViewState().error();
                    }
                }

                @Override
                public void onFailure(Call<ListComments> call, Throwable t) {
                    getViewState().error();
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
