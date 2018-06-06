package com.example.dima.bsofttask.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dima.bsofttask.R;
import com.example.dima.bsofttask.mvp.model.Comment;
import com.example.dima.bsofttask.mvp.model.Image;
import com.example.dima.bsofttask.mvp.presenter.CommentsPresenter;
import com.example.dima.bsofttask.mvp.view.CommentsView;
import com.example.dima.bsofttask.ui.adapter.CommentsAdapter;
import com.example.dima.bsofttask.ui.adapter.ListImageAdapter;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class InfoActivity extends MvpAppCompatActivity implements CommentsView {
    @InjectPresenter
    CommentsPresenter commentsPresenter;

    private TextView datePhotoTitle;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView photoImage;
    private Image photo;
    private ImageView addCommentView;
    private EditText editComment;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CommentsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_info);

        photo = (Image) getIntent().getSerializableExtra("image");

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_comment);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                commentsPresenter.loadComments(photo.getId(),true);
            }
        });

        photoImage = findViewById(R.id.top_image);
        Glide.with(getApplicationContext())
                .load(photo.getUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_terrain_black_24dp))
                .into(photoImage);

        datePhotoTitle = findViewById(R.id.top_title);
        datePhotoTitle.setText(photo.getDateFormat());
        commentsPresenter.loadComments(photo.getId(),false);

        editComment = findViewById(R.id.add_comment);
        addCommentView = findViewById(R.id.button_add_comments);
        addCommentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentsPresenter.sendComment(photo.getId(),editComment.getText().toString());
                editComment.setText("");
            }
        });

        recyclerView = findViewById(R.id.recycler_comment);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void setRefreshing(boolean isRefresh) {
        swipeRefreshLayout.setRefreshing(isRefresh);
    }

    @Override
    public void onLoadResult(List<Comment> commentList,int imageId) {
        adapter = new CommentsAdapter(getBaseContext(),commentList,imageId);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void success() {
        Toasty.success(getApplicationContext(),getString(R.string.success)).show();
    }

    @Override
    public void error() {
        Toasty.error(getApplicationContext(),getString(R.string.error)).show();
    }

}
