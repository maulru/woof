package com.example.igorg.woof;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET
    Call<String> getResponseFromOurServer(@Url String url);





}