package com.example.dima.bsofttask.mvp.model;

/**
 * Created by Dima on 26.05.2018.
 */

public class Data {
    private Integer userId;
    private String login;
    private String token;

    public Data() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
