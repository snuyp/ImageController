package com.example.dima.bsofttask.mvp.view;

import android.content.Intent;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.dima.bsofttask.common.ImageLab;
import com.example.dima.bsofttask.mvp.model.Image;
import com.example.dima.bsofttask.mvp.model.ListImages;
import com.example.dima.bsofttask.ui.fragment.PhotoViewFragment;

import java.io.File;
import java.util.List;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface PhotoView extends MvpView{
    void takePicture(Intent intent);
    void success(String message);
    void showPhoto(PhotoViewFragment photoView);
}
