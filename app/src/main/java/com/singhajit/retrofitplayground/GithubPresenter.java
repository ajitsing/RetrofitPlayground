package com.singhajit.retrofitplayground;

import com.singhajit.retrofitplayground.github.GithubApiClient;
import com.singhajit.retrofitplayground.github.Repository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GithubPresenter {
  private final GithubApiClient githubApiClient;
  private final GithubView view;

  public GithubPresenter(GithubApiClient githubApiClient, GithubView view) {
    this.githubApiClient = githubApiClient;
    this.view = view;
  }

  public void renderRepos(String user) {
    Observable<List<Repository>> repos = githubApiClient.repos(user);
    repos.observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.newThread())
        .subscribe(new Observer<List<Repository>>() {
          @Override
          public void onSubscribe(Disposable d) {

          }

          @Override
          public void onNext(List<Repository> repos) {
            view.render(repos);
          }

          @Override
          public void onError(Throwable e) {

          }

          @Override
          public void onComplete() {
          }
        });
  }
}
