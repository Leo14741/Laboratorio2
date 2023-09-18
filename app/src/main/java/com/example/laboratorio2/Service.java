package com.example.laboratorio2;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
    @GET("/api/")
    Call<Profile> getProfile();
}
