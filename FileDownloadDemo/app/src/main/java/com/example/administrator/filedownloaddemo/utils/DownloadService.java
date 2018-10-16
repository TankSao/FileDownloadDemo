package com.example.administrator.filedownloaddemo.utils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2018/10/16.
 */

public interface DownloadService {
    //下载文件
    @Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);
}
