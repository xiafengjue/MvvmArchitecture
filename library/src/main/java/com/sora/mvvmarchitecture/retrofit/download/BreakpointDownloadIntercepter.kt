package com.yixun.yxprojectlib.retrofit.download

import okhttp3.Interceptor
import okhttp3.Response

import java.io.IOException

/**
 * 带有进度的断点下载拦截器
 */
class BreakpointDownloadIntercepter(private val startPoint: Long,private val progressListener: ProgressListener) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        val request =
            chain.request().newBuilder().addHeader("RANGE", "bytes=$startPoint-").build()//startpos 就是数据库记录的已经下载的大小
        val originalResponse = chain.proceed(request)
        return originalResponse.newBuilder().body(ProgressResponseBody(originalResponse.body(), progressListener)).build()
    }
}
