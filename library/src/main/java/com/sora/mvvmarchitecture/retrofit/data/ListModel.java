package com.sora.mvvmarchitecture.retrofit.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListModel<T> {
    @SerializedName("data_list")
    private List<T> dataList;
    @SerializedName("count")
    private int count;

    public List<T> getDataList() {
        return dataList;
    }

    public int getCount() {
        return count;
    }
}
