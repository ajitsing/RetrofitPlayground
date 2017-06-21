package com.singhajit.retrofitplayground;

import com.singhajit.retrofitplayground.github.Repository;

import java.util.List;

interface GithubView {
  void render(List<Repository> repos);
}
