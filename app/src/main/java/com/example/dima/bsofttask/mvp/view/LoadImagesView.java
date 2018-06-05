package com.example.dima.bsofttask.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.dima.bsofttask.mvp.model.Image;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface LoadImagesView  extends MvpView{
    void onLoadResult(List<Image> listImage);

    void setRefreshing(boolean isRefresh);
}
