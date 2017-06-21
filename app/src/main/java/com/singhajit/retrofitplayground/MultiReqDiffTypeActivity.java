package com.singhajit.retrofitplayground;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.singhajit.retrofitplayground.github.GithubApiClient;
import com.singhajit.retrofitplayground.github.Repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MultiReqDiffTypeActivity extends AppCompatActivity {
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    final List<Repository> allRepos = new ArrayList<>();

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


    Retrofit githubRetrofit = new Retrofit.Builder()
        .client(client)
        .baseUrl("https://api.github.com/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    GithubApiClient githubApiClient = githubRetrofit.create(GithubApiClient.class);

    Observable<List<Repository>> ajitsingRepos = githubApiClient.repos("ajitsing");
    Observable<List<Repository>> jakeWhartonRepos = githubApiClient.repos("JakeWharton");

    Observable.combineLatest(ajitsingRepos, jakeWhartonRepos, ajitsingRepos, new Function3<List<Repository>, List<Repository>, List<Repository>, MergedResult>() {
      @Override
      public MergedResult apply(List<Repository> repositories, List<Repository> repositories2, List<Repository> repositories3) throws Exception {
        return new MergedResult(repositories, repositories2);
      }
    }).subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<MergedResult>() {
          @Override
          public void onSubscribe(Disposable d) {

          }

          @Override
          public void onNext(MergedResult value) {
            allRepos.addAll(value.repos1);
            allRepos.addAll(value.repos2);
          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onComplete() {
            displayRepos(allRepos);
          }
        });
  }

  private void displayRepos(List<Repository> repos) {
    String allRepos = "";
    for (Repository repo : repos) {
      allRepos += repo.getName() + "\n";
    }
    TextView reposTextView = (TextView) findViewById(R.id.repos);
    reposTextView.setText(allRepos);
  }

  static class MergedResult {
    List<Repository> repos1;
    List<Repository> repos2;

    public MergedResult(List<Repository> repos1, List<Repository> repos2) {
      this.repos1 = repos1;
      this.repos2 = repos2;
    }
  }

}