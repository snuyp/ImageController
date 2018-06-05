package com.example.dima.bsofttask.mvp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Comment {
    @SerializedName("date")
    @Expose
    private Integer date;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("text")
    @Expose
    private String text;

    public Comment() {
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public String getDateFormat() {
        Date dt=new Date(date * 1000);
        SimpleDateFormat df2 =new SimpleDateFormat("dd.MM.yyyy [HH:mm]"
                ,new Locale("en","US"));
        String dateText = df2.format(dt);
        return dateText;
    }
}
