package com.example.dima.bsofttask.mvp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListImages {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<Image> listImage;

    public ListImages() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Image> getListImage() {
        return listImage;
    }

    public void setListImage(List<Image> listImage) {
        this.listImage = listImage;
    }
}

