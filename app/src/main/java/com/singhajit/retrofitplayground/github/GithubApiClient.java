package com.singhajit.retrofitplayground.github;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubApiClient {
  @GET("users/{user}/repos")
  Observable<List<Repository>> repos(@Path("user") String user);
}
