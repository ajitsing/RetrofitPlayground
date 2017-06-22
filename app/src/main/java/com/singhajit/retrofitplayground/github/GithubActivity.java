package com.singhajit.retrofitplayground.github;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.singhajit.retrofitplayground.R;
import com.singhajit.retrofitplayground.github.model.GithubUser;
import com.singhajit.retrofitplayground.github.model.Repository;
import com.singhajit.retrofitplayground.github.network.GithubApiClient;
import com.singhajit.retrofitplayground.github.network.RetrofitServiceFactory;

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
    resetViews();

    TextView user = (TextView) findViewById(R.id.user);
    user.setText("User: " + githubUser.getRepositories().get(0).getOwner().getLogin());

    TextView userRepos = (TextView) findViewById(R.id.user_repos);
    TextView userOrgs = (TextView) findViewById(R.id.user_orgs);

    userRepos.setText("Repos: " + githubUser.getRepositories().size());
    userOrgs.setText("Orgs: " + githubUser.getOrganizations().size());

    showUserInfo();
  }

  public void batchRequests(View view) {
    resetViews();
    presenter.renderRepos("JakeWharton", "ajitsing");
  }

  public void multiDiffRequests(View view) {
    presenter.render("ajitsing");
  }

  public void singleRequest(View view) {
    resetViews();
    presenter.renderRepos("ajitsing");
  }

  private void resetViews() {
    TextView reposTextView = (TextView) findViewById(R.id.repos);
    reposTextView.setText("");
    hideUserInfo();
  }

  private void hideUserInfo() {
    LinearLayout userInfo = (LinearLayout) findViewById(R.id.user_info);
    userInfo.setVisibility(View.GONE);
  }

  private void showUserInfo() {
    LinearLayout userInfo = (LinearLayout) findViewById(R.id.user_info);
    userInfo.setVisibility(View.VISIBLE);
  }
}
