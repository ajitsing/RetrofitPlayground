package com.singhajit.retrofitplayground;

import android.os.Bundle;
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
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MultiRequestActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Retrofit githubRetrofit = new Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    GithubApiClient githubApiClient = githubRetrofit.create(GithubApiClient.class);

    Observable<List<Repository>> ajitsingRepos = githubApiClient.repos("ajitsing");
    Observable<List<Repository>> jakeWhartonRepos = githubApiClient.repos("JakeWharton");

    Observable.merge(ajitsingRepos, jakeWhartonRepos, ajitsingRepos).subscribeOn(Schedulers.newThread())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(new Observer<List<Repository>>() {
      List<Repository> repositories = new ArrayList<>();
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(List<Repository> value) {
        repositories.addAll(value);
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onComplete() {
        displayRepos(repositories);
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
}
