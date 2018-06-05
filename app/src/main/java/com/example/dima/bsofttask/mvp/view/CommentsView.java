package com.example.dima.bsofttask.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.dima.bsofttask.mvp.model.Comment;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface CommentsView extends MvpView {
    void setRefreshing(boolean isRefresh);
    void onLoadResult(List<Comment> comment);

}
