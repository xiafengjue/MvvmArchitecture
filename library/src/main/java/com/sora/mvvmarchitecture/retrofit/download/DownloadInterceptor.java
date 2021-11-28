package com.sora.mvvmarchitecture.retrofit.download;

import com.yixun.yxprojectlib.retrofit.download.ProgressResponseBody;import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

/**
 * Author liang
 * Date 2018/12/20
 * Dsc:
 */
public class DownloadInterceptor implements Interceptor {
    ProgressListener progressListener;

    public DownloadInterceptor(ProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response origin = chain.proceed(chain.request());
        return origin.newBuilder().body(new ProgressResponseBody(origin.body(), progressListener)).build();
    }
}