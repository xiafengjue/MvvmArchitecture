package com.sora.mvvmarchitecture.framwork.network;

import android.annotation.SuppressLint;
import android.content.Context;

public class NetToolKit {
    @SuppressLint("StaticFieldLeak")
    private static ConnectivityChangeObserver changeObserver;
    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    public static void init(Context context) {
        sContext = context;
    }

    public static ConnectivityChangeObserver getConnectivityChangeObserver() {
        if (changeObserver == null) {
            changeObserver = new ConnectivityChangeObserver(sContext);
        }
        return changeObserver;
    }
}
