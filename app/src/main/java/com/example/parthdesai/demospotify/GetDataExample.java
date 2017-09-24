package com.example.parthdesai.demospotify;

import android.annotation.TargetApi;
import android.os.Build;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by krishna on 2017-08-24.
 */

public class GetDataExample {
    OkHttpClient client = new OkHttpClient();


    @TargetApi(Build.VERSION_CODES.KITKAT)
    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer "+MainActivity.Token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
//.addHeader("Authorization", "Bearer BQAST_rG925cFN2FiDaY6GL4HICZxQm2x23WsG5nb3VUo31pUTqQ1PD2WNmemAVEBMMiJ1_IDx3tKHqC8NT6FX7X0WRn7xSFcCwURrp96qF_SmyFtC2FQBxrCTm34Os84cNOsz16\"")
