package com.example.dima.bsofttask.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.dima.bsofttask.R;
import com.example.dima.bsofttask.mvp.model.Image;
import com.example.dima.bsofttask.mvp.presenter.LoadImagesPresenter;
import com.example.dima.bsofttask.mvp.presenter.PhotoPresenter;
import com.example.dima.bsofttask.mvp.view.LoadImagesView;
import com.example.dima.bsofttask.ui.adapter.ListImageAdapter;

import java.util.List;


public class PhotoFragment extends MvpAppCompatFragment implements LoadImagesView {
    @InjectPresenter
    LoadImagesPresenter loadImagePresenter;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ListImageAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private static PhotoFragment photoFragment = null;

    public static PhotoFragment getInstance() {
        if (photoFragment == null) {
            photoFragment = new PhotoFragment();
        }
        return photoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_photo,container,false);
        recyclerView = v.findViewById(R.id.recycler_recent);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout = v.findViewById(R.id.swipe_refresh_recents);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadImagePresenter.loadImages(true);
            }
        });
        loadImagePresenter.loadImages(false);
        return v;
    }

    @Override
    public void onLoadResult(List<Image> listImage) {
        adapter = new ListImageAdapter(getContext(),listImage);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void setRefreshing(boolean isRefresh) {
        swipeRefreshLayout.setRefreshing(isRefresh);
    }

}
