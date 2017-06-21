package com.singhajit.retrofitplayground;

import com.singhajit.retrofitplayground.github.GithubPresenter;
import com.singhajit.retrofitplayground.github.GithubView;
import com.singhajit.retrofitplayground.github.network.GithubApiClient;
import com.singhajit.retrofitplayground.github.model.Repository;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GithubPresenterTest {

  private GithubApiClient githubApiClient;
  private GithubView view;
  private GithubPresenter presenter;

  @Before
  public void setUp() throws Exception {
    RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
      @Override
      public Scheduler apply(Callable<Scheduler> schedulerCallable) throws Exception {
        return Schedulers.trampoline();
      }
    });

    githubApiClient = mock(GithubApiClient.class);
    view = mock(GithubView.class);
    presenter = new GithubPresenter(githubApiClient, view);
  }

  @Test
  public void shouldFetchAndRenderRepos() throws Exception {
    String user = "ajitsing";
    List<Repository> repos = (List<Repository>) mock(List.class);
    Observable<List<Repository>> reposObservable = Observable.just(repos);

    when(githubApiClient.repos(user)).thenReturn(reposObservable);

    presenter.renderRepos(user);

    verify(view).render(repos);
  }
}