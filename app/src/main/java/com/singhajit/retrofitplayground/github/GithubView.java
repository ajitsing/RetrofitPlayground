package com.singhajit.retrofitplayground.github;

import com.singhajit.retrofitplayground.github.model.GithubUser;
import com.singhajit.retrofitplayground.github.model.Repository;

import java.util.List;

public interface GithubView {
  void render(List<Repository> repos);

  void renderUser(GithubUser githubUser);
}
