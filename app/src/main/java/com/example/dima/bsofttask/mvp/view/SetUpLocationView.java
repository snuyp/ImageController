package com.example.dima.bsofttask.mvp.view;

import android.location.Location;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;


public interface SetUpLocationView extends MvpView{
    void setUpLocation();
    void displayLocation();
    void getLocation(Location location);
}
