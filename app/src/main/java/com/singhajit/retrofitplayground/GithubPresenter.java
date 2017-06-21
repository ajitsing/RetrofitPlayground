package com.singhajit.retrofitplayground;

import android.support.annotation.NonNull;

import com.singhajit.retrofitplayground.github.GithubApiClient;
import com.singhajit.retrofitplayground.github.GithubUser;
import com.singhajit.retrofitplayground.github.Organization;
import com.singhajit.retrofitplayground.github.Repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

import static java.util.Arrays.asList;

public class GithubPresenter {
  private final GithubApiClient githubApiClient;
  private final GithubView view;

  public GithubPresenter(GithubApiClient githubApiClient, GithubView view) {
    this.githubApiClient = githubApiClient;
    this.view = view;
  }

  public void renderRepos(String... users) {
    List<String> githubUsers = asList(users);
    ArrayList<Observable<List<Repository>>> pendingRequests = new ArrayList<>();
    for (String githubUser : githubUsers) {
      pendingRequests.add(githubApiClient.repos(githubUser));
    }

    Observable.merge(pendingRequests)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(githubReposObserver());
  }

  public void renderRepos(String user) {
    Observable<List<Repository>> repos = githubApiClient.repos(user);
    repos.observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.newThread())
        .subscribe(githubReposObserver());
  }

  public void render(String user) {
    Observable<List<Repository>> repos = githubApiClient.repos(user);
    Observable<List<Organization>> orgs = githubApiClient.organizations(user);

    Observable.combineLatest(repos, orgs, new BiFunction<List<Repository>, List<Organization>, GithubUser>() {
      @Override
      public GithubUser apply(List<Repository> repositories, List<Organization> organizations) throws Exception {
        return new GithubUser(repositories, organizations);
      }
    }).subscribeOn(Schedulers.newThread())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(githubUserObserver());
  }

  @NonNull
  private Observer<GithubUser> githubUserObserver() {
    return new Observer<GithubUser>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(GithubUser githubUser) {
        view.renderUser(githubUser);
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onComplete() {

      }
    };
  }

  @NonNull
  private Observer<List<Repository>> githubReposObserver() {
    final List<Repository> allRepos = new ArrayList<>();
    return new Observer<List<Repository>>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(List<Repository> repos) {
        allRepos.addAll(repos);
      }

      @Override
      public void onError(Throwable e) {
      }

      @Override
      public void onComplete() {
        view.render(allRepos);
      }
    };
  }
}
