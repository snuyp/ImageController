package com.example.dima.bsofttask.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toolbar;

import com.example.dima.bsofttask.R;
import com.example.dima.bsofttask.ui.adapter.AuthFragmentAdapter;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = findViewById(R.id.viewPager);;
        mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
        AuthFragmentAdapter fragmentAdapter = new AuthFragmentAdapter(getSupportFragmentManager(),this);
        mViewPager.setAdapter(fragmentAdapter);
        mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

    }
}