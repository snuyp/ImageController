package com.example.dima.bsofttask.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dima.bsofttask.R;
import com.example.dima.bsofttask.ui.fragment.SignInFragment;
import com.example.dima.bsofttask.ui.fragment.SignUpFragment;

/**
 * Created by Dima on 25.05.2018.
 */

public class AuthFragmentAdapter  extends FragmentPagerAdapter {
    private Context context;
    public AuthFragmentAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return SignInFragment.getInstance();
        } else if (position == 1) {
            return SignUpFragment.getInstance();
        } else return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.sign_in);
            case 1:
                return context.getString(R.string.sign_up);
        }
        return "";
    }
}
