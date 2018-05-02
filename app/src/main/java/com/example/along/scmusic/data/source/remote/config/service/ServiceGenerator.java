package com.example.along.scmusic.data.source.remote.config.service;

import com.example.along.scmusic.utils.Constant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static final int TIMEOUT = 15000;
    private static Retrofit sRetrofit;

    private static HttpLoggingInterceptor httpLoggingInterceptor =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    public static <T> T createService(Class<T> serviceClass) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS);
        builder.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS);
        builder.addInterceptor(httpLoggingInterceptor);
        builder.retryOnConnectionFailure(true);

        Gson gson = new GsonBuilder().setLenient().create();
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder().baseUrl(Constant.BASE_URL)
                    .client(builder.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return sRetrofit.create(serviceClass);
    }
}
