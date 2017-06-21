package com.singhajit.retrofitplayground.github;

import java.util.List;

public class GithubUser {
  private List<Repository> repositories;
  private List<Organization> organizations;

  public GithubUser(List<Repository> repositories, List<Organization> organizations) {
    this.repositories = repositories;
    this.organizations = organizations;
  }

  public List<Repository> getRepositories() {
    return repositories;
  }

  public List<Organization> getOrganizations() {
    return organizations;
  }
}
