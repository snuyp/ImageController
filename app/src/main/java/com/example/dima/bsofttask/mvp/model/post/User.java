package com.example.dima.bsofttask.mvp.model.post;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.regex.Pattern;

/**
 * Created by Dima on 25.05.2018.
 */

public class User {
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("password")
    @Expose
    private String password;
    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;

    }
    public int isValidData() {
        //1. Check email is empty
        //2. Check email is matches pattern
        //3. Check password length > 7
        if (TextUtils.isEmpty(getLogin())) {
            return 0;
        } else if (!Pattern.compile("[a-z0-9]*").matcher(getLogin()).matches()) {
            return 1;
        } else if (getPassword().length() <= 7) {
            return 2;
        } else
            return -1;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
