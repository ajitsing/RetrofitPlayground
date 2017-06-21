package com.singhajit.retrofitplayground;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.singhajit.retrofitplayground.github.CustomInterceptor;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceFactory {
  public static Retrofit getGithubService(Context context) {
    Cache cache = new Cache(new File(context.getCacheDir(), "http"), 10 * 1024 * 1024);

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient client = new OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(interceptor)
        .addNetworkInterceptor(new CustomInterceptor())
        .build();

    return new Retrofit.Builder()
        .client(client)
        .baseUrl("https://api.github.com/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }
}