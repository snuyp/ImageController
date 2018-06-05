package com.example.dima.bsofttask.mvp.view;
import android.content.Intent;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Dima on 25.05.2018.
 */
@StateStrategyType(SkipStrategy.class)
public interface LoginView extends MvpView {
    void onLoginResult(Integer messageResult);
    void onLoginError(Integer messageError);
    void onResponseError(String error);
}
