package com.sora.mvvmarchitecture.retrofit.data;

public class Bool {
    private int is_success;

    public int getIs_success() {
        return is_success;
    }

    public boolean success() {
        return is_success == 1;
    }
}
