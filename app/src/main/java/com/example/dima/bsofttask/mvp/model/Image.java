package com.example.dima.bsofttask.mvp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Image  implements Serializable{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("date")
    @Expose
    private long date;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double lng;

    private String dateFormat;

    public Image() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getDateFormat() {
        Date dt=new Date(date* 1000);
        SimpleDateFormat df2 =new SimpleDateFormat("dd.MM.yyyy"
                ,new Locale("en","US"));
        String dateText = df2.format(dt);;
        return dateText;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
}
