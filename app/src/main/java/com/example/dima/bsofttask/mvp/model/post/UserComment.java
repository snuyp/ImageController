package com.example.dima.bsofttask.mvp.model.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserComment {
    @SerializedName("text")
    @Expose
    public String text;

    public UserComment() {

    }

    public UserComment(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
