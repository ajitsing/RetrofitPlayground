package com.singhajit.retrofitplayground.github.network;

import android.content.Context;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class OfflineModeInterceptor implements Interceptor {
  private final Context context;

  public OfflineModeInterceptor(Context context) {
    this.context = context;
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();
    if (!NetworkStateIdentifier.isConnectedToInternet(context)) {
      return chain.proceed(request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build());
    }
    return chain.proceed(request);
  }
}
