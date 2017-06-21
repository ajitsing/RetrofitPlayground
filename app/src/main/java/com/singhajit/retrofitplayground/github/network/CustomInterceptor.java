package com.singhajit.retrofitplayground.github.network;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CustomInterceptor implements Interceptor {
  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();
    String path = request.url().encodedPath();
    Log.d("CustomInterceptor", "================>" + path);
    return chain.proceed(request);
  }
}
