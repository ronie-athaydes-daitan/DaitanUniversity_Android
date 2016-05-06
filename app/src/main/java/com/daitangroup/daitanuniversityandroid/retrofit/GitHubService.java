package com.daitangroup.daitanuniversityandroid.retrofit;

import com.daitangroup.daitanuniversityandroid.model.Repo;
import com.daitangroup.daitanuniversityandroid.retrofit.response.UsersResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubService {

    @GET("search/users")
    Call<UsersResponse> listUsers(@Query("q") String query);

    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);
}