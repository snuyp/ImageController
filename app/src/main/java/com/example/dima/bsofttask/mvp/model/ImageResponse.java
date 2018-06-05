package com.example.dima.bsofttask.mvp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageResponse {
    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("data")
    @Expose
    private Image image;

    public ImageResponse() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
