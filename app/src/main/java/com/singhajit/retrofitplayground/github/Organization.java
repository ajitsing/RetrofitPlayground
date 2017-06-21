package com.singhajit.retrofitplayground.github;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Organization {
  @SerializedName("login")
  @Expose
  public String login;
  @SerializedName("id")
  @Expose
  public Integer id;
  @SerializedName("url")
  @Expose
  public String url;
  @SerializedName("repos_url")
  @Expose
  public String reposUrl;
  @SerializedName("events_url")
  @Expose
  public String eventsUrl;
  @SerializedName("hooks_url")
  @Expose
  public String hooksUrl;
  @SerializedName("issues_url")
  @Expose
  public String issuesUrl;
  @SerializedName("members_url")
  @Expose
  public String membersUrl;
  @SerializedName("public_members_url")
  @Expose
  public String publicMembersUrl;
  @SerializedName("avatar_url")
  @Expose
  public String avatarUrl;
  @SerializedName("description")
  @Expose
  public String description;

}