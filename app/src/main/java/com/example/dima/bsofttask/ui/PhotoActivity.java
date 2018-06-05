package com.example.dima.bsofttask.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.dima.bsofttask.R;
import com.example.dima.bsofttask.common.Common;
import com.example.dima.bsofttask.mvp.model.Image;
import com.example.dima.bsofttask.mvp.presenter.LoadImagesPresenter;
import com.example.dima.bsofttask.mvp.presenter.PhotoPresenter;
import com.example.dima.bsofttask.mvp.view.LoadImagesView;
import com.example.dima.bsofttask.mvp.view.PhotoView;
import com.example.dima.bsofttask.ui.adapter.ListImageAdapter;
import com.example.dima.bsofttask.ui.fragment.PhotoViewFragment;

import java.util.List;

import es.dmoral.toasty.Toasty;


public class PhotoActivity extends MvpAppCompatActivity implements PhotoView,LoadImagesView {
    @InjectPresenter
    PhotoPresenter photoPresenter;
    @InjectPresenter
    LoadImagesPresenter loadImagePresenter;

    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;

    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ListImageAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoPresenter.toTakePhoto(getBaseContext());
            }
        });

        recyclerView = findViewById(R.id.recycler_recent);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_recents);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadImagePresenter.loadImages(true);
            }
        });
        loadImagePresenter.loadImages(false);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        else if ( requestCode == Common.REQUEST_IMAGE_CAPTURE) {
            photoPresenter.updatePhotoView();
        }

    }

    @Override
    public void takePicture(Intent takePictureIntent) {
        startActivityForResult(takePictureIntent, Common.REQUEST_IMAGE_CAPTURE);
    }

    @Override
    public void onLoadResult(List<Image> listImage) {
        adapter = new ListImageAdapter(getBaseContext(),listImage);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void success(String message) {
        Toasty.info(getBaseContext(),message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showPhoto(PhotoViewFragment photoView) {
        FragmentManager fragmentManager = getFragmentManager();
        photoView.show(fragmentManager,Common.ARG_PHOTO_ID);
    }

    @Override
    public void setRefreshing(boolean isRefresh) {
        swipeRefreshLayout.setRefreshing(isRefresh);
    }

}
