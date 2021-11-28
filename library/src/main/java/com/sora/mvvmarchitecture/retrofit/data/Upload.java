package com.sora.mvvmarchitecture.retrofit.data;

/**
 * Created by zheng on 17-11-3.
 * 上传返回数据
 */

public class Upload {
    public static class FileSizeType {
        public static final int VIDEO = 0;
        public static final int HEAD = 0;
        public static final int IMAGE_1080_640 = 1;
        public static final int IMAGE_640_1080 = 2;
        public static final int IMAGE_512_512 = 3;
        public static final int IMAGE_512_600 = 4;
        public static final int IMAGE_667_375 = 5;
        public static final int IMAGE_640_139 = 6;

    }

    private String cover_md5;
    private String cover_url;
    private String hieght;
    private String md5;
    private String url;
    private String width;

    public String getCover_md5() {
        return cover_md5;
    }

    public String getCover_url() {
        return cover_url;
    }

    public String getHieght() {
        return hieght;
    }

    public String getMd5() {
        return md5;
    }

    public String getUrl() {
        return url;
    }

    public String getWidth() {
        return width;
    }
}
