package com.singhajit.retrofitplayground.github;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface GithubApiClient {
  @GET("users/{user}/repos")
  @Headers("Cache-Control: max-age=10")
  Observable<List<Repository>> repos(@Path("user") String user);

  @GET("users/{user}/orgs")
  Observable<List<Organization>> organizations(@Path("user") String user);
}
