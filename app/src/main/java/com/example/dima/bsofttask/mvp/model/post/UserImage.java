package com.example.dima.bsofttask.mvp.model.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserImage implements Serializable {
    @SerializedName("base64Image")
    @Expose
    private String base64Image;
    @SerializedName("date")
    @Expose
    private long date;
    @SerializedName("lat")
    @Expose
    private Integer lat;
    @SerializedName("lng")
    @Expose
    private Integer lng;

    private String dt;
    public UserImage() {

    }

    public UserImage(String base64Image, Integer lat, Integer lng) {
        this.base64Image = base64Image;
        dt = new SimpleDateFormat("dd.MM.yyyy"
                ,new Locale("en","US"))
                .format(new Date());

        date = toLongDate();
        this.lat = lat;
        this.lng = lng;
    }
    private long toLongDate()
    {
       SimpleDateFormat timeStamp = new SimpleDateFormat("dd.MM.yyyy"
                ,new Locale("en","US"));
        try {
            Date d = timeStamp.parse(dt);
            long currentDate = d.getTime()/1000;
            return currentDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }


    public Integer getLat() {
        return lat;
    }

    public void setLat(Integer lat) {
        this.lat = lat;
    }

    public Integer getLng() {
        return lng;
    }

    public void setLng(Integer lng) {
        this.lng = lng;
    }

}
