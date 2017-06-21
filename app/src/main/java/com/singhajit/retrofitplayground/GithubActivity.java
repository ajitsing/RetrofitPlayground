package com.singhajit.retrofitplayground;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.singhajit.retrofitplayground.github.GithubApiClient;
import com.singhajit.retrofitplayground.github.GithubUser;
import com.singhajit.retrofitplayground.github.Repository;

import java.util.List;

import retrofit2.Retrofit;

public class GithubActivity extends AppCompatActivity implements GithubView {

  private GithubPresenter presenter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.github_activity);
    Retrofit githubService = RetrofitServiceFactory.getGithubService(this);
    GithubApiClient githubApiClient = githubService.create(GithubApiClient.class);

    presenter = new GithubPresenter(githubApiClient, this);
    presenter.renderRepos("ajitsing");
  }

  @Override
  public void render(List<Repository> repos) {
    String allRepos = "";
    for (Repository repo : repos) {
      allRepos += repo.getName() + "\n";
    }
    TextView reposTextView = (TextView) findViewById(R.id.repos);
    reposTextView.setText(allRepos);
  }

  @Override
  public void renderUser(GithubUser githubUser) {
    resetReposView();
    TextView user = (TextView) findViewById(R.id.user);
    user.setText("User: " + githubUser.getRepositories().get(0).getOwner().getLogin());

    TextView userRepos = (TextView) findViewById(R.id.user_repos);
    TextView userOrgs = (TextView) findViewById(R.id.user_orgs);

    userRepos.setText("Repos: " + githubUser.getRepositories().size());
    userOrgs.setText("Orgs: " + githubUser.getOrganizations().size());
  }

  public void batchRequests(View view) {
    presenter.renderRepos("ajitsing", "JakeWharton");
  }

  public void multiDiffRequests(View view) {
    presenter.render("ajitsing");
  }

  private void resetReposView() {
    TextView reposTextView = (TextView) findViewById(R.id.repos);
    reposTextView.setText("");
  }
}
