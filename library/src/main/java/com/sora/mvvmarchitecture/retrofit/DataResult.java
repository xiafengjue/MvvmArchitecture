package com.sora.mvvmarchitecture.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zheng on 17-10-30.
 */

public class DataResult<T> {
    @SerializedName("error_code")
    private int status_code;
    private String status;
    private T data;
    private String msg;

    public int getStatus_code() {
        return status_code;
    }

    public String getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }
}
